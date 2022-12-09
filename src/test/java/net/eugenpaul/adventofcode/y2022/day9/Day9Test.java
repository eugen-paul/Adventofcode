package net.eugenpaul.adventofcode.y2022.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2022Day9_part1() {
        testPuzzle_1(List.of(//
                "R 4", //
                "U 4", //
                "L 3", //
                "D 1", //
                "R 4", //
                "D 1", //
                "L 5", //
                "R 2" //
        ), 13);
    }

    private void testPuzzle_1(List<String> inputData, int tailPositions) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(tailPositions, event.getTailPositions());
    }

    @Test
    void testTest2022Day9_part2() {
        testPuzzle_2(List.of(//
                "R 5", //
                "U 8", //
                "L 8", //
                "D 3", //
                "R 17", //
                "D 10", //
                "L 25", //
                "U 20" //
        ), 36);
    }

    private void testPuzzle_2(List<String> inputData, int tailPositions10) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(tailPositions10, event.getTailPositions10());
    }

    @Test
    void testSolution2022Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5874, event.getTailPositions());
        assertEquals(2467, event.getTailPositions10());
    }

}
