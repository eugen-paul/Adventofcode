package net.eugenpaul.adventofcode.y2022.day19;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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

        int result = 0;
        for (int i = 0; i < bps.size(); i++) {
            Map<Long, Integer> hh = new HashMap<>();
            int g = getGeode(bps.get(i), 24, 0, 0, 0, 1, 0, 0, hh);
            result += g * (bps.get(i).number);
        }

        return result;
    }

    private long doPuzzle2(List<String> eventData) {
        var bps = readBp(eventData);

        if (bps.size() > 3) {
            bps = List.of(bps.get(0), bps.get(1), bps.get(2));
        }

        int result = 1;
        for (int i = 0; i < bps.size(); i++) {
            Map<Long, Integer> hh = new HashMap<>();
            int g = getGeode(bps.get(i), 32, 0, 0, 0, 1, 0, 0, hh);
            result *= g;
        }

        return result;
    }

    private int getGeode(Bp bp, int minRest, int ore, int clay, int obs, int oreR, int clayR, int obsR, Map<Long, Integer> hh) {

        Long key = minRest * 1000000000000000000L //
                + ore * 1000000000000000L + clay * 1000000000000L + obs * 1000000000L //
                + oreR * 1000000L + clayR * 10000L + obsR * 100L;

        Integer r = hh.get(key);
        if (r != null) {
            return r;
        }

        if (minRest <= 0) {
            return 0;
        }

        int maxResp = 0;

        int maxOreNeed = Math.max(Math.max(Math.max(bp.oreOreCost, bp.clayOreCost), bp.obsidianOreCost), bp.geodeOreCost);
        int maxClayNeed = Math.max(bp.clayOreCost, bp.obsidianClayCost);

        if (canBuildGeode(bp, ore, obs)) {
            var nowG = getGeode(bp, minRest - 1, //
                    ore - bp.geodeOreCost + oreR, clay + clayR, obs - bp.geodeObsidianCost + obsR, //
                    oreR, clayR, obsR, hh);
            maxResp = Math.max(maxResp, nowG + (minRest - 1));
        } else if (oreR > 0 && obsR > 0) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.geodeOreCost - ore) / oreR);
            int obsDeltaMin = (int) Math.ceil((double) (bp.geodeObsidianCost - obs) / obsR);
            int deltaMin = Math.max(oreDeltaMin, obsDeltaMin);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - deltaMin, //
                    ore + oreR * deltaMin, clay + clayR * deltaMin, obs + obsR * deltaMin, //
                    oreR, clayR, obsR, hh));
        }

        if (canBuildObs(bp, ore, clay) && obsR < bp.geodeObsidianCost) {
            var nowO = getGeode(bp, minRest - 1, //
                    ore - bp.obsidianOreCost + oreR, clay - bp.obsidianClayCost + clayR, obs + obsR, //
                    oreR, clayR, obsR + 1, hh);
            maxResp = Math.max(maxResp, nowO);
        } else if (oreR > 0 && clayR > 0 && obsR < bp.geodeObsidianCost) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.obsidianOreCost - ore) / oreR);
            int clayDeltaMin = (int) Math.ceil((double) (bp.obsidianClayCost - clay) / clayR);
            int deltaMin = Math.max(oreDeltaMin, clayDeltaMin);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - deltaMin, //
                    ore + oreR * deltaMin, clay + clayR * deltaMin, obs + obsR * deltaMin, //
                    oreR, clayR, obsR, hh));
        }

        if (canBuildClay(bp, ore) && clayR < maxClayNeed) {
            var nowC = getGeode(bp, minRest - 1, //
                    ore - bp.clayOreCost + oreR, clay + clayR, obs + obsR, //
                    oreR, clayR + 1, obsR, hh);
            maxResp = Math.max(maxResp, nowC);
        } else if (oreR > 0 && clayR < maxClayNeed) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.clayOreCost - ore) / oreR);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - oreDeltaMin, //
                    ore + oreR * oreDeltaMin, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, //
                    oreR, clayR, obsR, hh));
        }

        if (canBuildOre(bp, ore) && oreR < maxOreNeed) {
            maxResp = Math.max(maxResp, getGeode(bp, minRest - 1, //
                    ore - bp.oreOreCost + oreR, clay + clayR, obs + obsR, //
                    oreR + 1, clayR, obsR, hh));
        } else if (oreR > 0 && oreR < maxOreNeed) {
            int oreDeltaMin = (int) Math.ceil((double) (bp.oreOreCost - ore) / oreR);
            maxResp = Math.max(maxResp, getGeode(bp, minRest - oreDeltaMin, //
                    ore + oreR * oreDeltaMin, clay + clayR * oreDeltaMin, obs + obsR * oreDeltaMin, //
                    oreR, clayR, obsR, hh));
        }

        hh.put(key, maxResp);

        return maxResp;
    }

    private boolean canBuildOre(Bp bp, int ore) {
        return bp.oreOreCost <= ore;
    }

    private boolean canBuildClay(Bp bp, int ore) {
        return bp.clayOreCost <= ore;
    }

    private boolean canBuildObs(Bp bp, int ore, int clay) {
        return bp.obsidianOreCost <= ore && bp.obsidianClayCost <= clay;
    }

    private boolean canBuildGeode(Bp bp, int ore, int obs) {
        return bp.geodeOreCost <= ore && bp.geodeObsidianCost <= obs;
    }

    private List<Bp> readBp(List<String> eventData) {
        List<Bp> r = new LinkedList<>();
        for (var dada : eventData) {
            r.add(fromString(dada));
        }
        return r;
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
