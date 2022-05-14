package net.eugenpaul.adventofcode.y2018.day23;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.var;
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

        public long distanceToZero() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z) - range;
        }
    }

    @Getter
    private int nanobotsInRange;
    @Getter
    private long manhattanDistance;

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

        manhattanDistance = doPuzzle2(nanobots);
        logger.log(Level.INFO, () -> "manhattanDistance : " + getManhattanDistance());

        return true;
    }

    private long doPuzzle2(List<Nanobot> nanobots) {

        Set<Nanobot> testDone = new HashSet<>();

        // Megabot covers all bots.
        Nanobot megabot = getMegaBot(nanobots);

        // divide the mega bot to 6 child small bots an 4 same bots (but with other position)
        LinkedList<Nanobot> childs = divide(megabot);
        Collections.sort(childs, (a, b) -> sortTestBots(nanobots, a, b));

        long bestCut = 0;
        long bestDistance = 0;

        while (!childs.isEmpty()) {
            var testBot = childs.poll();
            long cutOfBot = collisionCount(nanobots, testBot);

            if (cutOfBot < bestCut) {
                // no need to chech the testBoot or devide the bot. We already found a end-bot with more collisions.
                continue;
            }

            if (testBot.range == 0) {
                if (cutOfBot == bestCut) {
                    bestDistance = Math.min(bestDistance, testBot.distanceToZero());
                } else {
                    bestDistance = testBot.distanceToZero();
                }
                bestCut = cutOfBot;
                continue;
            }

            LinkedList<Nanobot> testChilds = divide(testBot);

            long checkCount = Math.max(1, bestCut); // The test area must intersect with one of the octahedrons.

            testChilds = testChilds.stream()//
                    .filter(v -> collisionCount(nanobots, v) > checkCount)//
                    .filter(v -> !testDone.contains(v))// The areas that have already been tested do not need to be tested again.
                    .sorted((a, b) -> sortTestBots(nanobots, a, b) * (-1))//
                    .collect(Collectors.toCollection(LinkedList::new));

            for (Nanobot t : testChilds) {
                childs.addFirst(t);
                testDone.add(t);
            }
        }

        return bestDistance;
    }

    /**
     * After dividing the bots, I want to test the smaller bots first. Only then will the bots with the same radius be tested.
     */
    private int sortTestBots(List<Nanobot> nanobots, Nanobot a, Nanobot b) {
        if (a.range == b.range) {
            int collB = (int) collisionCount(nanobots, b);
            int collA = (int) collisionCount(nanobots, a);
            return collB - collA;
        }

        return (int) (a.range - b.range);
    }

    private boolean inRange(Nanobot from, Nanobot to) {
        return from.range >= distance(from, to);
    }

    private boolean isCollision(Nanobot a, Nanobot b) {
        return distance(a, b) <= a.range + b.range;
    }

    private long distance(Nanobot a, Nanobot b) {
        return Math.abs(b.x - a.x)//
                + Math.abs(b.y - a.y)//
                + Math.abs(b.z - a.z)//
        ;
    }

    private long collisionCount(List<Nanobot> nanobots, Nanobot testBot) {
        return nanobots.stream()//
                .filter(v -> isCollision(v, testBot))//
                .count();
    }

    private Nanobot getMegaBot(List<Nanobot> nanobots) {
        long minX = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long minY = Long.MAX_VALUE;
        long maxY = Long.MIN_VALUE;
        long minZ = Long.MAX_VALUE;
        long maxZ = Long.MIN_VALUE;

        for (Nanobot nanobot : nanobots) {
            minX = Math.min(minX, nanobot.x);
            maxX = Math.max(maxX, nanobot.x);
            minY = Math.min(minY, nanobot.y);
            maxY = Math.max(maxY, nanobot.y);
            minZ = Math.min(minZ, nanobot.z);
            maxZ = Math.max(maxZ, nanobot.z);
        }

        long radiusX = (maxX - minX) / 2;
        long radiusY = (maxY - minY) / 2;
        long radiusZ = (maxZ - minZ) / 2;

        long maxRadius = Math.max(radiusX, Math.max(radiusY, radiusZ));

        maxRadius = maxRadius * 4;

        long nextPowerOfTwo = Long.toString(maxRadius, 2).length() + 1L;

        return new Nanobot(//
                minX + radiusX, //
                minY + radiusY, //
                minZ + radiusZ, //
                1L << nextPowerOfTwo //
        );
    }

    private LinkedList<Nanobot> divide(Nanobot bot) {
        if (bot.range == 0) {
            throw new IllegalArgumentException();
        }

        LinkedList<Nanobot> response = new LinkedList<>();

        long newRadius = bot.range / 2;

        if (bot.range > 1) {
            // devide area/circles in 6 small areas/circles
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y, //
                    bot.z + newRadius, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y, //
                    bot.z - newRadius, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y + newRadius, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y - newRadius, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x + newRadius, //
                    bot.y, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x - newRadius, //
                    bot.y, //
                    bot.z, //
                    newRadius //
            ));

            if (bot.range > 2) {
                // Octahedron cannot be divided into 6 octahedrons.
                // To cover the remaining area, I move the current octahedron four times.
                // But only if radius of Octahedron is greater than 2. Otherwise all points are covered by the 6 smaller octahedrons.
                response.add(new Nanobot(//
                        bot.x + newRadius / 2, //
                        bot.y + newRadius / 2, //
                        bot.z, //
                        bot.range //
                ));
                response.add(new Nanobot(//
                        bot.x + newRadius / 2, //
                        bot.y - newRadius / 2, //
                        bot.z, //
                        bot.range //
                ));
                response.add(new Nanobot(//
                        bot.x - newRadius / 2, //
                        bot.y + newRadius / 2, //
                        bot.z, //
                        bot.range //
                ));
                response.add(new Nanobot(//
                        bot.x - newRadius / 2, //
                        bot.y - newRadius / 2, //
                        bot.z, //
                        bot.range //
                ));
            }
        } else {
            // If the radium is equal to 1, then octahedrons must be created manually.
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y, //
                    bot.z + 1, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y, //
                    bot.z - 1, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y + 1, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x, //
                    bot.y - 1, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x + 1, //
                    bot.y, //
                    bot.z, //
                    newRadius //
            ));
            response.add(new Nanobot(//
                    bot.x - 1, //
                    bot.y, //
                    bot.z, //
                    newRadius //
            ));

            response.add(new Nanobot(//
                    bot.x, //
                    bot.y, //
                    bot.z, //
                    newRadius //
            ));
        }

        return response;
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
