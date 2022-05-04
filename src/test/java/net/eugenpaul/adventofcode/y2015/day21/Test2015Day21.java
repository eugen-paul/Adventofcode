package net.eugenpaul.adventofcode.y2015.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day21
 */
class Test2015Day21 {

    @Test
    void testSolution2015Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(78, event.getLeastAmountOfGold());
        assertEquals(148, event.getMostAmountOfGold());
    }

}