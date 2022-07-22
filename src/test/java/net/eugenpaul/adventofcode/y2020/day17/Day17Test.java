package net.eugenpaul.adventofcode.y2020.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testTest2020Day17() {
        testPuzzle_1(List.of(//
                ".#.", //
                "..#", //
                "###" //
        ), 112);
    }

    private void testPuzzle_1(List<String> inputData, long activeCubes) {
        Day17 event = new Day17();

        assertTrue(event.doEvent(inputData));
        assertEquals(activeCubes, event.getActiveCubes());
    }

    @Test
    void testSolution2020Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(215, event.getActiveCubes());
        assertEquals(1728, event.getActiveCubes2());
    }

}
