package net.eugenpaul.adventofcode.y2017.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2017Day2_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "5 1 9 5", //
                        "7 5 3", //
                        "2 4 6 8"//
                ), //
                18 //
        );
    }

    private void testPuzzle1(List<String> inputData, int sum) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum, event.getChecksum());
    }

    @Test
    void testTest2017Day2_puzzle2() {
        testPuzzle2(//
                List.of(//
                        "5 9 2 8", //
                        "9 4 7 3", //
                        "3 8 6 5"//
                ), //
                9 //
        );
    }

    private void testPuzzle2(List<String> inputData, int sum2) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum2, event.getChecksum2());
    }

    @Test
    void testSolution2017Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2017/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(44670, event.getChecksum());
        assertEquals(285, event.getChecksum2());
    }

}
