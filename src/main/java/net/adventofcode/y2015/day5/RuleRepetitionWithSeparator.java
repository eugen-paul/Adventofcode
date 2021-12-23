package net.adventofcode.y2015.day5;

/**
 * String contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
 */
public class RuleRepetitionWithSeparator implements IRule {

    private boolean found = false;
    private Character lastChar = null;
    private Character lastChar2 = null;

    @Override
    public void addChar(char c) {
        if (null != lastChar2 && lastChar2.equals(c)) {
            found = true;
        }
        lastChar2 = lastChar;
        lastChar = c;
    }

    @Override
    public boolean getResult() {
        return found;
    }

    @Override
    public void reset() {
        found = false;
        lastChar = null;
        lastChar2 = null;
    }

}
