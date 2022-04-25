package net.eugenpaul.adventofcode.y2018.day9;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @Getter
    private long winningScore;
    @Getter
    private long winningScore2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2018/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        String[] elements = eventData.split(" ");
        int elfs = Integer.parseInt(elements[0]);
        int marbles = Integer.parseInt(elements[6]);

        winningScore = doPuzzle(elfs, marbles);
        winningScore2 = doPuzzle(elfs, marbles * 100);
        logger.log(Level.INFO, () -> "winningScore : " + getWinningScore());
        logger.log(Level.INFO, () -> "winningScore2 : " + getWinningScore2());

        return true;
    }

    private long doPuzzle(int elfs, int marbles) {
        LinkedList<Long> circle = new LinkedList<>();
        AtomicLong currentValue = new AtomicLong(0L);
        circle.add(0L);
        Map<Integer, Long> score = new HashMap<>();
        int currentElf = 1;

        var iterator = circle.listIterator();
        for (long i = 1L; i <= marbles; i++) {
            long currentMarble = i;
            if (i % 23L == 0L) {
                score.compute(currentElf, (k, v) -> v == null ? currentMarble : v + currentMarble);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                iterator = previous(circle, iterator, currentValue);
                score.compute(currentElf, (k, v) -> v + currentValue.get());
                iterator.remove();
                iterator = next(circle, iterator, currentValue);
            } else {
                iterator = next(circle, iterator, currentValue);
                iterator.add(i);
            }

            currentElf++;
            if (currentElf > elfs) {
                currentElf = 1;
            }
        }

        return score.values().stream().sorted((a, b) -> b.compareTo(a)).findFirst().orElse(0L);
    }

    private ListIterator<Long> next(LinkedList<Long> circle, ListIterator<Long> iterator, AtomicLong currentValue) {
        ListIterator<Long> response = iterator;

        if (!iterator.hasNext()) {
            response = circle.listIterator();
        }

        currentValue.set(response.next());
        return response;
    }

    private ListIterator<Long> previous(LinkedList<Long> circle, ListIterator<Long> iterator, AtomicLong currentValue) {
        ListIterator<Long> response = iterator;

        response.previous();

        if (!response.hasPrevious()) {
            response = circle.listIterator(circle.size());
        }

        currentValue.set(response.previous());

        currentValue.set(response.next());

        return response;
    }
}
