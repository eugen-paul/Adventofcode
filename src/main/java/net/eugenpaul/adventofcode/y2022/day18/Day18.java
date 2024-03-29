package net.eugenpaul.adventofcode.y2022.day18;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
        Set<Pos3d> area = readPoints(eventData);

        return area.stream()//
                .flatMap(v -> v.getNeighbors().stream())//
                .filter(v -> !area.contains(v))//
                .count();
    }

    private long doPuzzle2(List<String> eventData) {
        Set<Pos3d> area = readPoints(eventData);

        Set<Pos3d> watterArea = computeWatter(area);

        return area.stream()//
                .mapMulti((v, c) -> v.getNeighbors().forEach(c))//
                .filter(v -> !area.contains(v))//
                .filter(watterArea::contains)//
                .count();
    }

    private Set<Pos3d> computeWatter(Set<Pos3d> lavaArea) {
        long maxX = lavaArea.stream().mapToLong(Pos3d::getX).max().orElseThrow();
        long minX = lavaArea.stream().mapToLong(Pos3d::getX).min().orElseThrow();
        long maxY = lavaArea.stream().mapToLong(Pos3d::getY).max().orElseThrow();
        long minY = lavaArea.stream().mapToLong(Pos3d::getY).min().orElseThrow();
        long maxZ = lavaArea.stream().mapToLong(Pos3d::getZ).max().orElseThrow();
        long minZ = lavaArea.stream().mapToLong(Pos3d::getZ).min().orElseThrow();

        Pos3d min = new Pos3d(minX - 1, minY - 1, minZ - 1);
        Pos3d max = new Pos3d(maxX + 1, maxY + 1, maxZ + 1);

        Set<Pos3d> watterArea = new HashSet<>();
        LinkedList<Pos3d> toCheck = new LinkedList<>();
        toCheck.add(min);
        watterArea.add(min);

        while (!toCheck.isEmpty()) {
            Pos3d pos = toCheck.removeFirst();
            var neighbors = pos.getNeighbors();

            for (Pos3d nPos : neighbors) {
                if (isOk(lavaArea, watterArea, nPos, min, max)) {
                    watterArea.add(nPos);
                    toCheck.add(nPos);
                }
            }
        }
        return watterArea;
    }

    private boolean isOk(Set<Pos3d> lavaArea, Set<Pos3d> watterArea, Pos3d pos, Pos3d min, Pos3d max) {
        if (pos.getX() < min.getX() || pos.getY() < min.getY() || pos.getZ() < min.getZ() //
                || pos.getX() > max.getX() || pos.getY() > max.getY() || pos.getZ() > max.getZ() //
        ) {
            return false;
        }
        if (watterArea.contains(pos)) {
            return false;
        }
        if (lavaArea.contains(pos)) {
            return false;
        }
        return true;
    }

    private Set<Pos3d> readPoints(List<String> eventData) {
        return eventData.stream()//
                .map(v -> Pos3d.fromPattern(v, ","))//
                .collect(Collectors.toSet());
    }

}
