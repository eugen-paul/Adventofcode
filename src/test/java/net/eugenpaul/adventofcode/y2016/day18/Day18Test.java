package net.eugenpaul.adventofcode.y2016.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day18Test {

    @Test
    void testTest2016Day18_puzzle1() {
        testPuzzle1(//
                "..^^.", //
                6, //
                3 //
        );
        testPuzzle1(//
                ".^^.^.^^^^", //
                38, //
                10 //
        );
    }

    private void testPuzzle1(String inputData, int safeTiles, int rows) {
        Day18 event = new Day18();

        assertTrue(event.doPuzzleFromData(inputData, rows));
        assertEquals(safeTiles, event.getSafeTiles());
    }

}
