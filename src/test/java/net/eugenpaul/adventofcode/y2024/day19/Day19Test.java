package net.eugenpaul.adventofcode.y2024.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2024Day19() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day19/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day19 event = new Day19();
        assertEquals(6, event.doPuzzle1(testFilePath));
        assertEquals(16, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(367, event.getTotalScore());
        assertEquals(724388733465031L, event.getTotalScore2());
    }
}
