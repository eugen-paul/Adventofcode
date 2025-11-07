package net.eugenpaul.adventofcode.y2023.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2023Day24() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day24/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day24 event = new Day24();
        event.setTestA(true);
        assertEquals(2, event.doPuzzle1(testFilePath));
        // assertEquals(47, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(20963, event.getTotalScore());
        assertEquals(999782576459892L, event.getTotalScore2());
    }
}
