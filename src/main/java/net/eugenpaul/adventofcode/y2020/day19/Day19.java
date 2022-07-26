package net.eugenpaul.adventofcode.y2020.day19;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    private static final String SIMPLE = "^(\\d*): \\\"([a-b])\\\"$";
    private static final Pattern SIMPLE_PATTERN = Pattern.compile(SIMPLE);

    private interface Rule {
        /** A rule can match text parts of different length. The function returns all possible positions until which the rule matches. */
        List<Integer> isMatch(String data, int pos, Map<Integer, Rule> allRules);

        Integer getNumber();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private class SimpleRule implements Rule {

        private final char c;
        @Getter
        private final Integer number;

        @Override
        public List<Integer> isMatch(String data, int pos, Map<Integer, Rule> allRules) {
            if (data.length() <= pos) {
                return Collections.emptyList();
            }
            if (data.charAt(pos) == c) {
                return List.of(pos + 1);
            }
            return Collections.emptyList();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private class ComplexRule implements Rule {
        private final List<List<Integer>> rules;
        @Getter
        private final Integer number;

        @Override
        public List<Integer> isMatch(String data, int pos, Map<Integer, Rule> allRules) {
            if (data.length() <= pos) {
                return Collections.emptyList();
            }

            List<Integer> responsePositions = new LinkedList<>();

            for (List<Integer> subRules : rules) {
                responsePositions.addAll(checkSubRules(data, allRules, subRules, pos, 0));
            }

            return responsePositions;
        }

        private List<Integer> checkSubRules(String data, Map<Integer, Rule> allRules, List<Integer> subRules, Integer checkPos, int subRuleNumber) {
            List<Integer> reponsePos = new LinkedList<>();

            List<Integer> subRulPos = allRules.get(subRules.get(subRuleNumber)).isMatch(data, checkPos, allRules);

            if (subRulPos.isEmpty()) {
                return reponsePos;
            }

            if (subRules.size() == subRuleNumber + 1) {
                return subRulPos;
            }

            for (Integer sPos : subRulPos) {
                reponsePos.addAll(checkSubRules(data, allRules, subRules, sPos, subRuleNumber + 1));
            }

            return reponsePos;
        }
    }

    @Getter
    private long sum;
    @Getter
    private long sum2;

    @Setter
    private boolean doStep2 = true;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2020/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        Map<Integer, Rule> rules = new HashMap<>();

        int pos = 0;
        for (int i = 0; i < eventData.size(); i++) {
            if (eventData.get(i).isBlank() || eventData.get(i).isEmpty()) {
                pos = i + 1;
                break;
            }
            Rule rule = toRule(eventData.get(i));
            rules.put(rule.getNumber(), rule);
        }

        List<String> inputs = new LinkedList<>();
        for (int i = pos; i < eventData.size(); i++) {
            inputs.add(eventData.get(i));
        }

        Rule rule0 = rules.get(0);
        sum = inputs.stream().filter(v -> rule0.isMatch(v, 0, rules).contains(v.length())).count();

        logger.log(Level.INFO, () -> "sum : " + getSum());

        if (doStep2) {
            rules.put(8, new ComplexRule(List.of(List.of(42), List.of(42, 8)), 8));
            rules.put(11, new ComplexRule(List.of(List.of(42, 31), List.of(42, 11, 31)), 11));

            sum2 = inputs.stream().filter(v -> rule0.isMatch(v, 0, rules).contains(v.length())).count();

            logger.log(Level.INFO, () -> "sum2  : " + getSum2());
        }

        return true;
    }

    private Rule toRule(String data) {
        Matcher m = SIMPLE_PATTERN.matcher(data);
        if (m.find()) {
            return toSimpleRule(m);
        }

        return toComplexRule(data);
    }

    private Rule toSimpleRule(Matcher m) {
        return new SimpleRule(//
                m.group(2).charAt(0), //
                Integer.parseInt(m.group(1)) //
        );
    }

    private Rule toComplexRule(String data) {
        String[] nameValues = data.split(":");
        Integer number = Integer.parseInt(nameValues[0]);

        String[] subRulesData = nameValues[1].trim().split("\\|");
        List<List<Integer>> allSubrules = new LinkedList<>();
        for (var subrules : subRulesData) {
            String[] numbers = subrules.trim().split(" ");
            List<Integer> subrule = new LinkedList<>();
            for (var n : numbers) {
                subrule.add(Integer.parseInt(n));
            }
            allSubrules.add(subrule);
        }
        return new ComplexRule(allSubrules, number);
    }

}
