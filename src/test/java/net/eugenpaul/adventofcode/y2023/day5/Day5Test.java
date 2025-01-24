package net.eugenpaul.adventofcode.y2023.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2023Day5() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day5/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day5 event = new Day5();
        assertEquals(35, event.doPuzzle1(testFilePath));
        assertEquals(46, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(226172555, event.getTotalScore());
        assertEquals(47909639, event.getTotalScore2());
    }
}
