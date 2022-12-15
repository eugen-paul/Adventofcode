package net.eugenpaul.adventofcode.y2022.day15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    private enum Type {
        SENSOR, BEACON
    }

    @Setter
    private int part1Line = 2000000;
    @Setter
    private int part2Size = 4000000;

    @Getter
    private long unitsOfSand;
    @Getter
    private long unitsOfSand2;

    @Setter
    private boolean printArea = false;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2022/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        unitsOfSand = doPuzzle1(eventData, part1Line);
        unitsOfSand2 = doPuzzle2(eventData, part2Size);

        logger.log(Level.INFO, () -> "unitsOfSand : " + getUnitsOfSand());
        logger.log(Level.INFO, () -> "unitsOfSand2 : " + getUnitsOfSand2());

        return true;
    }

    private long doPuzzle1(List<String> eventData, int line) {
        Map<SimplePos, SimplePos> closestBeacon = new HashMap<>();
        var area = readArea(eventData, closestBeacon);

        long maxDistance = closestBeacon.entrySet().stream().mapToLong(v -> v.getKey().manhattanDistance(v.getValue())).max().orElseThrow();

        long minX = area.keySet().stream().mapToLong(SimplePos::getX).min().orElseThrow();
        long maxX = area.keySet().stream().mapToLong(SimplePos::getX).max().orElseThrow();

        int response = 0;
        SimplePos testPoint = new SimplePos(0, line);
        for (long i = minX - maxDistance; i <= maxX + maxDistance; i++) {
            testPoint.setX((int) i);

            if (area.get(testPoint) == Type.BEACON) {
                continue;
            }

            var isOk = true;
            for (var v : closestBeacon.entrySet()) {
                var d1 = v.getKey().manhattanDistance(v.getValue());
                var d2 = v.getKey().manhattanDistance(testPoint);
                if (d1 >= d2) {
                    isOk = false;
                    break;
                }
            }

            if (!isOk) {
                response++;
            }
        }

        return response;
    }

    private long doPuzzle2(List<String> eventData, int max) {
        Map<SimplePos, SimplePos> closestBeacon = new HashMap<>();
        var area = readArea(eventData, closestBeacon);

        SimplePos testPos = new SimplePos(0, 0);

        for (int y = 0; y <= max; y++) {
            testPos.setY(y);
            for (int x = 0; x <= max;) {
                testPos.setX(x);

                int deltaX = 1;
                boolean isOk = true;
                for (var entry : closestBeacon.entrySet()) {
                    long distToS = testPos.manhattanDistance(entry.getKey());
                    long distSB = entry.getKey().manhattanDistance(entry.getValue());

                    if (distSB >= distToS) {
                        deltaX = (int) Math.max(deltaX, distSB - distToS + 1);
                        isOk = false;
                    }
                }

                if (isOk) {
                    return x * 4000000L + y;
                }

                x += deltaX;
            }
        }

        return testPos.getX() * testPos.getY();
    }

    private Map<SimplePos, Type> readArea(List<String> eventData, Map<SimplePos, SimplePos> closestBeacon) {
        Map<SimplePos, Type> response = new HashMap<>();

        for (var data : eventData) {
            SimplePos sensorPos = new SimplePos(0, 0);
            SimplePos beaconPos = new SimplePos(0, 0);
            readPos(data, sensorPos, beaconPos);

            response.put(sensorPos, Type.SENSOR);
            response.put(beaconPos, Type.BEACON);

            closestBeacon.put(sensorPos.copy(), beaconPos.copy());
        }

        return response;
    }

    private void readPos(String data, SimplePos sensorPos, SimplePos beaconPos) {
        var d = data.split(" ");

        sensorPos.setX(Integer.parseInt(d[2].replace(",", "").substring(2)));
        sensorPos.setY(Integer.parseInt(d[3].replace(":", "").substring(2)));

        beaconPos.setX(Integer.parseInt(d[8].replace(",", "").substring(2)));
        beaconPos.setY(Integer.parseInt(d[9].substring(2)));

    }

}
