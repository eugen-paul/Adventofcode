package net.eugenpaul.adventofcode.y2024.day14;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    private static record Robot(int startX, int startY, int vX, int vY) {
        public static Robot fromLine(String l) {
            String[] sp = l.split(" ");
            String[] spPos = sp[0].substring(2).split(",");
            String[] spV = sp[1].substring(2).split(",");
            int startX = Integer.parseInt(spPos[0]);
            int startY = Integer.parseInt(spPos[1]);
            int vX = Integer.parseInt(spV[0]);
            int vY = Integer.parseInt(spV[1]);
            return new Robot(startX, startY, vX, vY);
        }
    }

    @Setter
    private int roomSizeX = 101;
    @Setter
    private int roomSizeY = 103;

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2024/day14/puzzle1.txt");
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

        List<Robot> robots = eventData.stream().map(Robot::fromLine).toList();

        Map<SimplePos, Integer> endPos = new HashMap<>();

        for (var r : robots) {
            long x = (r.startX + r.vX * 100) % roomSizeX;
            if (x < 0)
                x += roomSizeX;
            long y = (r.startY + r.vY * 100) % roomSizeY;
            if (y < 0)
                y += roomSizeY;
            endPos.compute(new SimplePos((int) x, (int) y), (k, v) -> v == null ? 1 : v + 1);
        }

        long q1 = 0;
        long q2 = 0;
        long q3 = 0;
        long q4 = 0;

        int x1End = roomSizeX / 2 - 1;
        int x2start = roomSizeX / 2 + 1;
        int y1End = roomSizeY / 2 - 1;
        int y2start = roomSizeY / 2 + 1;

        for (var p : endPos.entrySet()) {
            if (p.getKey().inRange(0, x1End, 0, y1End)) {
                q1 += p.getValue();
            } else if (p.getKey().inRange(x2start, roomSizeX, 0, y1End)) {
                q2 += p.getValue();
            } else if (p.getKey().inRange(0, x1End, y2start, roomSizeY)) {
                q3 += p.getValue();
            } else if (p.getKey().inRange(x2start, roomSizeX, y2start, roomSizeY)) {
                q4 += p.getValue();
            }
        }

        response = q1 * q2 * q3 * q4;

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Robot> robots = eventData.stream().map(Robot::fromLine).toList();

        //BF
        for (int i = 6668; i == 6668; i = i + 101) {
            Map<SimplePos, Integer> endPos = new HashMap<>();
            for (var r : robots) {
                long x = (r.startX + r.vX * i) % roomSizeX;
                if (x < 0)
                    x += roomSizeX;
                long y = (r.startY + r.vY * i) % roomSizeY;
                if (y < 0)
                    y += roomSizeY;
                endPos.compute(new SimplePos((int) x, (int) y), (k, v) -> v == null ? 1 : v + 1);
            }

            MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(endPos.keySet()));
            System.out.println(i);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

}
