package net.adventofcode.y2015.day5;

/**
 * String contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
 */
public class RuleThreeVowels implements IRule {

    private int count = 0;

    @Override
    public void addChar(char c) {
        switch (c) {
        case 'a':
        case 'e':
        case 'i':
        case 'o':
        case 'u':
            count++;
            break;
        default:
            break;
        }
    }

    @Override
    public boolean getResult() {
        return count >= 3;
    }

    @Override
    public void reset() {
        count = 0;
    }

}
