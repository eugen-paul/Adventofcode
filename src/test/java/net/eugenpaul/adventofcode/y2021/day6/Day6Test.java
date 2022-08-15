package net.eugenpaul.adventofcode.y2021.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2021Day6() {
        testPuzzle(List.of(//
                "3,4,3,1,2" //
        ), 5934L, 26984457539L);
    }

    private void testPuzzle(List<String> inputData, long fish80, long fish256) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData.get(0)));
        assertEquals(fish80, event.getFish80());
        assertEquals(fish256, event.getFish256());
    }

    @Test
    void testSolution2021Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(354564, event.getFish80());
        assertEquals(1609058859115L, event.getFish256());
    }

}
