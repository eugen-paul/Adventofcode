package net.eugenpaul.adventofcode.y2023.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2023Day4() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day4/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day4 event = new Day4();
        assertEquals(13, event.doPuzzle1(testFilePath));
        assertEquals(30, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(24542, event.getTotalScore());
        assertEquals(8736438, event.getTotalScore2());
    }
}
