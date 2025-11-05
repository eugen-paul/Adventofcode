package net.eugenpaul.adventofcode.y2024.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2024Day17_1() {
        Day17 event = new Day17();
        assertEquals("4,6,3,5,6,3,5,2,1,0", event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day17/test_puzzle1.txt")));
    }

    @Test
    void testTest2024Day17_2() {
        Day17 event = new Day17();
        assertEquals(117440, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day17/test_puzzle2.txt")));
    }

    @Test
    void testSolution2024Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals("4,1,5,3,1,5,3,5,7", event.getTotalScore());
        assertEquals(164542125272765L, event.getTotalScore2());
    }
}
