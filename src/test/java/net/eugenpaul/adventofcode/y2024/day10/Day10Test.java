package net.eugenpaul.adventofcode.y2024.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2024Day10_1() {
        Day10 event = new Day10();
        assertEquals(1, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle1A.txt")));
        assertEquals(2, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle1B.txt")));
        assertEquals(4, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle1C.txt")));
        assertEquals(3, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle1D.txt")));
        assertEquals(36, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle1E.txt")));
    }

    @Test
    void testTest2024Day10_2() {
        Day10 event = new Day10();
        assertEquals(3, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle2A.txt")));
        assertEquals(13, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle2B.txt")));
        assertEquals(227, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle2C.txt")));
        assertEquals(81, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day10/test_puzzle2D.txt")));
    }

    @Test
    void testSolution2024Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(841, event.getTotalScore());
        assertEquals(1875, event.getTotalScore2());
    }
}
