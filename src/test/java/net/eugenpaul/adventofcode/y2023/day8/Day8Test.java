package net.eugenpaul.adventofcode.y2023.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2023Day8() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day8/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day8 event = new Day8();
        assertEquals(2, event.doPuzzle1(testFilePath));
    }

    @Test
    void testTest2023Day8_2() {
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day8/test_puzzle2.txt"));
    }

    private void testPuzzle2(List<String> testFilePath) {
        Day8 event = new Day8();
        assertEquals(6, event.doPuzzle1(testFilePath));
    }

    @Test
    void testTest2023Day8_3() {
        testPuzzle3(FileReaderHelper.readListStringFromFile("y2023/day8/test_puzzle3.txt"));
    }

    private void testPuzzle3(List<String> testFilePath) {
        Day8 event = new Day8();
        assertEquals(6, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(21797, event.getTotalScore());
        assertEquals(23977527174353L, event.getTotalScore2());
    }
}
