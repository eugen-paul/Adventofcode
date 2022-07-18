package net.eugenpaul.adventofcode.y2020.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testTest2020Day13_testPuzzle() {
        testPuzzle(List.of(//
                "939", //
                "7,13,x,x,59,x,31,19" //
        ), 295, 1068781);
    }

    private void testPuzzle(List<String> inputData, int solution1, long solution2) {
        Day13 event = new Day13();

        assertTrue(event.doEvent(inputData));
        assertEquals(solution1, event.getSolution1());
        assertEquals(solution2, event.getSolution2());
    }

    @Test
    void testTest2020Day13_testPuzzle2() {
        testPuzzle2(List.of(//
                "939", //
                "17,x,13,19" //
        ), 3417);
        testPuzzle2(List.of(//
                "939", //
                "67,7,59,61" //
        ), 754018);
        testPuzzle2(List.of(//
                "939", //
                "67,7,x,59,61" //
        ), 1261476);
        testPuzzle2(List.of(//
                "939", //
                "1789,37,47,1889" //
        ), 1202161486);
    }

    private void testPuzzle2(List<String> inputData, long solution2) {
        Day13 event = new Day13();

        assertTrue(event.doEvent(inputData));
        assertEquals(solution2, event.getSolution2());
    }

    @Test
    void testSolution2020Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2095, event.getSolution1());
        assertEquals(598411311431841L, event.getSolution2());
    }

}
