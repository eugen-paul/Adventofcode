package net.adventofcode.y2015.day5;

import java.util.HashMap;
import java.util.Map;

/**
 * String contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa
 * (aa, but it overlaps).
 */
public class RulePairOfTwoLetters implements IRule {

    private Character lastChar = null;
    private int position = 0;
    private boolean found = false;

    private Map<String, Integer> pairs = new HashMap<>();

    @Override
    public void addChar(char c) {
        if (found) {
            return;
        }

        if (null != lastChar) {
            String data = "" + lastChar + c;
            pairs.compute(data, (k, v) -> {
                // new pair
                if (null == v) {
                    return position;
                }

                // pair overlap => store old position
                if (v + 1 == position) {
                    return v;
                }

                // pair repetition found
                found = true;
                return position;
            });
        }
        lastChar = c;
        position++;
    }

    @Override
    public boolean getResult() {
        return found;
    }

    @Override
    public void reset() {
        lastChar = null;
        position = 0;
        found = false;
        pairs.clear();
    }

}
