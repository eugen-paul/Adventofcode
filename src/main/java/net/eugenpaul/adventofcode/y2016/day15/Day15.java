package net.eugenpaul.adventofcode.y2016.day15;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day15 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day15.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day15.class.getName());

    @Getter
    private int time;

    @Getter
    private int time2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2016/day15/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "time : " + getTime());
        logger.log(Level.INFO, () -> "time2 : " + getTime2());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<Integer, Disc> dMap = eventData.stream()//
                .map(Disc::fromString)//
                .collect(Collectors.toMap(Disc::getNumber, v -> v));

        time = doPuzzle(dMap);

        dMap.put(dMap.size() + 1, new Disc(dMap.size() + 1, 11, 0));
        time2 = doPuzzleFast(dMap);

        return true;
    }

    private int doPuzzle(Map<Integer, Disc> dMap) {
        boolean found = false;

        int counter = -1;
        while (!found) {
            counter += 1;
            int checkTime = counter;

            Optional<Disc> badDisc = dMap.values().stream()//
                    .filter(v -> v.getPositionAtTime(v.getNumber() + checkTime) != 0)//
                    .findAny();
            if (badDisc.isEmpty()) {
                found = true;
            }
        }
        return counter;
    }

    private int doPuzzleFast(Map<Integer, Disc> dMap) {
        boolean found = false;

        int counter = dMap.get(1).getPositionCount() - dMap.get(1).getStartPosition() - 1;
        int stepSize = dMap.get(1).getPositionCount();
        int discsPassed = 1;
        int currentLcm = stepSize;

        while (!found) {
            counter += stepSize;
            int checkTime = counter;

            Optional<Disc> badDisc = dMap.values().stream()//
                    .filter(v -> v.getPositionAtTime(v.getNumber() + checkTime) != 0)//
                    .findFirst();

            if (badDisc.isEmpty()) {
                found = true;
            } else {
                if (badDisc.get().getNumber() > discsPassed + 1) {
                    int localDiscPassed = discsPassed;
                    stepSize = dMap.entrySet().stream()//
                            .filter(v -> v.getKey() < badDisc.get().getNumber())//
                            .filter(v -> v.getKey() > localDiscPassed)//
                            .mapToInt(v -> v.getValue().getPositionCount())//
                            .reduce(currentLcm, Day15::lcm);
                    discsPassed = badDisc.get().getNumber() - 1;
                }
            }
        }
        return counter;
    }

    /**
     * greatest common factor
     * 
     * @param a
     * @param b
     * @return
     */
    static int gcf(int a, int b) {

        if (a == b || b == 0)
            return a;
        else
            return gcf(b, a % b);

    }

    /**
     * lowest common multiple
     * 
     * @param a
     * @param b
     * @return
     */
    static int lcm(int a, int b)

    {
        return a * (b / gcf(a, b));
    }

}
