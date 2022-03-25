package net.eugenpaul.adventofcode.y2017.day14;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.HexConverter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2017.day10.Day10;

public class Day14 extends SolutionTemplate {

    @Getter
    private Integer squares;
    @Getter
    private Integer regions;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2017/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        List<String> hashsBinary = new LinkedList<>();
        Day10 d10 = new Day10();

        for (int i = 0; i < 128; i++) {
            String input = eventData + "-" + i;
            String hash = d10.puzzle2(input);
            String hashBinary = HexConverter.hexCharsToBinaryString(hash);
            hashsBinary.add(hashBinary);
        }

        squares = hashsBinary.stream()//
                .map(v -> v.chars()//
                        .filter(c -> c == '1')//
                        .count())//
                .reduce(0L, (a, b) -> a + b)//
                .intValue();

        regions = doPuzzle2(hashsBinary);

        logger.log(Level.INFO, () -> "squares : " + getSquares());
        logger.log(Level.INFO, () -> "regions : " + getRegions());

        return true;
    }

    private int doPuzzle2(List<String> hashsBinary) {
        char[][] fullMap = new char[128][128];
        for (int i = 0; i < 128; i++) {
            System.arraycopy(hashsBinary.get(i).toCharArray(), 0, fullMap[i], 0, 128);
        }

        int responseCounter = 0;
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                if (fullMap[x][y] == '1') {
                    fillRegion(fullMap, x, y);
                    responseCounter++;
                }
            }
        }

        return responseCounter;
    }

    public void fillRegion(char[][] fullMap, int x, int y) {
        if (fullMap[x][y] != '1') {
            return;
        }

        fullMap[x][y] = 'x';
        if (x > 0) {
            fillRegion(fullMap, x - 1, y);
        }
        if (y > 0) {
            fillRegion(fullMap, x, y - 1);
        }
        if (x < 127) {
            fillRegion(fullMap, x + 1, y);
        }
        if (y < 127) {
            fillRegion(fullMap, x, y + 1);
        }
    }

}
