package net.eugenpaul.adventofcode.y2025.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2025Day5_1() {
        Day5 event = new Day5();
        assertEquals(3, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day5/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day5_2() {
        Day5 event = new Day5();
        assertEquals(14, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day5/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(643, event.getTotalScore());
        assertEquals(342018167474526L, event.getTotalScore2());
    }
}
