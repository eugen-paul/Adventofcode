package net.eugenpaul.adventofcode.y2016.day6;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day6 extends SolutionTemplate {

    @Getter
    private String errorCorrectedInput;

    @Getter
    private String originalMessage;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2016/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        Map<Integer, Map<Character, Integer>> storage = new TreeMap<>();

        eventData.stream().forEach(v -> countCharacters(v, storage));

        errorCorrectedInput = getMsg(storage, (o1, o2) -> o2.getValue() - o1.getValue());
        originalMessage = getMsg(storage, (o1, o2) -> o1.getValue() - o2.getValue());

        logger.log(Level.INFO, () -> "errorCorrectedInput: " + getErrorCorrectedInput());
        logger.log(Level.INFO, () -> "originalMessage: " + getOriginalMessage());

        return true;
    }

    private String getMsg(Map<Integer, Map<Character, Integer>> storage, Comparator<Entry<Character, Integer>> comparator) {
        StringBuilder msg = new StringBuilder();
        storage.values().stream().forEach(v -> setMsgChar(comparator, msg, v));
        return msg.toString();
    }

    private void setMsgChar(Comparator<Entry<Character, Integer>> comparator, StringBuilder msg, Map<Character, Integer> v) {
        msg.append(//
                v.entrySet().stream()//
                        .sorted(comparator)//
                        .findFirst()//
                        .orElseThrow()//
                        .getKey()//
        )//
        ;
    }

    private void countCharacters(String data, Map<Integer, Map<Character, Integer>> storage) {
        for (int position = 0; position < data.length(); position++) {
            storage.computeIfAbsent(position, k -> new HashMap<>())//
                    .compute(data.charAt(position), (k, v) -> (null == v) ? 1 : v + 1);
        }
    }

}
