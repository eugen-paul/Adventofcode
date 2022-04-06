package net.eugenpaul.adventofcode.y2017.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day22Test {

    @Test
    void testTest2017Day22_puzzle1() {
        testPuzzle1(List.of(//
                "..#", //
                "#..", //
                "..." //
        ), 5587, 2511944);
    }

    private void testPuzzle1(List<String> inputData, int becomeInfected, int becomeInfected2) {
        Day22 event = new Day22();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(becomeInfected, event.getBecomeInfected());
        assertEquals(becomeInfected2, event.getBecomeInfected2());
    }

}
