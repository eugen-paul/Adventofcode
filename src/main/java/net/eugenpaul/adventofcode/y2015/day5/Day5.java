package net.eugenpaul.adventofcode.y2015.day5;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @Getter
    private Long niceStringCount = null;
    @Getter
    private Long advancedNiceStringCount = null;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2015/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<IRule> rules = List.of(new RuleThreeVowels(), new RuleDualLetter(), new RuleForbiddenStrings());
        List<IRule> rulesAdvanced = List.of(new RulePairOfTwoLetters(), new RuleRepetitionWithSeparator());

        niceStringCount = 0L;
        advancedNiceStringCount = 0L;

        for (String dataElement : eventData) {
            if (isNiceString(dataElement, rules)) {
                niceStringCount++;
            }
            if (isNiceString(dataElement, rulesAdvanced)) {
                advancedNiceStringCount++;
            }
        }

        logger.log(Level.INFO, () -> "niceStringCount  " + niceStringCount);
        logger.log(Level.INFO, () -> "advancedNiceStringCount " + advancedNiceStringCount);
        return true;
    }

    private boolean isNiceString(String data, List<IRule> rulesToCheck) {
        for (int i = 0, n = data.length(); i < n; i++) {
            for (IRule rule : rulesToCheck) {
                rule.addChar(data.charAt(i));
            }
        }

        boolean response = true;
        for (IRule rule : rulesToCheck) {
            boolean ruleResponse = rule.getResult();
            if (!ruleResponse) {
                response = false;
                break;
            }
        }

        for (IRule rule : rulesToCheck) {
            rule.reset();
        }

        return response;
    }
}
