package net.eugenpaul.adventofcode.y2022.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2022Day10_part1() {
        testPuzzle_1("y2022/day10/test_puzzle1.txt", 13140);
    }

    private void testPuzzle_1(String inputFile, int tailPositions) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(tailPositions, event.getSumSixSiglanStrengths());
    }

    @Test
    void testSolution2022Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(13920, event.getSumSixSiglanStrengths());
        //Part 2: EGLHBLFJ
    }

}
