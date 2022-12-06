package net.eugenpaul.adventofcode.y2022.day6;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day6 extends SolutionTemplate {

    @Getter
    private int firstMarker;
    @Getter
    private int firstMessage;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2022/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        firstMarker = doPuzzle1(eventData);
        firstMessage = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "firstMarker : " + getFirstMarker());
        logger.log(Level.INFO, () -> "firstMessage : " + getFirstMessage());

        return true;
    }

    private int doPuzzle1(String eventData) {
        return getMarker(eventData, 4);
    }

    private int doPuzzle2(String eventData) {
        return getMarker(eventData, 14);
    }

    private Integer getMarker(String eventData, int len) {
        char[] chars = eventData.toCharArray();
        LinkedList<Character> buffer = new LinkedList<>();

        for (int i = 0; i < chars.length; i++) {
            if (buffer.size() == len) {
                buffer.removeFirst();
            }
            buffer.add(chars[i]);
            if (isUnique(buffer, len)) {
                return i + 1;
            }
        }

        return null;
    }

    private boolean isUnique(LinkedList<Character> buffer, int len) {
        if (buffer.size() != len) {
            return false;
        }
        return (new HashSet<>(buffer)).size() == len;
    }

}
