package net.eugenpaul.adventofcode.y2022.day15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day15 extends SolutionTemplate {

    @Setter
    private int part1Line = 2000000;
    @Setter
    private int part2Size = 4000000;

    @Getter
    private long part1;
    @Getter
    private long part2;

    @Setter
    private boolean printArea = false;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2022/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData, part1Line);
        part2 = doPuzzle2Fast(eventData, part2Size);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData, int line) {
        Map<SimplePos, Long> distanceToBeacon = new HashMap<>();
        Set<SimplePos> beacons = new HashSet<>();
        readArea(eventData, distanceToBeacon, beacons);

        int maxDistance = distanceToBeacon.values().stream().mapToInt(Long::intValue).max().orElseThrow();

        int minX = distanceToBeacon.keySet().stream().mapToInt(SimplePos::getX).min().orElseThrow() - maxDistance;
        int maxX = distanceToBeacon.keySet().stream().mapToInt(SimplePos::getX).max().orElseThrow() + maxDistance;

        int response = 0;
        SimplePos testPoint = new SimplePos(0, line);
        for (int x = minX; x <= maxX; x++) {
            testPoint.setX(x);

            if (beacons.contains(testPoint)) {
                continue;
            }

            if (!isPlaceForBeacon(distanceToBeacon, testPoint)) {
                response++;
            }
        }

        return response;
    }

    private boolean isPlaceForBeacon(Map<SimplePos, Long> distanceToBeacon, SimplePos testPoint) {
        for (var v : distanceToBeacon.entrySet()) {
            var distToSensor = v.getKey().manhattanDistance(testPoint);
            var distSB = v.getValue();
            if (distSB >= distToSensor) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unused")
    private long doPuzzle2Slow(List<String> eventData, int max) {
        Map<SimplePos, Long> distanceToBeacon = new HashMap<>();
        Set<SimplePos> beacons = new HashSet<>();
        readArea(eventData, distanceToBeacon, beacons);

        SimplePos testPos = new SimplePos(0, 0);

        for (int y = 0; y <= max; y++) {
            testPos.setY(y);
            int x = 0;
            while (x <= max) {
                testPos.setX(x);

                int deltaX = 1;
                boolean isOk = true;
                for (var entry : distanceToBeacon.entrySet()) {
                    long distToSensor = testPos.manhattanDistance(entry.getKey());
                    long distSB = entry.getValue();

                    if (distSB >= distToSensor) {
                        deltaX = (int) Math.max(deltaX, distSB - distToSensor + 1);
                        isOk = false;
                    }
                }

                if (isOk) {
                    return x * 4000000L + y;
                }

                x += deltaX;
            }
        }

        throw new IllegalArgumentException("solition not found :(");
    }

    private long doPuzzle2Fast(List<String> eventData, int max) {
        Map<SimplePos, Long> distanceToBeacon = new HashMap<>();
        Set<SimplePos> beacons = new HashSet<>();
        readArea(eventData, distanceToBeacon, beacons);

        var ul = new SimplePos(0, 0);
        var dr = new SimplePos(max, max);

        return doStep(distanceToBeacon, ul, dr);
    }

    private long doStep(Map<SimplePos, Long> distanceToBeacon, SimplePos ul, SimplePos dr) {
        if (ul.getX() > dr.getX() || ul.getY() > dr.getY()) {
            return -1;
        }

        if (ul.equals(dr)) {
            if (isPlaceForBeacon(distanceToBeacon, dr)) {
                return dr.getX() * 4000000L + dr.getY();
            }
            return -1;
        }

        SimplePos ur = new SimplePos(dr.getX(), ul.getY());
        SimplePos dl = new SimplePos(ul.getX(), dr.getY());

        for (var entry : distanceToBeacon.entrySet()) {
            long ulToSensor = ul.manhattanDistance(entry.getKey());
            long urToSensor = ur.manhattanDistance(entry.getKey());
            long dlToSensor = dl.manhattanDistance(entry.getKey());
            long drToSensor = dr.manhattanDistance(entry.getKey());
            long distSB = entry.getValue();

            // If the square is completely blocked by the sensor, then abort.
            if (distSB >= ulToSensor //
                    && distSB >= urToSensor //
                    && distSB >= dlToSensor //
                    && distSB >= drToSensor //
            ) {
                return -1;
            }
        }

        // Divide the square into four equal areas and repeat the test.
        int fullX = dr.getX() - ul.getX();
        int halfX = fullX / 2;

        int fullY = dr.getY() - ul.getY();
        int halfY = fullY / 2;

        long resp = -1;
        resp = doStep(//
                distanceToBeacon, //
                ul, //
                ul.addNew(new SimplePos(halfX, halfY)) //
        );
        if (resp != -1) {
            return resp;
        }

        resp = doStep(//
                distanceToBeacon, //
                ul.addNew(new SimplePos(halfX + 1, 0)), //
                ul.addNew(new SimplePos(fullX, halfY)) //
        );
        if (resp != -1) {
            return resp;
        }

        resp = doStep(//
                distanceToBeacon, //
                ul.addNew(new SimplePos(0, halfY + 1)), //
                ul.addNew(new SimplePos(halfX, fullY)) //
        );
        if (resp != -1) {
            return resp;
        }

        resp = doStep(//
                distanceToBeacon, //
                ul.addNew(new SimplePos(halfX + 1, halfY + 1)), //
                dr //
        );
        if (resp != -1) {
            return resp;
        }

        return -1;
    }

    private void readArea(List<String> eventData, Map<SimplePos, Long> distanceToBeacon, Set<SimplePos> beacons) {
        for (var data : eventData) {
            SimplePos sensorPos = new SimplePos(0, 0);
            SimplePos beaconPos = new SimplePos(0, 0);
            readPos(data, sensorPos, beaconPos);

            beacons.add(beaconPos);

            distanceToBeacon.put(sensorPos.copy(), sensorPos.manhattanDistance(beaconPos));
        }
    }

    private void readPos(String data, SimplePos sensorPos, SimplePos beaconPos) {
        var d = data.split(" ");

        sensorPos.setX(Integer.parseInt(d[2].replace(",", "").substring(2)));
        sensorPos.setY(Integer.parseInt(d[3].replace(":", "").substring(2)));

        beaconPos.setX(Integer.parseInt(d[8].replace(",", "").substring(2)));
        beaconPos.setY(Integer.parseInt(d[9].substring(2)));
    }

}
