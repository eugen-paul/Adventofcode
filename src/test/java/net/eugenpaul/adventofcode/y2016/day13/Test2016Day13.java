package net.eugenpaul.adventofcode.y2016.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test2016Day13
 */
class Test2016Day13 {

    @Test
    void test2016Day13() {
        Day13 puzzle = new Day13();

        puzzle.doPuzzleFromData(10, 7, 4, 2);

        assertEquals(11, puzzle.getSteps());
        assertEquals(5, puzzle.getDistinctLocations());
    }

    @Test
    void test2016Day13_Puzzle2() {
        Day13 puzzle = new Day13();

        puzzle.doPuzzleFromData(10, 7, 4, 1);
        assertEquals(3, puzzle.getDistinctLocations());

        puzzle.doPuzzleFromData(10, 7, 4, 2);
        assertEquals(5, puzzle.getDistinctLocations());

        puzzle.doPuzzleFromData(10, 7, 4, 3);
        assertEquals(6, puzzle.getDistinctLocations());
    }

}