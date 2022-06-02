package net.eugenpaul.adventofcode.y2016.day19;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.CircleMemory;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    @Getter
    private int elfWithAllPresents;
    @Getter
    private int elfWithAllPresents2;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2016/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        int elfNumber = Integer.parseInt(eventData);

        elfWithAllPresents = doPuzzle1(elfNumber);
        elfWithAllPresents2 = doPuzzle2(elfNumber);

        // just for testing
        if (elfNumber < 1000) {
            int slow = doPuzzle2Slow(elfNumber);
            if (elfWithAllPresents2 != slow) {
                logger.log(Level.INFO, () -> "doPuzzle2 (" + elfWithAllPresents2 + ") != doPuzzle2_slow (" + slow + ")");
                return false;
            } else {
                logger.log(Level.INFO, () -> "doPuzzle2 (" + elfWithAllPresents2 + ") == doPuzzle2_slow (" + slow + ")");
            }
        }

        logger.log(Level.INFO, () -> "elfWithAllPresents " + getElfWithAllPresents());
        logger.log(Level.INFO, () -> "elfWithAllPresents2 " + getElfWithAllPresents2());

        return true;
    }

    private int doPuzzle1(int elfNumber) {
        CircleMemory<Integer> cm = new CircleMemory<>();

        CircleMemory<Integer>.CirclePosition first = cm.addFirst(1);
        for (int i = 2; i <= elfNumber; i++) {
            cm.addLast(i);
        }

        CircleMemory<Integer>.CirclePosition position = first;
        while (cm.getSize() > 1) {
            position = cm.moveNext(position);
            position = cm.removeAndMoveNext(position);
        }

        return position.getData();
    }

    /**
     * If the number of elves is even then delete an two elf sitting directly opposite, else delete one elves. Then perform the following step (in a circle) as
     * long as the number of remaining elves is greater than 1: Skip one elf and delete two next ones.
     * 
     * @param elfNumber
     * @return
     */
    private int doPuzzle2(int elfNumber) {
        CircleMemory<Integer> cm = new CircleMemory<>();

        CircleMemory<Integer>.CirclePosition first = cm.addFirst(1);
        for (int i = 2; i <= elfNumber; i++) {
            cm.addLast(i);
        }

        boolean even = elfNumber % 2 == 0;
        CircleMemory<Integer>.CirclePosition position = cm.moveNext(first, (elfNumber / 2));
        if (even) {
            position = cm.removeAndMoveNext(position);
            position = cm.removeAndMoveNext(position);
        } else {
            position = cm.removeAndMoveNext(position);
        }

        while (cm.getSize() > 1) {
            position = cm.moveNext(position);
            position = cm.removeAndMoveNext(position);
            if (cm.getSize() == 1) {
                break;
            }
            position = cm.removeAndMoveNext(position);
        }

        return position.getData();
    }

    private int doPuzzle2Slow(int elfNumber) {
        ArrayList<Integer> elfs = new ArrayList<>();
        for (int i = 1; i <= elfNumber; i++) {
            elfs.add(i);
        }

        int activeElfPosition = 0;

        while (elfs.size() > 1) {
            int acrossPosition = getAcrossPosition(elfs, activeElfPosition);
            if (acrossPosition >= activeElfPosition) {
                activeElfPosition++;
            }

            elfs.remove(acrossPosition);
            if (activeElfPosition >= elfs.size()) {
                activeElfPosition = 0;
            }
        }

        return elfs.get(0);
    }

    private int getAcrossPosition(List<Integer> elfs, int activeElfPosition) {
        return (elfs.size() / 2 + activeElfPosition) % elfs.size();
    }

}
