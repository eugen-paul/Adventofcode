package net.eugenpaul.adventofcode.y2024.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2024Day16_1() {
        Day16 event = new Day16();
        assertEquals(7036, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day16/test_puzzle1A.txt")));
        assertEquals(11048, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day16/test_puzzle1B.txt")));
    }

    @Test
    void testTest2024Day16_2() {
        Day16 event = new Day16();
        assertEquals(45, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day16/test_puzzle1A.txt")));
        assertEquals(64, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day16/test_puzzle1B.txt")));
    }

    @Test
    void testSolution2024Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(143564, event.getTotalScore());
        assertEquals(593, event.getTotalScore2());
    }
}
