package net.eugenpaul.adventofcode.y2017.day2;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day2 extends SolutionTemplate {

    @Getter
    private long checksum;
    @Getter
    private long checksum2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2017/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        checksum = 0;
        checksum2 = 0;
        for (String string : eventData) {
            List<Long> elements = StringConverter.toLongArrayListSorted(string);

            checksum += checksumPuzzle1(elements);
            checksum2 += checksumPuzzle2(elements);
        }

        logger.log(Level.INFO, () -> "checksum : " + getChecksum());
        logger.log(Level.INFO, () -> "checksum2 : " + getChecksum2());

        return true;
    }

    private long checksumPuzzle1(List<Long> data) {
        return data.get(data.size() - 1) - data.get(0);
    }

    private long checksumPuzzle2(List<Long> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            long a = data.get(i);
            for (int k = i + 1; k < data.size(); k++) {
                long b = data.get(k);
                if (b % a == 0) {
                    return b / a;
                }
            }
        }
        return 0;
    }

}
