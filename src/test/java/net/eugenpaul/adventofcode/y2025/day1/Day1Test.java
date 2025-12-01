package net.eugenpaul.adventofcode.y2025.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2025Day1_1() {
        Day1 event = new Day1();
        assertEquals(3, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day1/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day1_2() {
        Day1 event = new Day1();
        assertEquals(6, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day1/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1191, event.getTotalScore());
        assertEquals(6858, event.getTotalScore2());
    }
}
