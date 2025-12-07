package net.eugenpaul.adventofcode.y2025.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2025Day7_1() {
        Day7 event = new Day7();
        assertEquals(21, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day7/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day7_2() {
        Day7 event = new Day7();
        assertEquals(40, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day7/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1516, event.getTotalScore());
        assertEquals(1393669447690L, event.getTotalScore2());
    }
}
