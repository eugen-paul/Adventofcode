package net.eugenpaul.adventofcode.y2015.day11;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2015.day11.rules.NotContainLetter;
import net.eugenpaul.adventofcode.y2015.day11.rules.StraightOfThree;
import net.eugenpaul.adventofcode.y2015.day11.rules.TwoDifferentPairs;

public class Day11 extends SolutionTemplate {

    @Getter
    private String nextPassword = null;
    @Getter
    private String nextPassword2 = null;

    private List<IRule> rules;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2015/day11/puzzle1.txt");
    }

    public Day11() {
        rules = List.of(//
                new NotContainLetter('i'), //
                new NotContainLetter('o'), //
                new NotContainLetter('l'), //
                new StraightOfThree(), //
                new TwoDifferentPairs());
    }

    @Override
    public boolean doEvent(String eventData) {
        char[] password = eventData.toCharArray();
        password = computeNextPassword(password);
        nextPassword = new String(password);

        logger.log(Level.INFO, () -> "nextPassword  " + getNextPassword());

        password = computeNextPassword(password);
        nextPassword2 = new String(password);

        logger.log(Level.INFO, () -> "nextPassword2  " + getNextPassword2());

        return true;
    }

    private char[] computeNextPassword(char[] password) {
        do {
            password = incrementPassword(password, password.length - 1);
        } while (!isPasswordOk(password));
        return password;
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
