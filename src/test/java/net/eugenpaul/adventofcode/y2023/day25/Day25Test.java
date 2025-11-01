package net.eugenpaul.adventofcode.y2023.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2023Day25() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day25/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day25 event = new Day25();
        assertEquals(54, event.doPuzzle1(testFilePath));
        //assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(518391, event.getTotalScore());
        //assertEquals(0, event.getTotalScore2());
    }
}
