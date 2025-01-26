package net.eugenpaul.adventofcode.y2023.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2023Day10() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day10 event = new Day10();
        assertEquals(4, event.doPuzzle1(testFilePath));
    }

    @Test
    void testTest2023Day10b() {
        testPuzzleb(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle1b.txt"));
    }

    private void testPuzzleb(List<String> testFilePath) {
        Day10 event = new Day10();
        assertEquals(8, event.doPuzzle1(testFilePath));
    }

    @Test
    void testTest2023Day10_2a() {
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle2a.txt"), 4);
    }

    @Test
    void testTest2023Day10_2b() {
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle2b.txt"), 4);
    }

    @Test
    void testTest2023Day10_2c() {
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle2c.txt"), 8);
    }

    @Test
    void testTest2023Day10_2d() {
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day10/test_puzzle2d.txt"), 10);
    }

    private void testPuzzle2(List<String> testFilePath, int resp) {
        Day10 event = new Day10();
        assertEquals(resp, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day9() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(6820, event.getTotalScore());
        assertEquals(337, event.getTotalScore2());
    }
}
