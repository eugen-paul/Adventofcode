package net.eugenpaul.adventofcode.y2018.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2018Day5_puzzle1() {
        testPuzzle1(List.of(//
                "aA" //
        ), 0, 0);

        testPuzzle1(List.of(//
                "abBA" //
        ), 0, 0);

        testPuzzle1(List.of(//
                "abAB" //
        ), 4, 0);

        testPuzzle1(List.of(//
                "aabAAB" //
        ), 6, 0);

        testPuzzle1(List.of(//
                "dabAcCaCBAcCcaDA" //
        ), 10, 4);
    }

    private void testPuzzle1(List<String> inputData, long unitsAfterReacting, long shortestPolymer) {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(unitsAfterReacting, event.getUnitsAfterReacting());
    }

    @Test
    void testSolution2018Day4() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(10978, event.getUnitsAfterReacting());
        assertEquals(4840, event.getShortestPolymer());
    }

}
