package net.eugenpaul.adventofcode.y2024.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2024Day24_1() {
        Day24 event = new Day24();
        assertEquals(4, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day24/test_puzzle1A.txt")));
        assertEquals(2024, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day24/test_puzzle1B.txt")));
    }

    // @Test
    // void testTest2024Day24_2() {
    //     Day24 event = new Day24();
    //     assertEquals("z00,z01,z02,z05", event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day24/test_puzzle2A.txt")));
    // }

    @Test
    void testSolution2024Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(58639252480880L, event.getTotalScore());
        assertEquals("", event.getTotalScore2());
    }
}
