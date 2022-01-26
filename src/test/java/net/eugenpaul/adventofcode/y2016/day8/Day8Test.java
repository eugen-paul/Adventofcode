package net.eugenpaul.adventofcode.y2016.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day8Test {

    @Test
    void testTest2016Day8_puzzle1() {
        testPuzzle1(List.of(//
                "rect 3x2", //
                "rotate column x=1 by 1", //
                "rotate row y=0 by 4", //
                "rotate column x=1 by 1" //
        ), //
                6 //
        );
    }

    private void testPuzzle1(List<String> inputData, long pixelsLit) {
        Day8 event = new Day8();

        assertTrue(event.doPuzzleFromData(inputData, 7, 3));
        assertEquals(pixelsLit, event.getPixelsLit());
    }

}
