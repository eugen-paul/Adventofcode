package net.eugenpaul.adventofcode.y2021.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2021Day5() {
        testPuzzle(List.of(//
                "0,9 -> 5,9", //
                "8,0 -> 0,8", //
                "9,4 -> 3,4", //
                "2,2 -> 2,1", //
                "7,0 -> 7,4", //
                "6,4 -> 2,0", //
                "0,9 -> 2,9", //
                "3,4 -> 1,4", //
                "0,0 -> 8,8", //
                "5,5 -> 8,2" //
        ), 5, 12);
    }

    private void testPuzzle(List<String> inputData, long overlaps, int overlaps2) {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(overlaps, event.getOverlaps());
        assertEquals(overlaps2, event.getOverlaps2());
    }

    @Test
    void testSolution2021Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(7436, event.getOverlaps());
        assertEquals(21104, event.getOverlaps2());
    }

}
