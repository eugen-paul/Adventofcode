package net.eugenpaul.adventofcode.y2017.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2017Day11_puzzle1() {
        testPuzzle1(List.of("n"), 1);
        testPuzzle1(List.of("ne"), 1);
        testPuzzle1(List.of("se"), 1);
        testPuzzle1(List.of("s"), 1);
        testPuzzle1(List.of("sw"), 1);
        testPuzzle1(List.of("nw"), 1);
        testPuzzle1(List.of("ne,ne,ne"), 3);
        testPuzzle1(List.of("n,ne,ne,ne"), 4);
        testPuzzle1(List.of("ne,ne,sw,sw"), 0);
        testPuzzle1(List.of("ne,ne,s,s"), 2);
        testPuzzle1(List.of("se,sw,se,sw,sw"), 3);
        testPuzzle1(List.of("sw,sw,s"), 3);
        testPuzzle1(List.of("sw,sw,sw,s,s"), 5);
        testPuzzle1(List.of("s,s,s,s,s"), 5);
        testPuzzle1(List.of("nw,ne,se,sw"), 0);
    }

    private void testPuzzle1(List<String> inputData, int distance) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(distance, event.getDistance());
    }

    @Test
    void testSolution2017Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2017/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(808, event.getDistance());
        assertEquals(1556, event.getMaxDistance());
    }
}
