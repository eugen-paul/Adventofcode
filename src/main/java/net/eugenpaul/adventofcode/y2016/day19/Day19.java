package net.eugenpaul.adventofcode.y2016.day19;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day19 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day19.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day19.class.getName());

    @Getter
    private int elfWithAllPresents;
    @Getter
    private int elfWithAllPresents2;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2016/day19/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "elfWithAllPresents " + getElfWithAllPresents());
        logger.log(Level.INFO, () -> "elfWithAllPresents2 " + getElfWithAllPresents2());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        int elfNumber = Integer.parseInt(eventData);

        elfWithAllPresents = doPuzzle1(elfNumber);
        elfWithAllPresents2 = doPuzzle2Fast(elfNumber);

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

        return true;
    }

    private int doPuzzle1(int elfNumber) {
        LinkedList<Integer> elfs = new LinkedList<>();
        for (int i = 1; i <= elfNumber; i++) {
            elfs.add(i);
        }

        boolean removeFirst = false;

        while (elfs.size() > 1) {
            ListIterator<Integer> iterator = elfs.listIterator();
            while (iterator.hasNext()) {
                if (removeFirst) {
                    iterator.next();
                    iterator.remove();
                    removeFirst = false;
                    continue;
                }

                iterator.next();
                if (iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                } else {
                    removeFirst = true;
                }
            }
        }

        return elfs.get(0);
    }

    /**
     * If the number of elves is even then delete an one elf sitting directly opposite, else delete two elves. Then perform the following step (in a circle) as
     * long as the number of remaining elves is greater than 1: Skip one elf and delete two next ones.
     * 
     * @param elfNumber
     * @return
     */
    private int doPuzzle2Fast(int elfNumber) {
        LinkedList<Integer> elfs = new LinkedList<>();
        for (int i = 1; i <= elfNumber; i++) {
            elfs.add(i);
        }

        Integer lastElement = null;

        int cnt = elfs.size() % 2 + 1;
        int offset = elfs.size() / 2;

        while (elfs.size() > 1) {
            ListIterator<Integer> iterator = elfs.listIterator();
            int pos = 0;
            while (iterator.hasNext()) {
                lastElement = iterator.next();
                if (pos >= offset) {
                    if (cnt % 3 != 0) {
                        iterator.remove();
                    }
                    cnt++;
                }
                pos++;
            }

            offset = 0;
        }

        if (elfs.isEmpty()) {
            return lastElement;
        }
        return elfs.get(0);
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
