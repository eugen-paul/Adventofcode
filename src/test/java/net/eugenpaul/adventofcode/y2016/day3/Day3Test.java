package net.eugenpaul.adventofcode.y2016.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day3Test {

    @Test
    void testTest2016Day3_puzzle_1() {
        testPuzzle1(List.of(//
                "5 10 25", //
                "5 10 11", //
                "5 15 19" //
        ), //
                2, //
                3 //
        );
    }

    private void testPuzzle1(List<String> inputData, int triangleCount, int triangleVerticallyCount) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(triangleCount, event.getTriangleCount());
        assertEquals(triangleVerticallyCount, event.getTriangleVerticallyCount());
    }

}
