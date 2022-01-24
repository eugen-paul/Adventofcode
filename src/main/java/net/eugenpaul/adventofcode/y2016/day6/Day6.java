package net.eugenpaul.adventofcode.y2016.day6;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day6 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day6.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day6.class.getName());

    @Getter
    private String errorCorrectedInput;

    @Getter
    private String originalMessage;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2016/day6/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "errorCorrectedInput: " + getErrorCorrectedInput());
        logger.log(Level.INFO, () -> "originalMessage: " + getOriginalMessage());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<Integer, Map<Character, Integer>> storage = new TreeMap<>();

        eventData.stream().forEach(v -> countCharacters(v, storage));

        errorCorrectedInput = getMsg(storage, (o1, o2) -> o2.getValue() - o1.getValue());
        originalMessage = getMsg(storage, (o1, o2) -> o1.getValue() - o2.getValue());

        return true;
    }

    private String getMsg(Map<Integer, Map<Character, Integer>> storage, Comparator<Entry<Character, Integer>> comparator) {
        StringBuilder msg = new StringBuilder();
        storage.values().stream().forEach(v -> //
        msg.append(//
                v.entrySet().stream()//
                        .sorted(comparator)//
                        .findFirst()//
                        .get()//
                        .getKey()//
        )//
        );
        return msg.toString();
    }

    private void countCharacters(String data, Map<Integer, Map<Character, Integer>> storage) {
        for (int i = 0; i < data.length(); i++) {
            addCharToStorage(data.charAt(i), i, storage);
        }
    }

    private void addCharToStorage(char c, int position, Map<Integer, Map<Character, Integer>> storage) {
        storage.compute(position, (k, v) -> {
            if (null == v) {
                Map<Character, Integer> posMap = new HashMap<>();
                posMap.put(c, 1);
                return posMap;
            } else {
                countChar(c, v);
                return v;
            }
        });
    }

    private void countChar(char c, Map<Character, Integer> countStorage) {
        countStorage.compute(c, (k, v) -> (null == v) ? 1 : v + 1);
    }

}
