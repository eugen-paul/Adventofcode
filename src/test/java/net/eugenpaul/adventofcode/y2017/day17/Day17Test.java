package net.eugenpaul.adventofcode.y2017.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day17Test {

    @Test
    void testTest2017Day17_puzzle1() {
        testPuzzle1(List.of(//
                "3" //
        ), 638);
    }

    private void testPuzzle1(List<String> inputData, int valueAfter2017) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(valueAfter2017, event.getValueAfter2017());
    }
}
