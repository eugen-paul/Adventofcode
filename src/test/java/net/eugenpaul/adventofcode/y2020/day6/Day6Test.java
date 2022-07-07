package net.eugenpaul.adventofcode.y2020.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2020Day6_testPuzzle() {
        testPuzzle(List.of(//
                "abc", //
                "", //
                "a", //
                "b", //
                "c", //
                "", //
                "ab", //
                "ac", //
                "", //
                "a", //
                "a", //
                "a", //
                "a", //
                "", //
                "b" //
        ), 11, 6);
    }

    private void testPuzzle(List<String> inputData, long sum, long sum2) {
        Day6 event = new Day6();

        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2020Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(6930, event.getSum());
        assertEquals(3585, event.getSum2());
    }

}
