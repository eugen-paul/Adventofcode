package net.eugenpaul.adventofcode.y2023.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2023Day16() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day16/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day16 event = new Day16();
        assertEquals(46, event.doPuzzle1(testFilePath));
        assertEquals(51, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(7788, event.getTotalScore());
        assertEquals(7987, event.getTotalScore2());
    }
}
