package net.eugenpaul.adventofcode.y2015.day5;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day5 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day5.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day5.class.getName());

    private Long niceStringCount = null;
    private Long advancedNiceStringCount = null;

    private List<IRule> rules;
    private List<IRule> rulesAdvanced;

    public Long getNiceStringCount() {
        return niceStringCount;
    }

    public Long getAdvancedNiceStringCount() {
        return advancedNiceStringCount;
    }

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2015/day5/puzzle1.txt");
    }

    public Day5() {
        rules = List.of(new RuleThreeVowels(), new RuleDualLetter(), new RuleForbiddenStrings());
        rulesAdvanced = List.of(new RulePairOfTwoLetters(), new RuleRepetitionWithSeparator());
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "niceStringCount  " + niceStringCount);
        logger.log(Level.INFO, () -> "advancedNiceStringCount " + advancedNiceStringCount);

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

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
