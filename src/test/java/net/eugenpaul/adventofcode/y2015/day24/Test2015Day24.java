package net.eugenpaul.adventofcode.y2015.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day24
 */
class Test2015Day24 {

    @Test
    void test2015Day24GroupsOf3() {
        Day24 puzzle = new Day24();

        puzzle.doEvent(List.of(1, 2, 3, 4, 5, 7, 8, 9, 10, 11), 3);
        assertEquals(99, puzzle.getQuantumEntanglement());
    }

    @Test
    void test2015Day24GroupsOf4() {
        Day24 puzzle = new Day24();

        puzzle.doEvent(List.of(1, 2, 3, 4, 5, 7, 8, 9, 10, 11), 4);
        assertEquals(44, puzzle.getQuantumEntanglement());
    }

}