package net.eugenpaul.adventofcode.y2022.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2022Day9_part1() {
        testPuzzle_1("y2022/day9/test_puzzle1.txt", 13);
    }

    private void testPuzzle_1(String inputFile, int tailPositions) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(tailPositions, event.getTailPositions());
    }

    @Test
    void testTest2022Day9_part2() {
        testPuzzle_2("y2022/day9/test_puzzle2.txt", 36);
    }

    private void testPuzzle_2(String inputFile, int tailPositions10) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(tailPositions10, event.getTailPositions10());
    }

    @Test
    void testSolution2022Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5874, event.getTailPositions());
        assertEquals(2467, event.getTailPositions10());
    }

}
