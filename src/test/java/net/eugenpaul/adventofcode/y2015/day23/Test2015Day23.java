package net.eugenpaul.adventofcode.y2015.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day23
 */
class Test2015Day23 {

    @Test
    void test2015Day23() {
        Day23 puzzle = new Day23();

        puzzle.doEvent(//
                List.of(//
                        "inc b", //
                        "jio b, +2", //
                        "tpl b", //
                        "inc b"//
                ));

        assertEquals(2, puzzle.getRegisterB());
    }

    @Test
    void testSolution2015Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(184, event.getRegisterB());
        assertEquals(231, event.getRegisterBPuzzle2());
    }

}