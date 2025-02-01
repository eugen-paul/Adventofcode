package net.eugenpaul.adventofcode.y2023.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2023Day17() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day17/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day17 event = new Day17();
        assertEquals(102, event.doPuzzle1(testFilePath));
        assertEquals(94, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(722, event.getTotalScore());
        assertEquals(894, event.getTotalScore2());
    }
}
