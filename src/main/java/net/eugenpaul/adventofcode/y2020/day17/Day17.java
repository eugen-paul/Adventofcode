package net.eugenpaul.adventofcode.y2020.day17;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day17 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class Pos4d {
        private int x;
        private int y;
        private int z;
        private int w;

        public List<Pos4d> getNeighbors3d() {
            return List.of(//
                    new Pos4d(x - 1, y - 1, z - 1, w), //
                    new Pos4d(x + 0, y - 1, z - 1, w), //
                    new Pos4d(x + 1, y - 1, z - 1, w), //
                    new Pos4d(x - 1, y + 0, z - 1, w), //
                    new Pos4d(x + 0, y + 0, z - 1, w), //
                    new Pos4d(x + 1, y + 0, z - 1, w), //
                    new Pos4d(x - 1, y + 1, z - 1, w), //
                    new Pos4d(x + 0, y + 1, z - 1, w), //
                    new Pos4d(x + 1, y + 1, z - 1, w), //
                    new Pos4d(x - 1, y - 1, z + 0, w), //
                    new Pos4d(x + 0, y - 1, z + 0, w), //
                    new Pos4d(x + 1, y - 1, z + 0, w), //
                    new Pos4d(x - 1, y + 0, z + 0, w), //
                    new Pos4d(x + 1, y + 0, z + 0, w), //
                    new Pos4d(x - 1, y + 1, z + 0, w), //
                    new Pos4d(x + 0, y + 1, z + 0, w), //
                    new Pos4d(x + 1, y + 1, z + 0, w), //
                    new Pos4d(x - 1, y - 1, z + 1, w), //
                    new Pos4d(x + 0, y - 1, z + 1, w), //
                    new Pos4d(x + 1, y - 1, z + 1, w), //
                    new Pos4d(x - 1, y + 0, z + 1, w), //
                    new Pos4d(x + 0, y + 0, z + 1, w), //
                    new Pos4d(x + 1, y + 0, z + 1, w), //
                    new Pos4d(x - 1, y + 1, z + 1, w), //
                    new Pos4d(x + 0, y + 1, z + 1, w), //
                    new Pos4d(x + 1, y + 1, z + 1, w) //
            );
        }

        public List<Pos4d> getNeighbors4d() {
            return List.of(//
                    new Pos4d(x - 1, y - 1, z - 1, w - 1), //
                    new Pos4d(x + 0, y - 1, z - 1, w - 1), //
                    new Pos4d(x + 1, y - 1, z - 1, w - 1), //
                    new Pos4d(x - 1, y + 0, z - 1, w - 1), //
                    new Pos4d(x + 0, y + 0, z - 1, w - 1), //
                    new Pos4d(x + 1, y + 0, z - 1, w - 1), //
                    new Pos4d(x - 1, y + 1, z - 1, w - 1), //
                    new Pos4d(x + 0, y + 1, z - 1, w - 1), //
                    new Pos4d(x + 1, y + 1, z - 1, w - 1), //
                    new Pos4d(x - 1, y - 1, z + 0, w - 1), //
                    new Pos4d(x + 0, y - 1, z + 0, w - 1), //
                    new Pos4d(x + 1, y - 1, z + 0, w - 1), //
                    new Pos4d(x - 1, y + 0, z + 0, w - 1), //
                    new Pos4d(x + 0, y + 0, z + 0, w - 1), //
                    new Pos4d(x + 1, y + 0, z + 0, w - 1), //
                    new Pos4d(x - 1, y + 1, z + 0, w - 1), //
                    new Pos4d(x + 0, y + 1, z + 0, w - 1), //
                    new Pos4d(x + 1, y + 1, z + 0, w - 1), //
                    new Pos4d(x - 1, y - 1, z + 1, w - 1), //
                    new Pos4d(x + 0, y - 1, z + 1, w - 1), //
                    new Pos4d(x + 1, y - 1, z + 1, w - 1), //
                    new Pos4d(x - 1, y + 0, z + 1, w - 1), //
                    new Pos4d(x + 0, y + 0, z + 1, w - 1), //
                    new Pos4d(x + 1, y + 0, z + 1, w - 1), //
                    new Pos4d(x - 1, y + 1, z + 1, w - 1), //
                    new Pos4d(x + 0, y + 1, z + 1, w - 1), //
                    new Pos4d(x + 1, y + 1, z + 1, w - 1), //
                    new Pos4d(x - 1, y - 1, z - 1, w), //
                    new Pos4d(x + 0, y - 1, z - 1, w), //
                    new Pos4d(x + 1, y - 1, z - 1, w), //
                    new Pos4d(x - 1, y + 0, z - 1, w), //
                    new Pos4d(x + 0, y + 0, z - 1, w), //
                    new Pos4d(x + 1, y + 0, z - 1, w), //
                    new Pos4d(x - 1, y + 1, z - 1, w), //
                    new Pos4d(x + 0, y + 1, z - 1, w), //
                    new Pos4d(x + 1, y + 1, z - 1, w), //
                    new Pos4d(x - 1, y - 1, z + 0, w), //
                    new Pos4d(x + 0, y - 1, z + 0, w), //
                    new Pos4d(x + 1, y - 1, z + 0, w), //
                    new Pos4d(x - 1, y + 0, z + 0, w), //
                    new Pos4d(x + 1, y + 0, z + 0, w), //
                    new Pos4d(x - 1, y + 1, z + 0, w), //
                    new Pos4d(x + 0, y + 1, z + 0, w), //
                    new Pos4d(x + 1, y + 1, z + 0, w), //
                    new Pos4d(x - 1, y - 1, z + 1, w), //
                    new Pos4d(x + 0, y - 1, z + 1, w), //
                    new Pos4d(x + 1, y - 1, z + 1, w), //
                    new Pos4d(x - 1, y + 0, z + 1, w), //
                    new Pos4d(x + 0, y + 0, z + 1, w), //
                    new Pos4d(x + 1, y + 0, z + 1, w), //
                    new Pos4d(x - 1, y + 1, z + 1, w), //
                    new Pos4d(x + 0, y + 1, z + 1, w), //
                    new Pos4d(x + 1, y + 1, z + 1, w), //
                    new Pos4d(x - 1, y - 1, z - 1, w + 1), //
                    new Pos4d(x + 0, y - 1, z - 1, w + 1), //
                    new Pos4d(x + 1, y - 1, z - 1, w + 1), //
                    new Pos4d(x - 1, y + 0, z - 1, w + 1), //
                    new Pos4d(x + 0, y + 0, z - 1, w + 1), //
                    new Pos4d(x + 1, y + 0, z - 1, w + 1), //
                    new Pos4d(x - 1, y + 1, z - 1, w + 1), //
                    new Pos4d(x + 0, y + 1, z - 1, w + 1), //
                    new Pos4d(x + 1, y + 1, z - 1, w + 1), //
                    new Pos4d(x - 1, y - 1, z + 0, w + 1), //
                    new Pos4d(x + 0, y - 1, z + 0, w + 1), //
                    new Pos4d(x + 1, y - 1, z + 0, w + 1), //
                    new Pos4d(x - 1, y + 0, z + 0, w + 1), //
                    new Pos4d(x + 0, y + 0, z + 0, w + 1), //
                    new Pos4d(x + 1, y + 0, z + 0, w + 1), //
                    new Pos4d(x - 1, y + 1, z + 0, w + 1), //
                    new Pos4d(x + 0, y + 1, z + 0, w + 1), //
                    new Pos4d(x + 1, y + 1, z + 0, w + 1), //
                    new Pos4d(x - 1, y - 1, z + 1, w + 1), //
                    new Pos4d(x + 0, y - 1, z + 1, w + 1), //
                    new Pos4d(x + 1, y - 1, z + 1, w + 1), //
                    new Pos4d(x - 1, y + 0, z + 1, w + 1), //
                    new Pos4d(x + 0, y + 0, z + 1, w + 1), //
                    new Pos4d(x + 1, y + 0, z + 1, w + 1), //
                    new Pos4d(x - 1, y + 1, z + 1, w + 1), //
                    new Pos4d(x + 0, y + 1, z + 1, w + 1), //
                    new Pos4d(x + 1, y + 1, z + 1, w + 1) //
            );
        }
    }

    @Getter
    private int activeCubes;
    @Getter
    private int activeCubes2;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2020/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        activeCubes = doPuzzle1(eventData);
        activeCubes2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "activeCubes   : " + getActiveCubes());
        logger.log(Level.INFO, () -> "activeCubes2  : " + getActiveCubes2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        Set<SimplePos> z0 = StringConverter.toSet(eventData, '#');
        Set<Pos4d> fullSet = z0.stream().map(v -> new Pos4d(v.getX(), v.getY(), 0, 0)).collect(Collectors.toSet());

        for (int i = 0; i < 6; i++) {
            Set<Pos4d> nextState = new HashSet<>();

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxZ = Integer.MIN_VALUE;

            for (Pos4d pos4d : fullSet) {
                minX = Math.min(minX, pos4d.getX() - 1);
                maxX = Math.max(maxX, pos4d.getX() + 1);
                minY = Math.min(minY, pos4d.getY() - 1);
                maxY = Math.max(maxY, pos4d.getY() + 1);
                minZ = Math.min(minZ, pos4d.getZ() - 1);
                maxZ = Math.max(maxZ, pos4d.getZ() + 1);
            }

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Pos4d currentPos = new Pos4d(x, y, z, 0);
                        boolean isActive = fullSet.contains(currentPos);
                        int activeNeighbors = getActiveNeighbors3d(fullSet, currentPos);
                        if (isActive && (activeNeighbors == 2 || activeNeighbors == 3) //
                                || !isActive && activeNeighbors == 3 //
                        ) {
                            nextState.add(currentPos);
                        }
                    }
                }
            }

            fullSet = nextState;
        }

        return fullSet.size();
    }

    private int doPuzzle2(List<String> eventData) {
        Set<SimplePos> z0 = StringConverter.toSet(eventData, '#');
        Set<Pos4d> fullSet = z0.stream().map(v -> new Pos4d(v.getX(), v.getY(), 0, 0)).collect(Collectors.toSet());

        for (int i = 0; i < 6; i++) {
            Set<Pos4d> nextState = new HashSet<>();

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxZ = Integer.MIN_VALUE;
            int minW = Integer.MAX_VALUE;
            int maxW = Integer.MIN_VALUE;

            for (Pos4d pos4d : fullSet) {
                minX = Math.min(minX, pos4d.getX() - 1);
                maxX = Math.max(maxX, pos4d.getX() + 1);
                minY = Math.min(minY, pos4d.getY() - 1);
                maxY = Math.max(maxY, pos4d.getY() + 1);
                minZ = Math.min(minZ, pos4d.getZ() - 1);
                maxZ = Math.max(maxZ, pos4d.getZ() + 1);
                minW = Math.min(minW, pos4d.getW() - 1);
                maxW = Math.max(maxW, pos4d.getW() + 1);
            }

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        for (int w = minW; w <= maxW; w++) {
                            Pos4d currentPos = new Pos4d(x, y, z, w);
                            boolean isActive = fullSet.contains(currentPos);
                            int activeNeighbors = getActiveNeighbors4d(fullSet, currentPos);
                            if (isActive && (activeNeighbors == 2 || activeNeighbors == 3) //
                                    || !isActive && activeNeighbors == 3 //
                            ) {
                                nextState.add(currentPos);
                            }
                        }
                    }
                }
            }

            fullSet = nextState;
        }

        return fullSet.size();
    }

    private int getActiveNeighbors3d(Set<Pos4d> area, Pos4d pos) {
        int response = 0;

        for (Pos4d toCheck : pos.getNeighbors3d()) {
            if (area.contains(toCheck)) {
                response++;
            }
        }

        return response;
    }

    private int getActiveNeighbors4d(Set<Pos4d> area, Pos4d pos) {
        int response = 0;

        for (Pos4d toCheck : pos.getNeighbors4d()) {
            if (area.contains(toCheck)) {
                response++;
            }
        }

        return response;
    }

}
