package net.eugenpaul.adventofcode.y2018.day12;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Data
    private class Rules {
        private boolean l2;
        private boolean l1;
        private boolean c;
        private boolean r1;
        private boolean r2;

        private boolean o;

        public Rules(String input) {
            l2 = input.charAt(0) == '#';
            l1 = input.charAt(1) == '#';
            c = input.charAt(2) == '#';
            r1 = input.charAt(3) == '#';
            r2 = input.charAt(4) == '#';
            o = input.charAt(9) == '#';
        }

        public boolean isRule(Map<Integer, Boolean> pots, int position) {
            return pots.getOrDefault(position - 2, false) == l2 //
                    && pots.getOrDefault(position - 1, false) == l1 //
                    && pots.getOrDefault(position, false) == c //
                    && pots.getOrDefault(position + 1, false) == r1 //
                    && pots.getOrDefault(position + 2, false) == r2 //
            ;
        }
    }

    @Getter
    private long sumOfAllPots;
    @Getter
    private long sumOfAllPots2;

    @Setter
    private boolean doPuzzle2 = true;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2018/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        TreeMap<Integer, Boolean> pots = new TreeMap<>();
        readInitState(eventData, pots);

        List<Rules> rules = readRules(eventData);

        sumOfAllPots = doPuzzle(pots, rules, 20L);

        if (doPuzzle2) {
            pots = new TreeMap<>();
            readInitState(eventData, pots);
            sumOfAllPots2 = doPuzzle(pots, rules, 50_000_000_000L);
        } else {
            sumOfAllPots2 = 0;
        }

        logger.log(Level.INFO, () -> "sumOfAllPots : " + getSumOfAllPots());
        logger.log(Level.INFO, () -> "sumOfAllPots2 : " + getSumOfAllPots2());

        return true;
    }

    private long doPuzzle(TreeMap<Integer, Boolean> pots, List<Rules> rules, long maxGenerations) {
        for (long generation = 0L; generation < maxGenerations; generation++) {
            pots = doGeneration(pots, rules);

            String hash = computeHash(pots);
            if (hash.equals(
                    "#################################################################################################################################################################################################")) {
                // each generation the plants will be moved one step to right
                int minPlant = pots.firstKey();
                long minPlantAfterMaxGenerations = minPlant + maxGenerations - generation - 1;
                return MathHelper.sumRange(minPlantAfterMaxGenerations, minPlantAfterMaxGenerations + 193L);
            }

        }
        return pots.keySet().stream().reduce(0, (a, b) -> a + b);
    }

    private TreeMap<Integer, Boolean> doGeneration(TreeMap<Integer, Boolean> pots, List<Rules> rules) {
        TreeMap<Integer, Boolean> potsNext = new TreeMap<>();
        int min = pots.firstKey();
        int max = pots.lastKey();
        for (int i = min - 2; i < max + 2; i++) {
            for (Rules rule : rules) {
                if (rule.isRule(pots, i)) {
                    if (rule.isO()) {
                        potsNext.put(i, rule.isO());
                    }
                    break;
                }
            }
        }
        return potsNext;
    }

    private String computeHash(TreeMap<Integer, Boolean> pots) {
        int min = pots.firstKey();
        int max = pots.lastKey();
        StringBuilder response = new StringBuilder(max - min + 1);
        for (int i = min; i < max; i++) {
            if (pots.getOrDefault(i, false).booleanValue()) {
                response.append("#");
            } else {
                response.append(".");
            }
        }
        return response.toString();
    }

    private List<Rules> readRules(List<String> eventData) {
        List<Rules> rules = new LinkedList<>();
        for (int i = 2; i < eventData.size(); i++) {
            rules.add(new Rules(eventData.get(i)));
        }
        return rules;
    }

    private void readInitState(List<String> eventData, TreeMap<Integer, Boolean> pots) {
        for (int i = 15; i < eventData.get(0).length(); i++) {
            if (eventData.get(0).charAt(i) == '#') {
                pots.put(i - 15, true);
            }
        }
    }

}
