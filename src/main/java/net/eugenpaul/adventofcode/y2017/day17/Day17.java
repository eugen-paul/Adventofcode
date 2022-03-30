package net.eugenpaul.adventofcode.y2017.day17;

import java.util.ArrayList;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {

    @Getter
    private int valueAfter2017;
    @Getter
    private int valueAfter0;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2017/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(Integer eventData) {
        if (null == eventData) {
            return false;
        }

        valueAfter2017 = doPuzzle1(eventData);
        valueAfter0 = doPuzzel2(eventData);

        logger.log(Level.INFO, () -> "valueAfter2017 : " + getValueAfter2017());
        logger.log(Level.INFO, () -> "valueAfter0 : " + getValueAfter0());

        return true;
    }

    private int doPuzzel2(int input) {
        int secondValue = 0;
        int currentPosition = 0;

        for (int i = 1; i <= 50_000_000; i++) {
            currentPosition = getNextPosition(currentPosition, i, input);
            if (currentPosition == 1) {
                secondValue = i;
            }
        }

        return secondValue;
    }

    private int doPuzzle1(int input) {
        ArrayList<Integer> buffer = new ArrayList<>();
        buffer.add(0);

        int currentPosition = 0;
        for (int i = 1; i <= 2017; i++) {
            currentPosition = getNextPosition(currentPosition, buffer.size(), input);
            buffer.add(currentPosition, i);
        }
        return buffer.get((currentPosition + 1) % buffer.size());
    }

    private int getNextPosition(int currentPosition, int bufferSize, int steps) {
        return ((currentPosition + steps) % bufferSize) + 1;
    }

}
