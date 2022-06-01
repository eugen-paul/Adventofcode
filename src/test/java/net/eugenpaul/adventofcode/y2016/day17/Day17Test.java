package net.eugenpaul.adventofcode.y2016.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2016Day17_puzzle1() {
        testPuzzle(//
                "ihgpwlah", //
                "DDRRRD", //
                370 //
        );
        testPuzzle(//
                "kglvqrro", //
                "DDUDRLRRUDRD", //
                492 //
        );
        testPuzzle(//
                "ulqzkmiv", //
                "DRURDRUDDLLDLUURRDULRLDUUDDDRR", //
                830);
    }

    private void testPuzzle(String inputData, String shortestPath, int longestPathLength) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(shortestPath, event.getShortestPath());
        assertEquals(longestPathLength, event.getLongestPathLength());
    }

    @Test
    void testSolution2016Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("RDURRDDLRD", event.getShortestPath());
        assertEquals(526, event.getLongestPathLength());
    }

}
