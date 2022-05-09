package net.eugenpaul.adventofcode.y2018.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2018Day18_testPuzzle1() {
        testPuzzle1(List.of(//
                ".#.#...|#.", //
                ".....#|##|", //
                ".|..|...#.", //
                "..|#.....#", //
                "#.#|||#|#|", //
                "...#.||...", //
                ".|....|...", //
                "||...#|.#|", //
                "|.||||..|.", //
                "...#.|..|." //
        ), 1147);

    }

    private void testPuzzle1(List<String> inputData, int resourceValue) {
        Day18 event = new Day18();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(resourceValue, event.getResourceValue());
    }

    @Test
    void testSolution2018Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(539682, event.getResourceValue());
        assertEquals(226450, event.getResourceValueAfter1b());
    }

}
