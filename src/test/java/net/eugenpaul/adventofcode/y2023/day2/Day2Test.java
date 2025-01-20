package net.eugenpaul.adventofcode.y2023.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2023Day2() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day2/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day2 event = new Day2();
        assertEquals(8, event.doPuzzle1(testFilePath));
        assertEquals(2286, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(2727, event.getTotalScore());
        assertEquals(56580, event.getTotalScore2());
    }
}
