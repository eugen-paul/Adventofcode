package net.eugenpaul.adventofcode.y2018.day23;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day23 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private static class Nanobot {
        private static final String NANOBOT_FORMAT = "^pos=<([\\-0-9]*),([\\-0-9]*),([\\-0-9]*)>, r=([\\-0-9]*)$";
        private static final Pattern NANOBOT_PATTERN = Pattern.compile(NANOBOT_FORMAT);

        private int x;
        private int y;
        private int z;
        private int range;

        public static Nanobot fromString(String data) {
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

        public int distanceFromZero() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
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
                .map(Nanobot::fromString)//
                .collect(Collectors.toList());

        Nanobot strongestNanobot = nanobots.stream()//
                .sorted((a, b) -> b.range - a.range)//
                .findFirst().orElseThrow();

        nanobotsInRange = (int) (nanobots.stream()//
                .filter(v -> inRange(strongestNanobot, v))//
                .count());

        logger.log(Level.INFO, () -> "nanobotsInRange : " + getNanobotsInRange());

        // getAllInRange(nanobots);
        // logger.log(Level.INFO, () -> "manhattanDistance : " + getManhattanDistance());

        return true;
    }

    private boolean inRange(Nanobot from, Nanobot to) {
        return from.range >= distance(from, to);
    }

    private int distance(Nanobot a, Nanobot b) {
        return Math.abs(b.x - a.x)//
                + Math.abs(b.y - a.y)//
                + Math.abs(b.z - a.z)//
        ;
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

}
