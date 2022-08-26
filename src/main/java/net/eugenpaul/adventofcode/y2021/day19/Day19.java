package net.eugenpaul.adventofcode.y2021.day19;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToLongFunction;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    private class Scanner {
        private int number;
        private Set<Pos3d> beacons;
        private Set<Pos3d> scanners;

        public Scanner() {
            beacons = new HashSet<>();
            scanners = new HashSet<>();
        }
    }

    @Getter
    private long beaconscount;
    @Getter
    private long maxDistance;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2021/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var scanners = initScanners(eventData);

        var complete = doPuzzle1(scanners);

        beaconscount = complete.beacons.size();

        maxDistance = Long.MIN_VALUE;

        for (var setA : complete.scanners) {
            for (var setB : complete.scanners) {
                maxDistance = Math.max(maxDistance, setA.manhattanDistance(setB));
            }
        }

        logger.log(Level.INFO, () -> "beaconscount  : " + getBeaconscount());
        logger.log(Level.INFO, () -> "maxDistance   : " + getMaxDistance());

        return true;
    }

    private Scanner doPuzzle1(Map<Integer, Scanner> scanners) {

        while (scanners.size() > 1) {
            boolean found = false;
            List<Scanner> workList = new LinkedList<>(scanners.values());
            for (int a = 0; a < workList.size() && !found; a++) {
                for (int b = a + 1; b < workList.size() && !found; b++) {
                    Scanner merged = merge(workList.get(a), workList.get(b));
                    if (merged != null) {
                        found = true;
                        scanners.remove(workList.get(a).number);
                        scanners.remove(workList.get(b).number);
                        scanners.put(merged.number, merged);
                    }
                }
            }
        }

        return scanners.values().stream().findFirst().orElseThrow();
    }

    private Scanner merge(Scanner a, Scanner b) {
        Set<Pos3d> aBeacons = a.beacons;
        Set<Set<Pos3d>> bBeacons = getAllOrientations(b.beacons);
        List<Pos3d> deltaResponse = new LinkedList<>();

        for (var bVar : bBeacons) {
            var m = merge(aBeacons, bVar, deltaResponse);
            if (!m.isEmpty()) {
                var response = new Scanner();
                response.number = a.number;
                response.beacons = m;

                response.scanners = new HashSet<>(a.scanners);
                response.scanners.addAll(//
                        b.scanners.stream()//
                                .map(v -> v.addNew(deltaResponse.get(0))) //
                                .collect(Collectors.toSet())//
                );

                return response;
            }
        }

        return null;
    }

    private Set<Pos3d> merge(Set<Pos3d> a, Set<Pos3d> b, List<Pos3d> deltaResponse) {

        for (Pos3d aRef : a) {
            for (Pos3d bRef : b) {
                long deltaX = aRef.getX() - bRef.getX();
                long deltaY = aRef.getY() - bRef.getY();
                long deltaZ = aRef.getZ() - bRef.getZ();

                Set<Pos3d> bDelta = b.stream()//
                        .map(v -> new Pos3d(v.getX() + deltaX, v.getY() + deltaY, v.getZ() + deltaZ))//
                        .collect(Collectors.toSet());

                Set<Pos3d> overlapps = bDelta.stream()//
                        .filter(a::contains)//
                        .collect(Collectors.toSet());

                if (overlapps.size() >= 12) {
                    Set<Pos3d> response = new HashSet<>(a);
                    response.addAll(bDelta.stream()//
                            .filter(v -> !a.contains(v))//
                            .collect(Collectors.toList()));

                    deltaResponse.add(new Pos3d(deltaX, deltaY, deltaZ));
                    return response;
                }
            }
        }

        return Collections.emptySet();
    }

    private Set<Set<Pos3d>> getAllOrientations(Set<Pos3d> beacons) {

        Set<Set<Pos3d>> response = new HashSet<>();

        response.addAll(getAllDirection(beacons, Pos3d::getX, Pos3d::getY, Pos3d::getZ));
        response.addAll(getAllDirection(beacons, Pos3d::getX, Pos3d::getZ, Pos3d::getY));
        response.addAll(getAllDirection(beacons, Pos3d::getY, Pos3d::getZ, Pos3d::getX));
        response.addAll(getAllDirection(beacons, Pos3d::getY, Pos3d::getX, Pos3d::getZ));
        response.addAll(getAllDirection(beacons, Pos3d::getZ, Pos3d::getX, Pos3d::getY));
        response.addAll(getAllDirection(beacons, Pos3d::getZ, Pos3d::getY, Pos3d::getX));

        return response;
    }

    private Set<Set<Pos3d>> getAllDirection(Set<Pos3d> beacons, ToLongFunction<Pos3d> x, ToLongFunction<Pos3d> y, ToLongFunction<Pos3d> z) {
        Set<Set<Pos3d>> response = new HashSet<>();

        for (long xDir = -1; xDir < 2; xDir = xDir + 2) {
            for (long yDir = -1; yDir < 2; yDir = yDir + 2) {
                for (long zDir = -1; zDir < 2; zDir = zDir + 2) {
                    Set<Pos3d> changed = new HashSet<>();
                    for (var pos : beacons) {
                        changed.add(new Pos3d(x.applyAsLong(pos) * xDir, y.applyAsLong(pos) * yDir, z.applyAsLong(pos) * zDir));
                    }
                    response.add(changed);
                }
            }
        }

        return response;

    }

    private Map<Integer, Scanner> initScanners(List<String> eventData) {
        Map<Integer, Scanner> response = new HashMap<>();

        Scanner currentScanner = new Scanner();

        for (String data : eventData) {
            if (data.isBlank()) {
                continue;
            }

            if (data.startsWith("---")) {
                currentScanner = new Scanner();
                currentScanner.number = Integer.parseInt(data.split(" ")[2]);
                currentScanner.scanners.add(new Pos3d(0, 0, 0));
                response.put(//
                        currentScanner.number, //
                        currentScanner //
                );
            } else {
                currentScanner.beacons.add(Pos3d.fromPattern(data, ","));
            }

        }

        return response;
    }
}
