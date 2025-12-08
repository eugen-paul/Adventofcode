package net.eugenpaul.adventofcode.y2025.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2025Day8_1() {
        Day8 event = new Day8();
        event.s1 = 10;
        assertEquals(40, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day8/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day8_2() {
        Day8 event = new Day8();
        assertEquals(25272, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day8/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(79056L, event.getTotalScore());
        assertEquals(4639477L, event.getTotalScore2());
    }
}
