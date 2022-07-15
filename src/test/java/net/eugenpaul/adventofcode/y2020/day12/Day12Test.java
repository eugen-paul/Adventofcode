package net.eugenpaul.adventofcode.y2020.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2020Day12_testPuzzle() {
        testPuzzle(List.of(//
                "F10", //
                "N3", //
                "F7", //
                "R90", //
                "F11" //
        ), 25, 286);
    }

    private void testPuzzle(List<String> inputData, int distance, int distance2) {
        Day12 event = new Day12();

        assertTrue(event.doEvent(inputData));
        assertEquals(distance, event.getDistance());
        assertEquals(distance2, event.getDistance2());
    }

    @Test
    void testSolution2020Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1482, event.getDistance());
        assertEquals(48739, event.getDistance2());
    }

}
