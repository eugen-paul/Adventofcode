package net.eugenpaul.adventofcode.y2024.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2024Day18() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day18/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day18 event = new Day18();
        event.setSize(7);
        event.setBytes1(12);
        assertEquals(22, event.doPuzzle1(testFilePath));
        assertEquals("6,1", event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(320, event.getTotalScore());
        assertEquals("34,40", event.getTotalScore2());
    }
}
