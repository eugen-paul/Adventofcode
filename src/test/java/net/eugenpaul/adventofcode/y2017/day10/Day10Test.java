package net.eugenpaul.adventofcode.y2017.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day10Test {

    @Test
    void testTest2017Day10_puzzle1() {
        testPuzzle1(List.of("3,4,1,5"), 5, 12);
    }

    private void testPuzzle1(List<String> inputData, int length, int hash) {
        Day10 event = new Day10();
        event.setTotalLength(length);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(hash, event.getHash());
    }

    @Test
    void testTest2017Day10_puzzle2a() {
        testPuzzle2(List.of("1,2,3"), "3efbe78a8d82f29979031a4aa0b16a9d");
    }

    @Test
    void testTest2017Day10_puzzle2b() {
        testPuzzle2(List.of("1,2,4"), "63960835bcdc130f0b66d7ff4f6a5a8e");
    }

    private void testPuzzle2(List<String> inputData, String hash) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(hash, event.getHashTotal());
    }
}
