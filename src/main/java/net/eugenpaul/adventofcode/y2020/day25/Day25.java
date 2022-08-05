package net.eugenpaul.adventofcode.y2020.day25;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @Getter
    private long key;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2020/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        int key1 = Integer.parseInt(eventData.get(0));
        int key2 = Integer.parseInt(eventData.get(1));

        long loopSize1 = -1;

        loopSize1 = getLoopSize(7, key1);

        if (loopSize1 == -1) {
            return false;
        }

        key = transform(loopSize1, key2);

        logger.log(Level.INFO, () -> "key  : " + getKey());

        return true;
    }

    private long transform(long loops, int subjectNumber) {
        long response = 1;
        for (long i = 0; i < loops; i++) {
            response = response * subjectNumber;
            response = response % 20_201_227L;
        }
        return response;
    }

    private long getLoopSize(int subjectNumber, long should) {
        long response = 1;
        for (int i = 1; i < 20_201_227; i++) {
            response = response * subjectNumber;
            response = response % 20_201_227L;
            if (response == should) {
                return i;
            }
        }
        return -1;
    }

}
