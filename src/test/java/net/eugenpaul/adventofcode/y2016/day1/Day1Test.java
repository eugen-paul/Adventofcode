package net.eugenpaul.adventofcode.y2016.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day1Test {

    @Test
    void testTest2016Day1_puzzle_1() {
        testPuzzle1("R2, L3", 5);
        testPuzzle1("R2, R2, R2", 2);
        testPuzzle1("R5, L5, R5, R3", 12);
    }

    private void testPuzzle1(String inputData, int distance) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(distance, event.getDistance());
    }

    @Test
    void testTest2016Day1_puzzle_2() {
        testPuzzle2("R8, R4, R4, R8", 4);
    }

    private void testPuzzle2(String inputData, int distance) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(distance, event.getDistancePuzzle2());
    }
}
