package net.eugenpaul.adventofcode.y2020.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2020Day10_testPuzzle_1() {
        testPuzzle(List.of(//
                "16", //
                "10", //
                "15", //
                "5", //
                "1", //
                "11", //
                "7", //
                "19", //
                "6", //
                "12", //
                "4" //
        ), 35, 62);
    }

    @Test
    void testTest2020Day10_testPuzzle_2() {
        testPuzzle(List.of(//
                "28", //
                "33", //
                "18", //
                "42", //
                "31", //
                "14", //
                "46", //
                "20", //
                "48", //
                "47", //
                "24", //
                "23", //
                "49", //
                "45", //
                "19", //
                "38", //
                "39", //
                "11", //
                "1", //
                "32", //
                "25", //
                "35", //
                "8", //
                "17", //
                "7", //
                "9", //
                "4", //
                "2", //
                "34", //
                "10", //
                "3" //
        ), 220, 62);
    }

    private void testPuzzle(List<String> inputData, int oneJoltDifferences, long number2) {
        Day10 event = new Day10();

        assertTrue(event.doEvent(inputData));
        assertEquals(oneJoltDifferences, event.getSolution1());
    }

    @Test
    void testSolution2020Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2664, event.getSolution1());
        assertEquals(148098383347712L, event.getWays());
    }

}
