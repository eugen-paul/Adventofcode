package net.eugenpaul.adventofcode.y2017.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day19Test {

    @Test
    void testTest2017Day19_puzzle1() {
        testPuzzle1(List.of(//
                "     |          ", //
                "     |  +--+    ", //
                "     A  |  C    ", //
                " F---|----E|--+ ", //
                "     |  |  |  D ", //
                "     +B-+  +--+ "//
        ), "ABCDEF", 38);
    }

    private void testPuzzle1(List<String> inputData, String letters, int counter) {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(letters, event.getLetters());
        assertEquals(counter, event.getCounter());
    }

}
