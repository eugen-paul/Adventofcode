package net.eugenpaul.adventofcode.y2020.day23;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.CircleMemory;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day23 extends SolutionTemplate {

    @Getter
    private String label;
    @Getter
    private long multiply;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2020/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        List<Long> cups = Arrays.stream(StringConverter.digitsToLongArray(eventData))//
                .boxed()//
                .collect(Collectors.toList());

        label = doPuzzle1(cups);
        multiply = doPuzzle2(cups);

        logger.log(Level.INFO, () -> "label  : " + getLabel());
        logger.log(Level.INFO, () -> "multiply  : " + getMultiply());

        return true;
    }

    private String doPuzzle1(List<Long> cupsInput) {
        LinkedList<Long> cups = doGame(new LinkedList<>(cupsInput), 100);

        StringBuilder part1 = new StringBuilder();
        StringBuilder part2 = new StringBuilder();
        StringBuilder currentBuilder = part1;
        var iterator = cups.iterator();
        while (iterator.hasNext()) {
            var d = iterator.next();
            if (d.equals(1L)) {
                currentBuilder = part2;
            } else {
                currentBuilder.append(d);
            }
        }

        return part2.toString() + part1.toString();
    }

    private Long doPuzzle2(List<Long> cupsInput) {
        LinkedList<Long> cups = new LinkedList<>(cupsInput);
        for (long i = cupsInput.size(); i < 1_000_000; i++) {
            cups.add(i + 1);
        }
        LinkedList<Long> finalCups = doGame(cups, 10_000_000);

        long a = 0;
        long b = 0;
        var iterator = finalCups.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(1L)) {
                if (iterator.hasNext()) {
                    a = iterator.next();
                    if (iterator.hasNext()) {
                        b = iterator.next();
                    } else {
                        b = finalCups.getFirst();
                    }
                } else {
                    a = finalCups.get(0);
                    b = finalCups.get(1);
                }
                break;
            }
        }

        return a * b;
    }

    private LinkedList<Long> doGame(LinkedList<Long> cups, int maxRounds) {
        CircleMemory<Long> memory = new CircleMemory<>();
        int max = cups.size();

        CircleMemory<Long>.CirclePosition currentPos = null;
        for (Long d : cups) {
            currentPos = memory.addLast(d);
        }

        if (currentPos == null) {
            throw new IllegalArgumentException();
        }

        CircleMemory<Long>.CirclePosition it = memory.moveNext(currentPos);
        Map<Long, CircleMemory<Long>.CirclePosition> dataToPos = new HashMap<>();
        while (it != currentPos) {
            dataToPos.put(it.getData(), it);
            it = memory.moveNext(it);
        }
        dataToPos.put(it.getData(), it);

        currentPos = memory.moveNext(currentPos);

        Long destination;
        for (int round = 0; round < maxRounds; round++) {
            List<Long> removedCups = pickUp(memory, currentPos);

            for (Long removed : removedCups) {
                dataToPos.remove(removed);
            }

            destination = getDestination(currentPos.getData(), removedCups, max);
            put(memory, removedCups, dataToPos.get(destination), dataToPos);
            currentPos = memory.moveNext(currentPos);
        }

        LinkedList<Long> response = new LinkedList<>();
        memory.forEach(response::add);
        return response;
    }

    private List<Long> pickUp(CircleMemory<Long> memory, CircleMemory<Long>.CirclePosition currentPos) {
        List<Long> response = new LinkedList<>();

        var next = memory.moveNext(currentPos);

        response.add(next.getData());

        next = memory.removeAndGetNext(next);
        response.add(next.getData());

        next = memory.removeAndGetNext(next);
        response.add(next.getData());

        memory.removeAndGetNext(next);

        return response;
    }

    private Long getDestination(Long current, List<Long> removedCups, long max) {
        for (long i = current - 1; i > 0; i--) {
            if (!removedCups.contains(i)) {
                return i;
            }
        }

        for (long i = max; i > current; i--) {
            if (!removedCups.contains(i)) {
                return i;
            }
        }

        throw new IllegalArgumentException();
    }

    private void put(CircleMemory<Long> memory, List<Long> removedCups, CircleMemory<Long>.CirclePosition pos,
            Map<Long, CircleMemory<Long>.CirclePosition> dataToPos) {
        CircleMemory<Long>.CirclePosition newPos = memory.add(removedCups.get(0), pos);
        dataToPos.put(removedCups.get(0), newPos);

        newPos = memory.add(removedCups.get(1), newPos);
        dataToPos.put(removedCups.get(1), newPos);

        newPos = memory.add(removedCups.get(2), newPos);
        dataToPos.put(removedCups.get(2), newPos);
    }
}
