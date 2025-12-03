package net.eugenpaul.adventofcode.y2025.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2025Day3_1() {
        Day3 event = new Day3();
        assertEquals(357, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day3/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day3_2() {
        Day3 event = new Day3();
        assertEquals(3121910778619L, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day3/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(17376L, event.getTotalScore());
        assertEquals(172119830406258L, event.getTotalScore2());
    }
}
