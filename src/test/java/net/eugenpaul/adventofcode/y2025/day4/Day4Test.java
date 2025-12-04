package net.eugenpaul.adventofcode.y2025.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2025Day4_1() {
        Day4 event = new Day4();
        assertEquals(13, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day4/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day4_2() {
        Day4 event = new Day4();
        assertEquals(43, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day4/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1457, event.getTotalScore());
        assertEquals(8310, event.getTotalScore2());
    }
}
