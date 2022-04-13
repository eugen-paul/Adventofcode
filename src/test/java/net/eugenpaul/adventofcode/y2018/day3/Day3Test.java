package net.eugenpaul.adventofcode.y2018.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2018Day3_puzzle1() {
        testPuzzle1(List.of(//
                "#1 @ 1,3: 4x4", //
                "#2 @ 3,1: 4x4", //
                "#3 @ 5,5: 2x2" //
        ), 4, 3);
    }

    private void testPuzzle1(List<String> inputData, long overlap, long intactClaim) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(overlap, event.getOverlap());
        assertEquals(intactClaim, event.getIntactClaim());
    }

    @Test
    void testSolution2018Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(107663, event.getOverlap());
        assertEquals(1166, event.getIntactClaim());
    }

}
