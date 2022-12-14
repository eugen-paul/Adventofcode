package net.eugenpaul.adventofcode.y2021.day5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.RangeOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @Getter
    private long overlaps;
    @Getter
    private long overlaps2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2021/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Integer> area = new HashMap<>();
        for (String line : eventData) {
            var range = RangeOfSimplePos.fromString(line, " -> ", ",");
            if (range.isHorizontalOrVertical()) {
                range.forEach(addPoints(area));
            }
        }

        Map<SimplePos, Integer> areaFull = new HashMap<>();
        for (String line : eventData) {
            var range = RangeOfSimplePos.fromString(line, " -> ", ",");
            if (range.isHorizontalOrVertical()) {
                range.forEach(addPoints(areaFull));
            } else {
                range.forEachDiagonaly(addPoints(areaFull));
            }
        }

        overlaps = area.values().stream().filter(v -> v > 1).count();
        overlaps2 = areaFull.values().stream().filter(v -> v > 1).count();

        logger.log(Level.INFO, () -> "overlaps  : " + getOverlaps());
        logger.log(Level.INFO, () -> "overlaps2 : " + getOverlaps2());

        return true;
    }

    private Consumer<SimplePos> addPoints(Map<SimplePos, Integer> areaFull) {
        return point -> areaFull.compute(point, (k, v) -> (v == null) ? 1 : v + 1);
    }

}
