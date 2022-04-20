package net.eugenpaul.adventofcode.y2018.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2018Day6_puzzle1() {
        testPuzzle1(List.of(//
                "1, 1", //
                "1, 6", //
                "8, 3", //
                "3, 4", //
                "5, 5", //
                "8, 9" //
        ), 17, 16);

    }

    private void testPuzzle1(List<String> inputData, long largestArea, long areaPuzzle2) {
        Day6 event = new Day6();

        event.setMaxDistance(32);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(largestArea, event.getLargestArea());
        assertEquals(areaPuzzle2, event.getAreaPuzzle2());
    }

    @Test
    void testSolution2018Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4171, event.getLargestArea());
        assertEquals(39545, event.getAreaPuzzle2());
    }

}
