package net.eugenpaul.adventofcode.y2017.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day21Test {

    @Test
    void testTest2017Day21_puzzle1() {
        testPuzzle1(List.of(//
                "../.# => ##./#../...", //
                ".#./..#/### => #..#/..../..../#..#" //
        ), 12);
    }

    private void testPuzzle1(List<String> inputData, int pixelsOn) {
        Day21 event = new Day21();

        event.setIterations(2);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(pixelsOn, event.getPixelsOn());
    }

}
