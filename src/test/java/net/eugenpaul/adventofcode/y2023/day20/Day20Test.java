package net.eugenpaul.adventofcode.y2023.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2023Day20_a() {
        // testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day20/test_puzzle.txt"), 32000000L);
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day20/test_puzzle1b.txt"), 11687500L);
    }

    private void testPuzzle(List<String> testFilePath, long resp) {
        Day20 event = new Day20();
        assertEquals(resp, event.doPuzzle1(testFilePath));
    }

    @Test
    void testSolution2023Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(949764474L, event.getTotalScore());
        assertEquals(243221023462303L, event.getTotalScore2());
    }
}
