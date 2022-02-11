package net.eugenpaul.adventofcode.y2016.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day15Test {

    @Test
    void testTest2016Day15_puzzle1() {
        testPuzzle1(//
                "110010110100", //
                "100", //
                12 //
        );
        testPuzzle1(//
                "10000", //
                "01100", //
                20 //
        );
    }

    private void testPuzzle1(String inputData, String checksum, int length) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromData(inputData, length, length));
        assertEquals(checksum, event.getChecksum());
    }

}
