package net.eugenpaul.adventofcode.y2015.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day23
 */
class Test2015Day23 {

    @Test
    void test2015Day23() {
        Day23 puzzle = new Day23();

        puzzle.doEvent(//
                List.of(//
                        "inc a", //
                        "jio a, +2", //
                        "tpl a", //
                        "inc a"//
                ), 0);

        assertEquals(2, puzzle.getRegisterA());
    }

}