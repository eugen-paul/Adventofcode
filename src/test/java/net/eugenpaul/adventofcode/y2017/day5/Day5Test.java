package net.eugenpaul.adventofcode.y2017.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day5Test {

    @Test
    void testTest2017Day5_puzzle1() {
        testPuzzle1(List.of(//
                "0", //
                "3", //
                "0", //
                "1", //
                "-3" //
        ), //
                5, 10 //
        );
    }

    private void testPuzzle1(List<String> inputData, int steps, int steps2) {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getSteps());
        assertEquals(steps2, event.getSteps2());
    }

}
