package net.eugenpaul.adventofcode.y2017.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day4 extends SolutionTemplate {

    @Getter
    private int validCount;
    @Getter
    private int validCount2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2017/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        validCount = doPuzzle(eventData, v -> v);
        validCount2 = doPuzzle(eventData, this::toStd);

        logger.log(Level.INFO, () -> "validCount : " + getValidCount());
        logger.log(Level.INFO, () -> "validCount2 : " + getValidCount2());

        return true;
    }

    private int doPuzzle(List<String> eventData, UnaryOperator<String> modifier) {
        int response = 0;
        for (String passphrase : eventData) {
            String[] elements = passphrase.split(" ");
            Map<String, Boolean> storage = new HashMap<>();
            boolean ok = true;
            for (String word : elements) {
                String modifiedElement = modifier.apply(word);
                if (storage.containsKey(modifiedElement)) {
                    ok = false;
                    break;
                }
                storage.put(modifiedElement, true);
            }
            if (ok) {
                response++;
            }
        }

        return response;
    }

    private String toStd(String data) {
        return StringConverter.toCharStream(data)//
                .sorted()//
                .map(c -> c + "")//
                .reduce("", (a, b) -> a + b);
    }

}
