package net.eugenpaul.adventofcode.y2017.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day8Test {

    @Test
    void testTest2017Day8_puzzle1() {
        testPuzzle1(List.of(//
                "b inc 5 if a > 1", //
                "a inc 1 if b < 5", //
                "c dec -10 if a >= 1", //
                "c inc -20 if c == 10" //
        ), //
                1, //
                10 //
        );
    }

    private void testPuzzle1(List<String> inputData, int largestValue, int largestValueEver) {
        Day8 event = new Day8();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(largestValue, event.getLargestValue());
        assertEquals(largestValueEver, event.getLargestValueEver());
    }

}
