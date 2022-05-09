package net.eugenpaul.adventofcode.y2015.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

/**
 * Test2015Day22
 */
class Test2015Day22 {

    @Test
    void test2015Day22Example1() {
        Day22 puzzle = new Day22();

        puzzle.setPlayer(new Actor("Player", 10, 0, 0, 250));

        puzzle.doEvent(//
                List.of(//
                        "Hit Points: 13", //
                        "Damage: 8" //
                ));

        assertEquals(226, puzzle.getEasyGameLeastAmountOfMana());
    }

    @Test
    void test2015Day22Example2() {
        Day22 puzzle = new Day22();

        puzzle.setPlayer(new Actor("Player", 10, 0, 0, 250));

        puzzle.doEvent(//
                List.of(//
                        "Hit Points: 14", //
                        "Damage: 8" //
                ));

        assertEquals(641, puzzle.getEasyGameLeastAmountOfMana());
    }

    @Test
    void testSolution2015Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(953, event.getEasyGameLeastAmountOfMana());
        assertEquals(1289, event.getHardGameLeastAmountOfMana());
    }

}