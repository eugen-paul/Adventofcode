package net.eugenpaul.adventofcode.y2017.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day6Test {

    @Test
    void testTest2017Day6_puzzle1() {
        testPuzzle1(List.of(//
                "0 2 7 0" //
        ), //
                5, 4 //
        );
    }

    private void testPuzzle1(List<String> inputData, int steps, int cycles) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getSteps());
        assertEquals(cycles, event.getCycles());
    }

}
