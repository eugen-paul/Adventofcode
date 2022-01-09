package net.eugenpaul.adventofcode.y2015.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

/**
 * Test2015Day22
 */
class Test2015Day22 {

    @Test
    void test2015Day22Example1() {
        Day22 puzzle = new Day22();

        puzzle.doEvent(//
                new Actor("Boss", 13, 8, 0, 0), //
                new Actor("Player", 10, 0, 0, 250)//
        );

        assertEquals(226, puzzle.getEasyGameLeastAmountOfMana());
    }

    @Test
    void test2015Day22Example2() {
        Day22 puzzle = new Day22();

        puzzle.doEvent(//
                new Actor("Boss", 14, 8, 0, 0), //
                new Actor("Player", 10, 0, 0, 250)//
        );

        assertEquals(641, puzzle.getEasyGameLeastAmountOfMana());
    }

}