package net.eugenpaul.adventofcode.y2022.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2022Day8() {
        testPuzzle(List.of(//
                "30373", //
                "25512", //
                "65332", //
                "33549", //
                "35390" //
        ), 21, 8);
    }

    private void testPuzzle(List<String> inputData, int firstMarker, int firstMessage) {
        Day8 event = new Day8();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(firstMarker, event.getVisibleTrees());
        assertEquals(firstMessage, event.getHighestScenicScore());
    }

    @Test
    void testSolution2022Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1796, event.getVisibleTrees());
        assertEquals(288120, event.getHighestScenicScore());
    }

}
