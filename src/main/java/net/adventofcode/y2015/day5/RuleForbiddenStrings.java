package net.adventofcode.y2015.day5;

/**
 * String does not contain the strings ab, cd, pq, or xy.
 */
public class RuleForbiddenStrings implements IRule {

    private boolean notFound = true;
    private Character lastChar = null;

    @Override
    public void addChar(char c) {
        if (null != lastChar //
                && ((lastChar.equals('a') && c == 'b') //
                        || (lastChar.equals('c') && c == 'd') //
                        || (lastChar.equals('p') && c == 'q') //
                        || (lastChar.equals('x') && c == 'y') //
                )) {
            notFound = false;
        }
        lastChar = c;
    }

    @Override
    public boolean getResult() {
        return notFound;
    }

    @Override
    public void reset() {
        notFound = true;
        lastChar = null;
    }

}
