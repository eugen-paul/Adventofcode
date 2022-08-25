package net.eugenpaul.adventofcode.y2021.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2021Day17() {
        testPuzzle(List.of(//
                "target area: x=20..30, y=-10..-5" //
        ), 45, 112);
    }

    private void testPuzzle(List<String> inputData, long highestY, long count) {
        Day17 event = new Day17();

        assertTrue(event.doPuzzleFromData(inputData.get(0)));
        assertEquals(highestY, event.getHighestY());
        assertEquals(count, event.getCount());
    }

    @Test
    void testSolution2021Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(30628, event.getHighestY());
        assertEquals(4433, event.getCount());
    }

}
