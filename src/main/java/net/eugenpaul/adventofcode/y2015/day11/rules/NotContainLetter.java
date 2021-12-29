package net.eugenpaul.adventofcode.y2015.day11.rules;

import net.eugenpaul.adventofcode.y2015.day11.IRule;

public class NotContainLetter implements IRule {

    private char letter;

    public NotContainLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public boolean checkPassword(char[] password) {
        for (int i = 0, n = password.length; i < n; i++) {
            if (password[i] == letter) {
                return false;
            }
        }
        return true;
    }

}
