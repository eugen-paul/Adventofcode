package net.eugenpaul.adventofcode.y2023.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2023Day23() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day23/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day23 event = new Day23();
        assertEquals(94, event.doPuzzle1(testFilePath));
        assertEquals(154, event.doPuzzle2(testFilePath));
    }

    @Test
    void testTest2023Day23A() {
        testPuzzleA(FileReaderHelper.readListStringFromFile("y2023/day23/test_puzzleA.txt"));
    }

    private void testPuzzleA(List<String> testFilePath) {
        Day23 event = new Day23();
        // assertEquals(94, event.doPuzzle1(testFilePath));
        assertEquals(14, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(2206, event.getTotalScore());
        assertEquals(6490, event.getTotalScore2());
    }
}
