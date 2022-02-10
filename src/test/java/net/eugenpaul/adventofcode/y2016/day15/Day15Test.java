package net.eugenpaul.adventofcode.y2016.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day15Test {

    @Test
    void testTest2016Day15_puzzle1() {
        testPuzzle1(List.of(//
                "Disc #1 has 5 positions; at time=0, it is at position 4.", //
                "Disc #2 has 2 positions; at time=0, it is at position 1." //
        ), //
                5 //
        );
    }

    private void testPuzzle1(List<String> inputData, int time) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(time, event.getTime());
    }

}
