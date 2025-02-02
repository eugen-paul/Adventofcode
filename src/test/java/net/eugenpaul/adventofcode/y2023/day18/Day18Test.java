package net.eugenpaul.adventofcode.y2023.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2023Day18() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day18/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day18 event = new Day18();
        assertEquals(62, event.doPuzzle1(testFilePath));
        assertEquals(952408144115L, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(56678, event.getTotalScore());
        assertEquals(79088855654037L, event.getTotalScore2());
    }
}
