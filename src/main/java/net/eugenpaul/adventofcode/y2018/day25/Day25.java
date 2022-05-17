package net.eugenpaul.adventofcode.y2018.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class Point4d {
        private int x;
        private int y;
        private int z;
        private int t;
    }

    @Getter
    private int constellations;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2018/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Point4d> points = eventData.stream()//
                .map(this::toPoint)//
                .collect(Collectors.toList());

        Map<Integer, List<Point4d>> groups = new HashMap<>();

        initGroups(points, groups);

        boolean groupFound = true;
        while (groupFound) {
            groupFound = false;
            List<Integer> groupNumbers = new ArrayList<>(groups.keySet());
            for (int baseGroup = 0; baseGroup < groupNumbers.size(); baseGroup++) {
                var groupA = groups.get(groupNumbers.get(baseGroup));
                for (int checkGroup = baseGroup + 1; checkGroup < groupNumbers.size(); checkGroup++) {
                    var groupB = groups.get(groupNumbers.get(checkGroup));
                    if (isInConstellation(groupA, groupB)) {
                        groupA.addAll(groupB);
                        groups.remove(groupNumbers.get(checkGroup));
                        groupFound = true;
                        break;
                    }
                }
                if (groupFound) {
                    break;
                }
            }
        }

        constellations = groups.size();

        logger.log(Level.INFO, () -> "constellations : " + getConstellations());

        return true;
    }

    private void initGroups(List<Point4d> points, Map<Integer, List<Point4d>> groups) {
        int groupCounter = 0;
        for (Point4d point : points) {
            var group = groups.entrySet().stream()//
                    .filter(v -> isInConstellation(point, v.getValue()))//
                    .findFirst();

            if (group.isPresent()) {
                group.get().getValue().add(point);
            } else {
                groupCounter++;
                List<Point4d> newGroup = new LinkedList<>();
                newGroup.add(point);
                groups.put(groupCounter, newGroup);
            }
        }
    }

    private Point4d toPoint(String data) {
        String[] elements = data.split(",");
        return new Point4d(//
                Integer.parseInt(elements[0]), //
                Integer.parseInt(elements[1]), //
                Integer.parseInt(elements[2]), //
                Integer.parseInt(elements[3]) //
        );
    }

    private int computeDistance(Point4d a, Point4d b) {
        return Math.abs(b.x - a.x) //
                + Math.abs(b.y - a.y) //
                + Math.abs(b.z - a.z) //
                + Math.abs(b.t - a.t) //
        ;
    }

    private boolean isInConstellation(Point4d a, Point4d b) {
        return computeDistance(a, b) <= 3;
    }

    private boolean isInConstellation(Point4d a, List<Point4d> group) {
        return group.stream().anyMatch(v -> isInConstellation(v, a));
    }

    private boolean isInConstellation(List<Point4d> groupA, List<Point4d> groupB) {
        return groupA.stream().anyMatch(v -> isInConstellation(v, groupB));
    }
}
