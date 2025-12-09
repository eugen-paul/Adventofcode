package net.eugenpaul.adventofcode.y2025.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2025Day9_1() {
        Day9 event = new Day9();
        assertEquals(50, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day9/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day9_2() {
        Day9 event = new Day9();
        assertEquals(24, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day9/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(4745816424L, event.getTotalScore());
        assertEquals(1351617690L, event.getTotalScore2());
        
    }
}
