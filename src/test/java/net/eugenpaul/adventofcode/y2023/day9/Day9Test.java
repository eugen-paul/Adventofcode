package net.eugenpaul.adventofcode.y2023.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2023Day9() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day9/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day9 event = new Day9();
        assertEquals(114, event.doPuzzle1(testFilePath));
        assertEquals(2, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1930746032, event.getTotalScore());
        assertEquals(1154, event.getTotalScore2());
    }
}
