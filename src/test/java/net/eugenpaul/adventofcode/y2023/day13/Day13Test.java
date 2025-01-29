package net.eugenpaul.adventofcode.y2023.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testTest2023Day13() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day13/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day13 event = new Day13();
        assertEquals(405, event.doPuzzle1(testFilePath));
        assertEquals(400, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(37025, event.getTotalScore());
        assertEquals(32854, event.getTotalScore2());
    }
}
