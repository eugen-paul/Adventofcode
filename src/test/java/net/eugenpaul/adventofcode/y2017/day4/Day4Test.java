package net.eugenpaul.adventofcode.y2017.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day4Test {

    @Test
    void testTest2017Day4_puzzle1() {
        testPuzzle1(List.of(//
                "aa bb cc dd ee", //
                "aa bb cc dd aa", //
                "aa bb cc dd aaa" //
        ), //
                2 //
        );
        testPuzzle1(List.of(//
                "aa bb cc dd ee", //
                "aa bb cc dd aa" //
        ), //

                1 //
        );
        testPuzzle1(List.of(//
                "aa bb cc dd ee", //
                "aa bb cc dd aaa" //
        ), //
                2 //
        );
    }

    private void testPuzzle1(List<String> inputData, int validCount) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(validCount, event.getValidCount());
    }

    @Test
    void testTest2017Day4_puzzle2() {
        testPuzzle2(List.of(//
                "abcde fghij", //
                "abcde xyz ecdab", //
                "a ab abc abd abf abj", //
                "iiii oiii ooii oooi oooo", //
                "oiii ioii iioi iiio"//
        ), //
                3 //
        );
        testPuzzle2(List.of(//
                "abcde xyz ecdab", //
                "oiii ioii iioi iiio"//
        ), //
                0 //
        );
        testPuzzle2(List.of(//
                "abcde fghij", //
                "a ab abc abd abf abj", //
                "iiii oiii ooii oooi oooo" //
        ), //
                3 //
        );
    }

    private void testPuzzle2(List<String> inputData, int validCount2) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(validCount2, event.getValidCount2());
    }

}
