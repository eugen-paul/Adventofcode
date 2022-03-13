package net.eugenpaul.adventofcode.y2017.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day3Test {

    @Test
    void testTest2017Day3_puzzle1() {
        testPuzzle1("12", 3);
        testPuzzle1("23", 2);
        testPuzzle1("25", 4);
        testPuzzle1("26", 5);
        testPuzzle1("27", 4);
        testPuzzle1("1024", 31);
    }

    private void testPuzzle1(String inputData, int steps) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getSteps());
    }

    @Test
    void testTest2017Day3_puzzle2() {
        testPuzzle2("9", 10);
        testPuzzle2("20", 23);
        testPuzzle2("50", 54);
        testPuzzle2("100", 122);
        testPuzzle2("300", 304);
        testPuzzle2("747", 806);
        testPuzzle2("800", 806);
    }

    private void testPuzzle2(String inputData, int solution2) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(solution2, event.getSolution2());
    }

}
