package net.eugenpaul.adventofcode.y2017.day10;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.NumberArrayHelper;
import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day10 extends SolutionTemplate {

    @Getter
    private Integer hash;
    @Getter
    private String hashTotal;

    @Setter
    private Integer totalLength = 256;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2017/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        hash = puzzle1(eventData);
        logger.log(Level.INFO, () -> "hash : " + getHash());

        if (totalLength == 256) {
            hashTotal = puzzle2(eventData);
            logger.log(Level.INFO, () -> "full hash : " + getHashTotal());
        }

        return true;
    }

    public String puzzle2(String eventData) {
        List<Integer> input = eventData.chars().boxed().collect(Collectors.toCollection(LinkedList::new));
        input.addAll(List.of(17, 31, 73, 47, 23));

        int[] sparseHash = round(input, 64);
        int[] denseHash = toDenseHash(sparseHash);

        return NumberArrayHelper.numberArrayToString(denseHash, "%02x");
    }

    private int[] toDenseHash(int[] sparseHash) {
        int[] denseHash = new int[16];
        Arrays.fill(denseHash, 0);

        for (int j = 0; j < 256; j++) {
            denseHash[j / 16] ^= sparseHash[j];
        }
        return denseHash;
    }

    private int puzzle1(String eventData) {
        List<Integer> input = StringConverter.toNumberLinkedList(eventData, Integer.class);

        int[] data = round(input, 1);

        return data[0] * data[1];
    }

    private int[] round(List<Integer> input, int rounds) {
        int currentPosition = 0;
        int skip = 0;

        int[] data = initCircularList();

        for (int i = 0; i < rounds; i++) {
            for (Integer length : input) {
                int start = currentPosition;
                int end = (start + length - 1) % totalLength;
                int steps = length / 2;
                while (steps > 0) {
                    Permutation.swap(data, start, end);
                    start = (start + 1) % totalLength;
                    end--;
                    if (end < 0) {
                        end = totalLength - 1;
                    }
                    steps--;
                }
                currentPosition = (currentPosition + length + skip) % totalLength;
                skip++;
            }
        }

        return data;
    }

    private int[] initCircularList() {
        int[] data = new int[totalLength];
        for (int n = 0; n < totalLength; n++) {
            data[n] = n;
        }
        return data;
    }

}
