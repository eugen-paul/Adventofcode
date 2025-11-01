package net.eugenpaul.adventofcode.y2023.day24;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @Setter
    private boolean testA = false;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2023/day24/puzzle1.txt");
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

        for (int a = 0; a < eventData.size(); a++) {
            String dataA = eventData.get(a);
            var splitsA = dataA.replace(" ", "").split("@");
            Pos3d posA = Pos3d.fromPattern(splitsA[0], ",");
            Pos3d velA = Pos3d.fromPattern(splitsA[1], ",");
            for (int b = a + 1; b < eventData.size(); b++) {
                String dataB = eventData.get(b);
                var splitsB = dataB.replace(" ", "").split("@");
                Pos3d posB = Pos3d.fromPattern(splitsB[0], ",");
                Pos3d velB = Pos3d.fromPattern(splitsB[1], ",");

                if (is2dIntersection(posA, velA, posB, velB)) {
                    response++;
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private boolean is2dIntersection(Pos3d posA, Pos3d velA, Pos3d posB, Pos3d velB) {
        long minX = 200000000000000L;
        long maxX = 400000000000000L;
        long minY = 200000000000000L;
        long maxY = 400000000000000L;

        if (testA) {
            minX = 7;
            maxX = 27;
            minY = 7;
            maxY = 27;
        }

        double b = (double) ((velA.getX() * posA.getY() + posB.getX() * velA.getY() - posA.getX() * velA.getY() - posB.getY() * velA.getX()))
                / (velB.getY() * velA.getX() - velB.getX() * velA.getY());

        double x = posB.getX() + b * velB.getX();
        double y = posB.getY() + b * velB.getY();

        if (x < minX || maxX < x || y < minY || maxY < y) {
            return false;
        }

        boolean ax = (posA.getX() < x && velA.getX() > 0) || (posA.getX() > x && velA.getX() < 0);
        boolean ay = (posA.getY() < y && velA.getY() > 0) || (posA.getY() > y && velA.getY() < 0);

        boolean bx = (posB.getX() < x && velB.getX() > 0) || (posB.getX() > x && velB.getX() < 0);
        boolean by = (posB.getY() < y && velB.getY() > 0) || (posB.getY() > y && velB.getY() < 0);

        return ax && ay && bx && by;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Set<Integer> xVelocitys = new HashSet<>();
        Set<Integer> yVelocitys = new HashSet<>();
        Set<Integer> zVelocitys = new HashSet<>();

        for (int a = 0; a < eventData.size(); a++) {
            String dataA = eventData.get(a);
            var splitsA = dataA.replace(" ", "").split("@");
            Pos3d posA = Pos3d.fromPattern(splitsA[0], ",");
            Pos3d velA = Pos3d.fromPattern(splitsA[1], ",");
            for (int b = a + 1; b < eventData.size(); b++) {
                String dataB = eventData.get(b);
                var splitsB = dataB.replace(" ", "").split("@");
                Pos3d posB = Pos3d.fromPattern(splitsB[0], ",");
                Pos3d velB = Pos3d.fromPattern(splitsB[1], ",");

                // if X-Velocity of A and B are equal, then stone X-Velocity is divisor of X-Distance.
                // Same for Y and Z
                if (velA.getX() == velB.getX() && xVelocitys.size() != 1) {
                    if (xVelocitys.isEmpty()) {
                        xVelocitys.addAll(computeVelocities(posA.getX(), posB.getX(), (int)velA.getX()));
                    } else {
                        xVelocitys.retainAll(computeVelocities(posA.getX(), posB.getX(), (int)velA.getX()));
                    }
                }
                if (velA.getY() == velB.getY() && yVelocitys.size() != 1) {
                    if (yVelocitys.isEmpty()) {
                        yVelocitys.addAll(computeVelocities(posA.getY(), posB.getY(), (int)velA.getY()));
                    } else {
                        yVelocitys.retainAll(computeVelocities(posA.getY(), posB.getY(), (int)velA.getY()));
                    }
                }
                if (velA.getZ() == velB.getZ() && zVelocitys.size() != 1) {
                    if (zVelocitys.isEmpty()) {
                        zVelocitys.addAll(computeVelocities(posA.getZ(), posB.getZ(), (int)velA.getZ()));
                    } else {
                        zVelocitys.retainAll(computeVelocities(posA.getZ(), posB.getZ(), (int)velA.getZ()));
                    }
                }
            }
        }

        
        String dataA = eventData.get(0);
        var splitsA = dataA.replace(" ", "").split("@");
        Pos3d posA = Pos3d.fromPattern(splitsA[0], ",");
        Pos3d velA = Pos3d.fromPattern(splitsA[1], ",");

        String dataB = eventData.get(1);
        var splitsB = dataB.replace(" ", "").split("@");
        Pos3d posB = Pos3d.fromPattern(splitsB[0], ",");
        Pos3d velB = Pos3d.fromPattern(splitsB[1], ",");

        long vx = xVelocitys.iterator().next();
        long vy = yVelocitys.iterator().next();
        long vz = zVelocitys.iterator().next();

        long c = (velB.getX() - vx) * (velA.getY() - vy);
        long d = (posB.getX() - posA.getX()) * (velA.getY() - vy);
        long e = (velB.getY() - vy) * (velA.getX() - vx);
        long f = (posB.getY() - posA.getY()) * (velA.getX() - vx);
        
        long t2 = (f-d)/ (c-e);

        long x = (posB.getX() + t2 * velB.getX()) - (vx * t2);
        long y = (posB.getY() + t2 * velB.getY()) - (vy * t2);
        long z = (posB.getZ() + t2 * velB.getZ()) - (vz * t2);
        
        response = x+y+z;
        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private Set<Integer> computeVelocities(long posA, long posB, int velA) {
        Set<Integer> velocitys = new HashSet<>();

        long distance = Math.abs(posA - posB);
        long sqrtDistance = (long) Math.sqrt(distance);
        for (int v = 1; v <= sqrtDistance; v++) {
            if (distance % v == 0) {
                velocitys.add(v + velA);
                velocitys.add((int) (distance / v) + velA);
                velocitys.add(-v + velA);
                velocitys.add(-(int) (distance / v) + velA);
            }
        }

        return velocitys;
    }

}
