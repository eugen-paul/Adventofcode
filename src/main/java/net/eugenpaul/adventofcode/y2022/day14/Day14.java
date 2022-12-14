package net.eugenpaul.adventofcode.y2022.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    private enum Value {
        EMPTY, SAND, ROCK
    }

    @Getter
    private long unitsOfSand;
    @Getter
    private long unitsOfSand2;

    @Setter
    private boolean printArea = false;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2022/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        unitsOfSand = doPuzzle1(eventData);
        unitsOfSand2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "unitsOfSand : " + getUnitsOfSand());
        logger.log(Level.INFO, () -> "unitsOfSand2 : " + getUnitsOfSand2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        SimplePos start = new SimplePos(500, 0);
        Map<SimplePos, Value> area = parseMap(eventData);

        int counter = 0;

        if (printArea) {
            MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(area, v -> {
                if (v == null || v == Value.EMPTY) {
                    return ' ';
                }
                if (v == Value.SAND) {
                    return 'o';
                }
                return '#';
            }));
        }

        int maxY = area.keySet().stream().mapToInt(SimplePos::getY).max().orElseThrow();

        while (doFall(area, start, maxY, maxY + 2)) {
            counter++;
        }

        return counter;
    }

    private long doPuzzle2(List<String> eventData) {
        SimplePos start = new SimplePos(500, 0);
        Map<SimplePos, Value> area = parseMap(eventData);

        int counter = 0;

        int floorY = area.keySet().stream().mapToInt(SimplePos::getY).max().orElseThrow() + 2;

        while (doFall(area, start, floorY + 1, floorY)) {
            counter++;
        }

        return counter;
    }

    private boolean doFall(Map<SimplePos, Value> area, SimplePos pos, int maxY, int floorY) {
        var down = new SimplePos(0, 1);
        var left = new SimplePos(-1, 1);
        var right = new SimplePos(1, 1);

        var currentPos = pos.copy();
        if (area.get(currentPos) == Value.SAND) {
            return false;
        }

        while (true) {
            var toDown = currentPos.addNew(down);
            if (toDown.getY() == floorY) {
                area.put(currentPos, Value.SAND);
                return true;
            }

            if (toDown.getY() > maxY) {
                return false;
            }

            if (area.get(toDown) == null || area.get(toDown) == Value.EMPTY) {
                currentPos = toDown;
                continue;
            }

            var toLeft = currentPos.addNew(left);
            if (area.get(toLeft) == null || area.get(toLeft) == Value.EMPTY) {
                currentPos = toLeft;
                continue;
            }

            var toRight = currentPos.addNew(right);
            if (area.get(toRight) == null || area.get(toRight) == Value.EMPTY) {
                currentPos = toRight;
                continue;
            }

            area.put(currentPos, Value.SAND);
            break;
        }
        return true;
    }

    private Map<SimplePos, Value> parseMap(List<String> eventData) {
        Map<SimplePos, Value> area = new HashMap<>();

        for (var path : eventData) {
            addPath(area, path);
        }
        return area;
    }

    private void addPath(Map<SimplePos, Value> area, String path) {
        var points = path.split(" -> ");

        SimplePos last = null;
        for (String p : points) {
            SimplePos current = SimplePos.fromData(p, ",");
            if (last == null) {
                last = current;
                continue;
            }

            last.forEach(current, v -> area.put(v, Value.ROCK));

            last = current;
        }
    }

}
