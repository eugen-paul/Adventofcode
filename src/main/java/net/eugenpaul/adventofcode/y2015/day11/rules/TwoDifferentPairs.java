package net.eugenpaul.adventofcode.y2015.day11.rules;

import net.eugenpaul.adventofcode.y2015.day11.IRule;

public class TwoDifferentPairs implements IRule {

    @Override
    public boolean checkPassword(char[] password) {
        Character pairOne = null;
        for (int i = 0, n = password.length; i < n - 1; i++) {
            if (password[i] == password[i + 1]) {
                if (pairOne == null) {
                    pairOne = password[i];
                } else if (pairOne != password[i]) {
                    return true;
                }
            }
        }
        return false;
    }

}
