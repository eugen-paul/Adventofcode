package net.eugenpaul.adventofcode.y2016.day17;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Dijkstra {

    @AllArgsConstructor
    @Data
    private static class Pos {
        String hash;
        int x;
        int y;
    }

    private LinkedList<Pos> steps;

    public Dijkstra() {
        steps = new LinkedList<>();
    }

    public String getSteps(BuildingArea area, String passcode, int fromX, int fromY, int toX, int toY) {
        Pos to = new Pos(passcode, toX, toY);
        Pos from = new Pos(passcode, fromX, fromY);

        // pathfinding
        while (to.getX() != from.getX() || to.getY() != from.getY()) {
            checkAndAddNextSteps(area, from);
            from = steps.poll();
        }

        return from.getHash().substring(passcode.length());
    }

    public int getLongethPathLength(BuildingArea area, String passcode, int fromX, int fromY, int toX, int toY) {
        Pos to = new Pos(passcode, toX, toY);
        Pos from = new Pos(passcode, fromX, fromY);

        int longestPathLength = 0;

        while (from != null) {
            if (to.getX() != from.getX() || to.getY() != from.getY()) {
                checkAndAddNextSteps(area, from);
            } else {
                longestPathLength = from.getHash().substring(passcode.length()).length();
            }
            from = steps.poll();
        }

        return longestPathLength;
    }

    private void checkAndAddNextSteps(BuildingArea area, Pos src) {
        List<Doors> openDoors = area.getOpenDoors(src.getHash(), src.getX(), src.getY());

        steps.addAll(//
                openDoors.stream()//
                        .map(v -> new Pos(//
                                src.getHash() + v.getValue(), //
                                src.getX() + v.getXChange(), //
                                src.getY() + v.getYChange())//
                        )//
                        .collect(Collectors.toList())//
        );
    }
}
