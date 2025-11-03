package net.eugenpaul.adventofcode.y2024.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2024Day8() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day8/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day8 event = new Day8();
        assertEquals(14, event.doPuzzle1(testFilePath));
        assertEquals(34, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(220, event.getTotalScore());
        assertEquals(813, event.getTotalScore2());
    }
}
