package net.eugenpaul.adventofcode.y2018.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2018Day25_puzzle1() {
        testPuzzle1(List.of(//
                "+1", //
                "-2", //
                "+3", //
                "+1" //
        ), 3);
    }

    private void testPuzzle1(List<String> inputData, long resultingFrequency) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(resultingFrequency, event.getResultingFrequency());
    }

    @Test
    void testTest2018Day25_puzzle2() {
        testPuzzle2(List.of(//
                "+1", //
                "-2", //
                "+3", //
                "+1", //
                "+1", //
                "-2" //
        ), 2);

        testPuzzle2(List.of(//
                "+1", //
                "-1" //
        ), 0);

        testPuzzle2(List.of(//
                "+3", //
                "+3", //
                "+4", //
                "-2", //
                "-4" //
        ), 10);
    }

    private void testPuzzle2(List<String> inputData, long firstTwice) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(firstTwice, event.getFirstTwice());
    }

    @Test
    void testSolution2018Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(479, event.getResultingFrequency());
        assertEquals(66105, event.getFirstTwice());
    }

}
