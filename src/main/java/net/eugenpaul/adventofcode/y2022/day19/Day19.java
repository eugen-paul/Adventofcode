package net.eugenpaul.adventofcode.y2022.day19;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class Bp {
        int number;
        int oreOreCost;
        int clayOreCost;
        int obsidianOreCost;
        int obsidianClayCost;
        int geodeOreCost;
        int geodeObsidianCost;
    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2022/day19/puzzle1.txt");
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
        var bps = readBp(eventData);

        return bps.stream()//
                .mapToInt(v -> v.number * getGeode(v, 24, 0, 0, 0, 0, 1, 0, 0, 0, new int[25]))//
                .sum();
    }

    private long doPuzzle2(List<String> eventData) {
        var bps = readBp(eventData);

        return bps.stream()//
                .limit(3)//
                .mapToInt(v -> getGeode(v, 32, 0, 0, 0, 0, 1, 0, 0, 0, new int[33]))//
                .reduce(1, (a, b) -> a * b);
    }

    private int getWaitTime(int cost, int mats, int robots) {
        if (cost <= mats) {
            return 0;
        }
        int diff = cost - mats;
        int mod = diff % robots;
        if (mod == 0) {
            return diff / robots;
        }
        return diff / robots + 1;
    }

    private int getGeode(Bp bp, int minRest, //
            int ore, int clay, int obs, int geode, //
            int oreR, int clayR, int obsR, int geodeR, //
            int[] maxBuffer //
    ) {
        if (minRest <= 0) {
            return 0;
        }

        int maxPosible = geode + (int) MathHelper.sumRange(1, minRest);
        if (maxBuffer[minRest] > maxPosible) {
            return 0;
        }

        int maxResp = 0;

        int maxOreNeed = (int) MathHelper.max(bp.oreOreCost, bp.clayOreCost, bp.obsidianOreCost, bp.geodeOreCost);
        int maxClayNeed = (int) MathHelper.max(bp.clayOreCost, bp.obsidianClayCost);

        if (oreR > 0 && obsR > 0) {
            int oreDeltaMin = getWaitTime(bp.geodeOreCost, ore, oreR);
            int obsDeltaMin = getWaitTime(bp.geodeObsidianCost, obs, obsR);
            int deltaMin = Math.max(oreDeltaMin, obsDeltaMin) + 1;
            if (deltaMin < minRest) {
                var nowG = getGeode(bp, minRest - deltaMin, //
                        ore + oreR * deltaMin - bp.geodeOreCost, clay + clayR * deltaMin, obs + obsR * deltaMin - bp.geodeObsidianCost,
                        geode + geodeR * deltaMin, //
                        oreR, clayR, obsR, geodeR + 1, maxBuffer //
                );
                maxResp = Math.max(maxResp, nowG + (minRest - deltaMin));
            }
        }

        boolean needBuildObs = obsR < bp.geodeObsidianCost;
        if (needBuildObs && oreR > 0 && clayR > 0) {
            int oreDeltaMin = getWaitTime(bp.obsidianOreCost, ore, oreR);
            int clayDeltaMin = getWaitTime(bp.obsidianClayCost, clay, clayR);
            int deltaMin = Math.max(oreDeltaMin, clayDeltaMin) + 1;
            maxResp = Math.max(//
                    maxResp, //
                    getGeode(//
                            bp, minRest - deltaMin, //
                            ore + oreR * deltaMin - bp.obsidianOreCost, clay + clayR * deltaMin - bp.obsidianClayCost, obs + obsR * deltaMin,
                            geode + geodeR * deltaMin, //
                            oreR, clayR, obsR + 1, geodeR, maxBuffer //
                    )//
            );
        }

        boolean needBuildClay = clayR < maxClayNeed;
        if (needBuildClay) {
            int oreDeltaMin = getWaitTime(bp.clayOreCost, ore, oreR) + 1;
            maxResp = Math.max(//
                    maxResp, //
                    getGeode(bp, minRest - oreDeltaMin, //
                            ore + oreR * oreDeltaMin - bp.clayOreCost, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, geode + geodeR * oreDeltaMin, //
                            oreR, clayR + 1, obsR, geodeR, maxBuffer //
                    )//
            );
        }

        boolean needBuildOre = oreR < maxOreNeed;
        if (needBuildOre) {
            int oreDeltaMin = getWaitTime(bp.oreOreCost, ore, oreR) + 1;
            maxResp = Math.max(//
                    maxResp, //
                    getGeode(bp, minRest - oreDeltaMin, //
                            ore + oreR * oreDeltaMin - bp.oreOreCost, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, geode + geodeR * oreDeltaMin, //
                            oreR + 1, clayR, obsR, geodeR, maxBuffer //
                    ) //
            );
        }

        if (maxBuffer[minRest] < geode + maxResp) {
            maxBuffer[minRest] = geode + maxResp;
        }

        return maxResp;
    }

    private List<Bp> readBp(List<String> eventData) {
        return eventData.stream()//
                .map(this::fromString)//
                .collect(Collectors.toList());
    }

    private Bp fromString(String data) {
        var splits = data.split(" ");
        int number = Integer.parseInt(splits[1].replace(":", ""));
        int oreOreCost = Integer.parseInt(splits[6]);
        int clayOreCost = Integer.parseInt(splits[12]);
        int obsidianOreCount = Integer.parseInt(splits[18]);
        int obsidianClayCount = Integer.parseInt(splits[21]);
        int geodeOreCount = Integer.parseInt(splits[27]);
        int geodeObsidianCount = Integer.parseInt(splits[30]);

        return new Bp(number, oreOreCost, clayOreCost, obsidianOreCount, obsidianClayCount, geodeOreCount, geodeObsidianCount);
    }
}
