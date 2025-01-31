package net.eugenpaul.adventofcode.y2023.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2023Day15() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day15/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day15 event = new Day15();
        assertEquals(1320, event.doPuzzle1(testFilePath));
        assertEquals(145, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(516657, event.getTotalScore());
        assertEquals(210906, event.getTotalScore2());
    }
}
