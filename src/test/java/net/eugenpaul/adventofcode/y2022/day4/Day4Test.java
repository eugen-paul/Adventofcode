package net.eugenpaul.adventofcode.y2022.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2022Day4() {
        testPuzzle(List.of(//
                "2-4,6-8", //
                "2-3,4-5", //
                "5-7,7-9", //
                "2-8,3-7", //
                "6-6,4-6", //
                "2-6,4-8" //
        ), 2, 4);
    }

    private void testPuzzle(List<String> inputData, int sumOfThePriorities, int overlapAtAll) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sumOfThePriorities, event.getFullyContains());
        assertEquals(overlapAtAll, event.getOverlapAtAll());
    }

    @Test
    void testSolution2022Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(534, event.getFullyContains());
        assertEquals(841, event.getOverlapAtAll());
    }

}
