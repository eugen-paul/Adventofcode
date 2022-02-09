package net.eugenpaul.adventofcode.y2016.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test2016Day14
 */
class Test2016Day14 {

    @Test
    void test2016Day13() {
        Day14 puzzle = new Day14();

        puzzle.doPuzzleFromData("abc");

        assertEquals(22728, puzzle.getIndex());
        assertEquals(22551, puzzle.getIndex2());
    }

}