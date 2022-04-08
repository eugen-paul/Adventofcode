package net.eugenpaul.adventofcode.y2017.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day24Test {

    @Test
    void testTest2017Day24_puzzle1() {
        testPuzzle1(List.of(//
                "0/2", //
                "2/2", //
                "2/3", //
                "3/4", //
                "3/5", //
                "0/1", //
                "10/1", //
                "9/10" //
        ), 31, 19);
    }

    private void testPuzzle1(List<String> inputData, long strongestBridge, long longestStrongestBridge) {
        Day24 event = new Day24();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(strongestBridge, event.getStrongestBridge());
        assertEquals(longestStrongestBridge, event.getLongestStrongestBridge());
    }

}
