package net.eugenpaul.adventofcode.y2016.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2016Day3_testPuzzle() {
        testPuzzle(List.of(//
                "5 10 25", //
                "5 10 11", //
                "5 15 19" //
        ), //
                2, //
                3 //
        );
    }

    private void testPuzzle(List<String> inputData, int triangleCount, int triangleVerticallyCount) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(triangleCount, event.getTriangleCount());
        assertEquals(triangleVerticallyCount, event.getTriangleVerticallyCount());
    }

    @Test
    void testSolution2016Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(983, event.getTriangleCount());
        assertEquals(1836, event.getTriangleVerticallyCount());
    }

}
