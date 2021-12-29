package net.eugenpaul.adventofcode.y2015.day11;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2015.day11.rules.NotContainLetter;
import net.eugenpaul.adventofcode.y2015.day11.rules.StraightOfThree;
import net.eugenpaul.adventofcode.y2015.day11.rules.TwoDifferentPairs;

public class Day11 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day11.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day11.class.getName());

    private String nextPassword = null;

    private List<IRule> rules;

    public String getNextPassword() {
        return nextPassword;
    }

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2015/day11/puzzle1.txt");

        puzzle.doPuzzleFromData(puzzle.getNextPassword());
    }

    public Day11() {
        rules = List.of(//
                new NotContainLetter('i'), //
                new NotContainLetter('o'), //
                new NotContainLetter('l'), //
                new StraightOfThree(), //
                new TwoDifferentPairs());
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "nextPassword  " + getNextPassword());

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        boolean passwordIsOk = false;
        char[] password = eventData.toCharArray();
        do {
            password = incrementPassword(password, password.length - 1);
            passwordIsOk = isPasswordOk(password);
        } while (!passwordIsOk);

        nextPassword = new String(password);

        return true;
    }

    private boolean isPasswordOk(char[] password) {
        for (IRule rule : rules) {
            if (!rule.checkPassword(password)) {
                return false;
            }
        }
        return true;
    }

    private char[] incrementPassword(char[] input, int position) {
        if (position < 0) {
            return input;
        }
        if (input[position] == 'z') {
            input[position] = 'a';
            return incrementPassword(input, position - 1);
        }
        input[position]++;
        return input;
    }

}
