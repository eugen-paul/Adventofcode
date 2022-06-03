package net.eugenpaul.adventofcode.y2019.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2019Day12_testPuzzle1_1() {
        testPuzzle1(List.of(//
                "<x=-1, y=0, z=2>", //
                "<x=2, y=-10, z=-7>", //
                "<x=4, y=-8, z=8>", //
                "<x=3, y=5, z=-1>" //
        ), 179, 10, 2772);
    }

    @Test
    void testTest2019Day12_testPuzzle1_2() {
        testPuzzle1(List.of(//
                "<x=-8, y=-10, z=0>", //
                "<x=5, y=5, z=10>", //
                "<x=2, y=-7, z=3>", //
                "<x=9, y=-8, z=-3>" //
        ), 1940, 100, 4686774924L);
    }

    private void testPuzzle1(List<String> inputData, int totalEnergy, int steps, long repeating) {
        Day12 event = new Day12();

        event.setSteps(steps);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(totalEnergy, event.getTotalEnergy());
        assertEquals(repeating, event.getRepeating());
    }

    @Test
    void testSolution2019Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(10028, event.getTotalEnergy());
        assertEquals(314610635824376L, event.getRepeating());
    }

}
