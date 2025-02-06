package net.eugenpaul.adventofcode.y2023.day22;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private static int globalId = 0;

    @AllArgsConstructor
    private static class Brick {
        int id;

        long minX, maxX;
        long minY, maxY;
        long minZ, maxZ;

        Brick(Pos3d a, Pos3d b) {
            this.id = globalId++;

            minX = Math.min(a.getX(), b.getX());
            maxX = Math.max(a.getX(), b.getX());

            minY = Math.min(a.getY(), b.getY());
            maxY = Math.max(a.getY(), b.getY());

            minZ = Math.min(a.getZ(), b.getZ());
            maxZ = Math.max(a.getZ(), b.getZ());
        }

        void fall() {
            minZ--;
            maxZ--;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Brick o) //
                    && o.minX == this.minX //
                    && o.maxX == this.maxX //
                    && o.minY == this.minY //
                    && o.maxY == this.maxY //
                    && o.minZ == this.minZ //
                    && o.maxZ == this.maxZ;
        }

        static Brick read(String data) {
            var splits = data.split("~");
            return new Brick(Pos3d.fromPattern(splits[0], ","), Pos3d.fromPattern(splits[1], ","));
        }

        Brick copy() {
            return new Brick(id, minX, maxX, minY, maxY, minZ, maxZ);
        }
    }

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2023/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Brick> bricks = eventData.stream().map(Brick::read).toList();

        bricks = bricks.stream().sorted((a, b) -> Long.compare(a.minZ, b.minZ)).toList();

        List<Brick> target = new ArrayList<>();
        for (var brick : bricks) {
            var check = brick.copy();
            while (check.minZ > 1) {
                var copy = check.copy();
                copy.fall();

                var ok = target.stream().allMatch(t -> {
                    var zOk = t.maxZ < copy.minZ || copy.maxZ < t.minZ;
                    var xOk = t.maxX < copy.minX || copy.maxX < t.minX;
                    var yOk = t.maxY < copy.minY || copy.maxY < t.minY;
                    return zOk || xOk || yOk;
                });

                if (!ok) {
                    break;
                }
                check = copy;
            }
            target.add(check);
        }

        Set<Integer> need = new HashSet<>();

        for (int i = target.size() - 1; i >= 0; i--) {
            var check = target.get(i).copy();
            check.fall();
            var dd = new ArrayList<Integer>();
            for (int k = i - 1; k >= 0; k--) {
                var t = target.get(k);
                var zOk = t.maxZ < check.minZ || check.maxZ < t.minZ;
                var xOk = t.maxX < check.minX || check.maxX < t.minX;
                var yOk = t.maxY < check.minY || check.maxY < t.minY;
                if (!zOk && !xOk && !yOk) {
                    dd.add(t.id);
                }
            }
            if (dd.size() == 1) {
                need.add(dd.get(0));
            }
        }

        response = (long) target.size() - need.size();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        List<Brick> bricks = eventData.stream().map(Brick::read).toList();

        bricks = bricks.stream().sorted((a, b) -> Long.compare(a.minZ, b.minZ)).toList();

        List<Brick> target = new ArrayList<>();
        for (var brick : bricks) {
            var check = brick.copy();
            while (check.minZ > 1) {
                var copy = check.copy();
                copy.fall();

                var ok = target.stream().allMatch(t -> {
                    var zOk = t.maxZ < copy.minZ || copy.maxZ < t.minZ;
                    var xOk = t.maxX < copy.minX || copy.maxX < t.minX;
                    var yOk = t.maxY < copy.minY || copy.maxY < t.minY;
                    return zOk || xOk || yOk;
                });

                if (!ok) {
                    break;
                }
                check = copy;
            }
            target.add(check);
        }

        for (int i = 0; i < target.size(); i++) {
            var removed = target.remove(i);
            response += cntFalls(target);
            target.add(i, removed);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private int cntFalls(List<Brick> bricks) {
        int r = 0;
        List<Brick> target = new ArrayList<>();
        for (var brick : bricks) {
            var check = brick.copy();
            while (check.minZ > 1) {
                var copy = check.copy();
                copy.fall();

                var ok = target.stream().allMatch(t -> {
                    var zOk = t.maxZ < copy.minZ || copy.maxZ < t.minZ;
                    var xOk = t.maxX < copy.minX || copy.maxX < t.minX;
                    var yOk = t.maxY < copy.minY || copy.maxY < t.minY;
                    return zOk || xOk || yOk;
                });

                if (!ok) {
                    break;
                }
                check = copy;
            }
            if (!brick.equals(check)) {
                r++;
            }
            target.add(check);
        }
        return r;
    }

}
