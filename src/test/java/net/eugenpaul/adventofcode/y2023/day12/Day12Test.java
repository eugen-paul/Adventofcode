package net.eugenpaul.adventofcode.y2023.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2023Day12() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day12/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day12 event = new Day12();
        assertEquals(21, event.doPuzzle1(testFilePath));
        assertEquals(525152, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(6488, event.getTotalScore());
        assertEquals(815364548481L, event.getTotalScore2());
    }
}
