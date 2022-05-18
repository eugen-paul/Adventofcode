package net.eugenpaul.adventofcode.y2016.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

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

    @Test
    void testSolution2016Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(234, event.getDistance());
        assertEquals(113, event.getDistancePuzzle2());
    }
}
