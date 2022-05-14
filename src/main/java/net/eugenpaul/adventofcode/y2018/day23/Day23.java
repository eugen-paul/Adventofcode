package net.eugenpaul.adventofcode.y2018.day23;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day23 extends SolutionTemplate {
    private static final String NANOBOT_FORMAT = "^pos=<([\\-0-9]*),([\\-0-9]*),([\\-0-9]*)>, r=([\\-0-9]*)$";
    private static final Pattern NANOBOT_PATTERN = Pattern.compile(NANOBOT_FORMAT);

    @AllArgsConstructor
    @Data
    private class Nanobot {

        private long x;
        private long y;
        private long z;
        private long range;

        public Area toArea() {
            Pos3d a = new Pos3d(x, y, z + range);
            Pos3d b = new Pos3d(x, y, z - range);
            return new Area(a, b);
        }
    }

    @AllArgsConstructor
    @Data
    private class Area {
        private Pos3d t;
        private Pos3d b;

        public Pos3d getP1() {
            long deltaX = t.getX() - b.getX();
            long deltaY = t.getY() - b.getY();
            long deltaZ = t.getZ() - b.getZ();

            return new Pos3d(//
                    b.getX() - (deltaZ / 2 - deltaX / 2), //
                    b.getY() - (deltaZ / 2 - deltaY / 2), //
                    b.getZ() - (deltaZ / 2 - deltaZ / 2) //
            );
        }
    }

    public int part2(List<Nanobot> bots) {
        TreeMap<Integer, Integer> ranges = new TreeMap<>();
        for (Nanobot n : bots) {
            int distFromZero = (int) distance(n, new Nanobot(0, 0, 0, 0));
            ranges.put(Math.max(0, distFromZero - (int) n.range), 1);
            ranges.put(distFromZero + (int) n.range, -1);
        }
        int count = 0;
        int result = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> each : ranges.entrySet()) {
            count += each.getValue();
            if (count > maxCount) {
                result = each.getKey();
                maxCount = count;
            }
        }
        return result;
    }

    @Getter
    private int nanobotsInRange;
    @Getter
    private int manhattanDistance;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2018/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Nanobot> nanobots = eventData.stream()//
                .map(this::toNanobot)//
                .collect(Collectors.toList());

        Nanobot strongestNanobot = nanobots.stream()//
                .sorted((a, b) -> (int) (b.range - a.range))//
                .findFirst().orElseThrow();

        nanobotsInRange = (int) (nanobots.stream()//
                .filter(v -> inRange(strongestNanobot, v))//
                .count());

        logger.log(Level.INFO, () -> "nanobotsInRange : " + getNanobotsInRange());

        List<Area> areas = nanobots.stream()//
                .map(Nanobot::toArea)//
                .collect(Collectors.toList());

        System.out.println("a");

        manhattanDistance = part2(nanobots);
        logger.log(Level.INFO, () -> "manhattanDistance : " + getManhattanDistance());

        return true;
    }

    private boolean inRange(Nanobot from, Nanobot to) {
        return from.range >= distance(from, to);
    }

    private long distance(Nanobot a, Nanobot b) {
        return Math.abs(b.x - a.x)//
                + Math.abs(b.y - a.y)//
                + Math.abs(b.z - a.z)//
        ;
    }

    private List<Area> collision(Area a, Area b) {
        if (isInArea(a, b.t) && isInArea(a, b.b)) {
            return List.of(new Area(b.t, b.b));
        }

        if (isInArea(b, a.t) && isInArea(b, a.b)) {
            return List.of(new Area(a.t, a.b));
        }

        return null;
    }

    private boolean isInArea(Area a, Pos3d point) {
        if (a.t.equals(point) || a.b.equals(point)) {
            return true;
        }

        Pos3d pointToB = new Pos3d(//
                point.getX() - a.b.getX(), //
                point.getY() - a.b.getY(), //
                point.getZ() - a.b.getZ() //
        );

        if (pointToB.getZ() < 0) {
            return false;
        }

        if (pointToB.getZ() < Math.abs(pointToB.getX()) || pointToB.getZ() < Math.abs(pointToB.getY())) {
            return false;
        }

        Pos3d pointToT = new Pos3d(//
                point.getX() - a.t.getX(), //
                point.getY() - a.t.getY(), //
                point.getZ() - a.t.getZ() //
        );

        if (pointToT.getZ() > 0) {
            return false;
        }

        if (pointToB.getZ() > Math.abs(pointToB.getX()) || pointToB.getZ() > Math.abs(pointToB.getY())) {
            return false;
        }

        return true;
    }

    private void getAllInRange(List<Nanobot> nanobots) {
        List<Nanobot> copy = new LinkedList<>(nanobots);

        for (Nanobot nanobot : copy) {
            int count = (int) (nanobots.stream()//
                    .filter(v -> inRange(nanobot, v))//
                    .count());
            System.out.println(nanobot.toString() + " " + count);
        }
    }

    private Nanobot toNanobot(String data) {
        Matcher m = NANOBOT_PATTERN.matcher(data);
        if (m.find()) {
            return new Nanobot(//
                    Integer.parseInt(m.group(1)), //
                    Integer.parseInt(m.group(2)), //
                    Integer.parseInt(m.group(3)), //
                    Integer.parseInt(m.group(4)) //
            );
        }
        throw new IllegalArgumentException(data);
    }

}
