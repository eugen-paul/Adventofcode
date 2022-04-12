package net.eugenpaul.adventofcode.y2018.day2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @Getter
    private long checksum;
    @Getter
    private String letters;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2018/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        checksum = doPuzzle1(eventData);
        letters = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "checksum : " + getChecksum());
        logger.log(Level.INFO, () -> "letters : " + getLetters());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        long count2 = 0;
        long count3 = 0;
        for (String data : eventData) {
            if (exactlyNLetter(data, 2)) {
                count2++;
            }
            if (exactlyNLetter(data, 3)) {
                count3++;
            }
        }

        return count2 * count3;
    }

    private boolean exactlyNLetter(String data, int n) {
        Map<Character, Integer> storage = new HashMap<>();
        for (char c : data.toCharArray()) {
            storage.compute(c, (k, v) -> (v == null) ? 1 : ++v);
        }

        var match = storage.values().stream().filter(v -> v == n).count();
        return match != 0L;
    }

    private String doPuzzle2(List<String> eventData) {
        Map<String, Boolean> maskMap = new HashMap<>();

        for (String data : eventData) {
            int pos = addToMaskMap(maskMap, data);
            if (pos >= 0) {
                return mask(data, pos).replace("*", "");
            }
        }

        return null;
    }

    private int addToMaskMap(Map<String, Boolean> maskMap, String data) {
        for (int i = 0; i < data.length(); i++) {
            String maskedData = mask(data, i);
            if (maskMap.containsKey(maskedData)) {
                return i;
            }
            maskMap.put(maskedData, true);
        }
        return -1;
    }

    private String mask(String data, int position) {
        StringBuilder response = new StringBuilder(data.length());
        response.append(data);
        response.setCharAt(position, '*');
        return response.toString();
    }

}
