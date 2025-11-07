package net.eugenpaul.adventofcode.y2024.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2024Day22_1() {
        Day22 event = new Day22();
        assertEquals(37327623, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day22/test_puzzle1.txt")));
    }

    @Test
    void testTest2024Day22_2() {
        Day22 event = new Day22();
        assertEquals(23, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day22/test_puzzle2.txt")));
    }

    @Test
    void testSolution2024Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(20215960478L, event.getTotalScore());
        assertEquals(2221, event.getTotalScore2());
    }
}
