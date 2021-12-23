package net.adventofcode.y2015.day5;

/**
 * String contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
 */
public class RuleDualLetter implements IRule {

    private boolean found = false;
    private Character lastChar = null;

    @Override
    public void addChar(char c) {
        if (null != lastChar && lastChar.equals(c)) {
            found = true;
        }
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
    }

}
