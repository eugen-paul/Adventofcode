package net.eugenpaul.adventofcode.y2016.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day17Test {

    @Test
    void testTest2016Day17_puzzle1() {
        testPuzzle1(//
                "ihgpwlah", //
                "DDRRRD", //
                370 //
        );
        testPuzzle1(//
                "kglvqrro", //
                "DDUDRLRRUDRD", //
                492 //
        );
        testPuzzle1(//
                "ulqzkmiv", //
                "DRURDRUDDLLDLUURRDULRLDUUDDDRR", //
                830);
    }

    private void testPuzzle1(String inputData, String shortestPath, int longestPathLength) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(shortestPath, event.getShortestPath());
        assertEquals(longestPathLength, event.getLongestPathLength());
    }

}
