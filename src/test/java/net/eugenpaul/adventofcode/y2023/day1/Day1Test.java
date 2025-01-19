package net.eugenpaul.adventofcode.y2023.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2023Day1() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day1/test_puzzle.txt"));
        testPuzzle2(FileReaderHelper.readListStringFromFile("y2023/day1/test_puzzle2.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day1 event = new Day1();
        System.out.println(event.doPuzzle1(testFilePath));
    }
    
    private void testPuzzle2(List<String> testFilePath) {
        Day1 event = new Day1();
        System.out.println(event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(55002, event.getTotalScore());
        assertEquals(55093, event.getTotalScore2());
    }
}
