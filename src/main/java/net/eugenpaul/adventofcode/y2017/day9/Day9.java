package net.eugenpaul.adventofcode.y2017.day9;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @Getter
    private Integer score;
    @Getter
    private Integer nonCanceledCharacters;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2017/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        score = 0;
        nonCanceledCharacters = 0;

        read(eventData, 0, 1, true);

        logger.log(Level.INFO, () -> "score : " + getScore());
        logger.log(Level.INFO, () -> "nonCanceledCharacters : " + getNonCanceledCharacters());

        return true;
    }

    private int read(String data, int offset, int depth, boolean isGroup) {
        int currentOffset = offset;
        boolean ignoreNext = false;
        while (currentOffset < data.length()) {
            if (ignoreNext) {
                ignoreNext = false;
                currentOffset++;
                continue;
            }

            if (isGroup) {
                switch (data.charAt(currentOffset)) {
                case '{':
                    score += depth;
                    currentOffset = read(data, currentOffset + 1, depth + 1, true);
                    break;
                case '}':
                    return currentOffset;
                case '<':
                    currentOffset = read(data, currentOffset + 1, depth + 1, false);
                    break;
                default:
                    break;
                }
            } else {
                switch (data.charAt(currentOffset)) {
                case '!':
                    ignoreNext = true;
                    break;
                case '>':
                    return currentOffset;
                default:
                    nonCanceledCharacters++;
                    break;
                }
            }

            currentOffset++;
        }
        return currentOffset;
    }

}
