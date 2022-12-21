package net.eugenpaul.adventofcode.y2022.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .mapToInt(v -> v.number * getGeode(v, 24, 0, 0, 0, 1, 0, 0, new HashMap<>(), false, false, false, false))//
                .sum();
    }

    private long doPuzzle2(List<String> eventData) {
        var bps = readBp(eventData);

        return bps.stream()//
                .limit(3)//
                .mapToInt(v -> getGeode(v, 32, 0, 0, 0, 1, 0, 0, new HashMap<>(), false, false, false, false))//
                .reduce(1, (a, b) -> a * b);
    }

    private int getGeode(Bp bp, int minRest, //
            int ore, int clay, int obs, //
            int oreR, int clayR, int obsR, //
            Map<Long, Integer> hh, //
            boolean waitForOre, boolean waitForClay, boolean waitForObs, boolean waitForGeode //
    ) {

        Long key = minRest * 1000000000000000000L //
                + ore * 1000000000000000L + clay * 1000000000000L + obs * 1000000000L //
                + oreR * 1000000L + clayR * 10000L + obsR * 100L;

        if (!waitForOre && !waitForClay && !waitForObs && !waitForGeode) {
            Integer r = hh.get(key);
            if (r != null) {
                return r;
            }
        }

        if (minRest <= 0) {
            return 0;
        }

        int maxResp = 0;

        int maxOreNeed = (int) MathHelper.max(bp.oreOreCost, bp.clayOreCost, bp.obsidianOreCost, bp.geodeOreCost);
        int maxClayNeed = (int) MathHelper.max(bp.clayOreCost, bp.obsidianClayCost);

        boolean canBuildGeode = bp.geodeOreCost <= ore && bp.geodeObsidianCost <= obs;
        if (canBuildGeode && !waitForOre && !waitForClay && !waitForObs) {
            var nowG = getGeode(bp, minRest - 1, //
                    ore - bp.geodeOreCost + oreR, clay + clayR, obs - bp.geodeObsidianCost + obsR, //
                    oreR, clayR, obsR, hh, false, false, false, false);
            maxResp = Math.max(maxResp, nowG + (minRest - 1));
        } else if (oreR > 0 && obsR > 0 && !waitForOre && !waitForClay && !waitForObs) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.geodeOreCost - ore) / oreR);
            int obsDeltaMin = (int) Math.ceil((double) (bp.geodeObsidianCost - obs) / obsR);
            int deltaMin = Math.max(oreDeltaMin, obsDeltaMin);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - deltaMin, //
                    ore + oreR * deltaMin, clay + clayR * deltaMin, obs + obsR * deltaMin, //
                    oreR, clayR, obsR, hh, //
                    false, false, false, true //
            ));
        }

        boolean canBuildObs = bp.obsidianOreCost <= ore && bp.obsidianClayCost <= clay;
        boolean needBuildObs = obsR < bp.geodeObsidianCost;

        if (canBuildObs && needBuildObs && !waitForOre && !waitForClay && !waitForGeode) {
            var nowO = getGeode(bp, minRest - 1, //
                    ore - bp.obsidianOreCost + oreR, clay - bp.obsidianClayCost + clayR, obs + obsR, //
                    oreR, clayR, obsR + 1, hh, false, false, false, false);
            maxResp = Math.max(maxResp, nowO);
        } else if (oreR > 0 && clayR > 0 && needBuildObs && !waitForOre && !waitForClay && !waitForGeode) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.obsidianOreCost - ore) / oreR);
            int clayDeltaMin = (int) Math.ceil((double) (bp.obsidianClayCost - clay) / clayR);
            int deltaMin = Math.max(oreDeltaMin, clayDeltaMin);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - deltaMin, //
                    ore + oreR * deltaMin, clay + clayR * deltaMin, obs + obsR * deltaMin, //
                    oreR, clayR, obsR, hh, //
                    false, false, true, false //
            ));
        }

        boolean canBuildClay = bp.clayOreCost <= ore;
        boolean needBuildClay = clayR < maxClayNeed;

        if (canBuildClay && needBuildClay && !waitForOre && !waitForObs && !waitForGeode) {
            var nowC = getGeode(bp, minRest - 1, //
                    ore - bp.clayOreCost + oreR, clay + clayR, obs + obsR, //
                    oreR, clayR + 1, obsR, hh, false, false, false, false);
            maxResp = Math.max(maxResp, nowC);
        } else if (oreR > 0 && needBuildClay && !waitForOre && !waitForObs && !waitForGeode) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.clayOreCost - ore) / oreR);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - oreDeltaMin, //
                    ore + oreR * oreDeltaMin, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, //
                    oreR, clayR, obsR, hh, //
                    false, true, false, false //
            ));
        }

        boolean canBuildOre = bp.oreOreCost <= ore;
        boolean needBuildOre = oreR < maxOreNeed;

        if (canBuildOre && needBuildOre && !waitForClay && !waitForObs && !waitForGeode) {
            maxResp = Math.max(maxResp, getGeode(bp, minRest - 1, //
                    ore - bp.oreOreCost + oreR, clay + clayR, obs + obsR, //
                    oreR + 1, clayR, obsR, hh, false, false, false, false));
        } else if (oreR > 0 && needBuildOre && !waitForClay && !waitForObs && !waitForGeode) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.oreOreCost - ore) / oreR);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - oreDeltaMin, //
                    ore + oreR * oreDeltaMin, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, //
                    oreR, clayR, obsR, hh, //
                    true, false, false, false //
            ));
        }

        if (!waitForOre && !waitForClay && !waitForObs && !waitForGeode) {
            hh.put(key, maxResp);
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
