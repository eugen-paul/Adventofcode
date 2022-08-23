package net.eugenpaul.adventofcode.y2021.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testTest2021Day13() {
        testPuzzle(List.of(//
                "6,10", //
                "0,14", //
                "9,10", //
                "0,3", //
                "10,4", //
                "4,11", //
                "6,0", //
                "6,12", //
                "4,1", //
                "0,13", //
                "10,12", //
                "3,4", //
                "3,0", //
                "8,4", //
                "1,10", //
                "2,14", //
                "8,10", //
                "9,0", //
                "", //
                "fold along y=7", //
                "fold along x=5" //
        ), 17);
    }

    private void testPuzzle(List<String> inputData, long visible) {
        Day13 event = new Day13();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(visible, event.getVisibleAfterFirst());
    }

    @Test
    void testSolution2021Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(753, event.getVisibleAfterFirst());
        // Text: HZLEHJRK
    }

}
