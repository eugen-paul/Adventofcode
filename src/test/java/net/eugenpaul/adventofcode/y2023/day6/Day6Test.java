package net.eugenpaul.adventofcode.y2023.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2023Day6() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day6/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day6 event = new Day6();
        assertEquals(288, event.doPuzzle1(testFilePath));
        assertEquals(71503, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(449820, event.getTotalScore());
        assertEquals(42250895, event.getTotalScore2());
    }
}
