package net.eugenpaul.adventofcode.y2015.day11.rules;

import net.eugenpaul.adventofcode.y2015.day11.IRule;

public class StraightOfThree implements IRule {

    @Override
    public boolean checkPassword(char[] password) {
        for (int i = 0, n = password.length; i < n - 2; i++) {
            if (password[i] == password[i + 1] - 1//
                    && password[i + 1] == password[i + 2] - 1//
            ) {
                return true;
            }
        }
        return false;
    }

}
