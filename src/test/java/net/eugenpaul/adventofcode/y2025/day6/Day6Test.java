package net.eugenpaul.adventofcode.y2025.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2025Day6_1() {
        Day6 event = new Day6();
        assertEquals(4277556L, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day6/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day6_2() {
        Day6 event = new Day6();
        assertEquals(3263827, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day6/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(5227286044585L, event.getTotalScore());
        assertEquals(10227753257799L, event.getTotalScore2());
    }
}
