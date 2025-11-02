package net.eugenpaul.adventofcode.y2024.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2024Day3() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day3/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day3 event = new Day3();
        assertEquals(161, event.doPuzzle1(testFilePath));
    }

    @Test
    void testTest2024Day3_2() {
        testPuzzle_2(FileReaderHelper.readListStringFromFile("y2024/day3/test_puzzle2.txt"));
    }

    private void testPuzzle_2(List<String> testFilePath) {
        Day3 event = new Day3();
        assertEquals(48, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(187194524, event.getTotalScore());
        assertEquals(127092535, event.getTotalScore2());
    }
}
