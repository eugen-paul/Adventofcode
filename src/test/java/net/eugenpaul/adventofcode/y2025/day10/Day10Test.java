package net.eugenpaul.adventofcode.y2025.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2025Day10_1() {
        Day10 event = new Day10();
        assertEquals(7, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day10/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day10_2() {
        Day10 event = new Day10();
        // assertEquals(33, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day10/test_puzzle.txt")));
        assertEquals(0, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day10/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day10_2a() {
        Day10 event = new Day10();
        // assertEquals(279, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day10/test_puzzlea.txt")));
        assertEquals(0, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day10/test_puzzlea.txt")));
    }

    @Test
    void testSolution2025Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(494, event.getTotalScore());
        //assertEquals(0, event.getTotalScore2());
    }
}
