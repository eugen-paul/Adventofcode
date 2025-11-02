package net.eugenpaul.adventofcode.y2024.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2024Day4A() {
        testPuzzleA(FileReaderHelper.readListStringFromFile("y2024/day4/test_puzzle1A.txt"));
    }

    private void testPuzzleA(List<String> testFilePath) {
        Day4 event = new Day4();
        assertEquals(4, event.doPuzzle1(testFilePath));
        //assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testTest2024Day4B() {
        testPuzzleB(FileReaderHelper.readListStringFromFile("y2024/day4/test_puzzle1B.txt"));
    }

    private void testPuzzleB(List<String> testFilePath) {
        Day4 event = new Day4();
        assertEquals(18, event.doPuzzle1(testFilePath));
        //assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testTest2024Day4_2A() {
        testPuzzle2A(FileReaderHelper.readListStringFromFile("y2024/day4/test_puzzle2A.txt"));
    }

    private void testPuzzle2A(List<String> testFilePath) {
        Day4 event = new Day4();
        // assertEquals(4, event.doPuzzle1(testFilePath));
        assertEquals(1, event.doPuzzle2(testFilePath));
    }

    @Test
    void testTest2024Day4_2B() {
        testPuzzle2B(FileReaderHelper.readListStringFromFile("y2024/day4/test_puzzle2B.txt"));
    }

    private void testPuzzle2B(List<String> testFilePath) {
        Day4 event = new Day4();
        // assertEquals(18, event.doPuzzle1(testFilePath));
        assertEquals(9, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(2534, event.getTotalScore());
        assertEquals(1866, event.getTotalScore2());
    }
}
