package net.eugenpaul.adventofcode.y2016.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2016Day12
 */
class Test2016Day12 {

    @Test
    void test2016Day12() {
        Day12 puzzle = new Day12();

        List.of(//
                puzzle.doPuzzleFromData(//
                        List.of(//
                                "cpy 41 a", //
                                "inc a", //
                                "inc a", //
                                "dec a", //
                                "jnz a 2", //
                                "dec a"//
                        )//
                )//
        );

        assertEquals(42, puzzle.getA());
    }

}