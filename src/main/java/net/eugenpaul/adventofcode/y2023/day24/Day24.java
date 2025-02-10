package net.eugenpaul.adventofcode.y2023.day24;

import java.util.List;
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

        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;
        long minZ = Long.MAX_VALUE;
        long maxZ = Long.MIN_VALUE;

        for (int a = 0; a < eventData.size(); a++) {
            String dataA = eventData.get(a);
            var splitsA = dataA.replace(" ", "").split("@");
            Pos3d posA = Pos3d.fromPattern(splitsA[0], ",");
            Pos3d velA = Pos3d.fromPattern(splitsA[1], ",");
            if (velA.getX() < 0) {
                minX = Math.min(minX, posA.getX());
            }
            if (velA.getX() > 0) {
                maxX = Math.max(maxX, posA.getX());
            }
            if (velA.getY() < 0) {
                minY = Math.min(minY, posA.getY());
            }
            if (velA.getY() > 0) {
                maxY = Math.max(maxY, posA.getY());
            }
            if (velA.getZ() < 0) {
                minZ = Math.min(minZ, posA.getZ());
            }
            if (velA.getZ() > 0) {
                maxZ = Math.max(maxZ, posA.getZ());
            }
        }

        System.out.println(minX + " " + maxX);
        System.out.println(minY + " " + maxY);
        System.out.println(minZ + " " + maxZ);

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
