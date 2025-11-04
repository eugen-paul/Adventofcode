package net.eugenpaul.adventofcode.y2024.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2024Day15_1() {
        Day15 event = new Day15();
        assertEquals(2028, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day15/test_puzzle1A.txt")));
        assertEquals(10092, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day15/test_puzzle1B.txt")));
    }

    @Test
    void testTest2024Day15_2() {
        Day15 event = new Day15();
        assertEquals(618, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day15/test_puzzle2A.txt")));
        assertEquals(9021, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day15/test_puzzle1B.txt")));
    }

    private void testPuzzle(List<String> testFilePath) {
        // assertEquals(0, event.doPuzzle2(testFilePath));
    }
    // @Test
    // void testTest2024Day15() {
    // testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day15/test_puzzle.txt"));
    // }

    // private void testPuzzle(List<String> testFilePath) {
    // Day15 event = new Day15();
    // assertEquals(0, event.doPuzzle1(testFilePath));
    // //assertEquals(0, event.doPuzzle2(testFilePath));
    // }

    @Test
    void testSolution2024Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1413675, event.getTotalScore());
        assertEquals(1399772, event.getTotalScore2());
    }
}
