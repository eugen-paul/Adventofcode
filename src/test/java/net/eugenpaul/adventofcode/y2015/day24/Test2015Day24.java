package net.eugenpaul.adventofcode.y2015.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day24
 */
class Test2015Day24 {

    @Test
    void test2015Day24_puzzle() {
        Day24 puzzle = new Day24();

        puzzle.doEvent(List.of("1", "2", "3", "4", "5", "7", "8", "9", "10", "11"));
        assertEquals(99, puzzle.getQuantumEntanglement3());
        assertEquals(44, puzzle.getQuantumEntanglement4());
    }

    @Test
    void testSolution2015Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(10439961859L, event.getQuantumEntanglement3());
        assertEquals(72050269L, event.getQuantumEntanglement4());
    }

}