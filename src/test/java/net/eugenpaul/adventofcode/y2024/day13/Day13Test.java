package net.eugenpaul.adventofcode.y2024.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testTest2024Day13() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day13/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day13 event = new Day13();
        assertEquals(480, event.doPuzzle1(testFilePath));
        assertEquals(875318608908L, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(29436, event.getTotalScore());
        assertEquals(103729094227877L, event.getTotalScore2());
    }
}
