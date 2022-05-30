package net.eugenpaul.adventofcode.y2016.day15;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    @Getter
    private int time;

    @Getter
    private int time2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2016/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<Integer, Disc> dMap = eventData.stream()//
                .map(Disc::fromString)//
                .collect(Collectors.toMap(Disc::getNumber, v -> v));

        time = doPuzzle(dMap);

        dMap.put(dMap.size() + 1, new Disc(dMap.size() + 1, 11, 0));
        time2 = doPuzzleFast(dMap);

        logger.log(Level.INFO, () -> "time : " + getTime());
        logger.log(Level.INFO, () -> "time2 : " + getTime2());

        return true;
    }

    private int doPuzzle(Map<Integer, Disc> dMap) {
        boolean found = false;

        int counter = -1;
        while (!found) {
            counter += 1;
            int checkTime = counter;

            found = dMap.values().stream()//
                    .noneMatch(v -> v.getPositionAtTime(v.getNumber() + checkTime) != 0) //
            ;
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
                            .reduce(currentLcm, MathHelper::lcm);
                    discsPassed = badDisc.get().getNumber() - 1;
                }
            }
        }
        return counter;
    }
}
