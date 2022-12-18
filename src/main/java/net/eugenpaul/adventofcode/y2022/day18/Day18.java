package net.eugenpaul.adventofcode.y2022.day18;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2022/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        Map<Pos3d, Integer> area = computeSurfaces(eventData);

        return area.values().stream().filter(v -> v >= 1).mapToInt(v -> v).sum();
    }

    private long doPuzzle2(List<String> eventData) {
        Map<Pos3d, Integer> area = computeSurfaces(eventData);

        Set<Pos3d> watterArea = computeWatter(area);

        return area.entrySet().stream()//
                .filter(v -> v.getValue() >= 1)//
                .filter(v -> watterArea.contains(v.getKey()))//
                .mapToInt(Entry::getValue).sum();
    }

    private Set<Pos3d> computeWatter(Map<Pos3d, Integer> lavaArea) {
        Set<Pos3d> watterArea = new HashSet<>();
        LinkedList<Pos3d> toCheck = new LinkedList<>();
        toCheck.add(new Pos3d(-1, -1, -1));
        watterArea.add(new Pos3d(-1, -1, -1));

        while (!toCheck.isEmpty()) {
            Pos3d pos = toCheck.removeFirst();
            var neighbors = getNeighbors(pos);
            for (Pos3d nPos : neighbors) {
                if (isOk(lavaArea, watterArea, nPos)) {
                    watterArea.add(nPos);
                    toCheck.add(nPos);
                }
            }
        }
        return watterArea;
    }

    private boolean isOk(Map<Pos3d, Integer> lavaArea, Set<Pos3d> watterArea, Pos3d pos) {
        if (pos.getX() < -1 || pos.getY() < -1 || pos.getZ() < -1 //
                || pos.getX() > 22 || pos.getY() > 22 || pos.getZ() > 22 //
        ) {
            return false;
        }
        if (watterArea.contains(pos)) {
            return false;
        }
        if (lavaArea.containsKey(pos) && lavaArea.get(pos) == -1) {
            return false;
        }
        return true;
    }

    private Map<Pos3d, Integer> computeSurfaces(List<String> eventData) {
        List<Pos3d> allPoints = readPoints(eventData);

        Map<Pos3d, Integer> area = new HashMap<>();

        for (Pos3d pos : allPoints) {
            area.put(pos, -1);
        }

        for (Pos3d pos : allPoints) {
            List<Pos3d> neighbors = getNeighbors(pos);
            for (var n : neighbors) {
                if (!area.containsKey(n)) {
                    area.put(n, 1);
                } else if (area.get(n) != -1) {
                    area.put(n, area.get(n) + 1);
                }
            }
        }
        return area;
    }

    private List<Pos3d> getNeighbors(Pos3d point) {
        List<Pos3d> response = new LinkedList<>();
        response.add(new Pos3d(point.getX(), point.getY() + 1, point.getZ()));
        response.add(new Pos3d(point.getX(), point.getY() - 1, point.getZ()));
        response.add(new Pos3d(point.getX(), point.getY(), point.getZ() + 1));
        response.add(new Pos3d(point.getX(), point.getY(), point.getZ() - 1));
        response.add(new Pos3d(point.getX() + 1, point.getY(), point.getZ()));
        response.add(new Pos3d(point.getX() - 1, point.getY(), point.getZ()));
        return response;
    }

    private List<Pos3d> readPoints(List<String> eventData) {
        List<Pos3d> response = new LinkedList<>();
        for (var data : eventData) {
            response.add(Pos3d.fromPattern(data, ","));
        }
        return response;
    }

}
