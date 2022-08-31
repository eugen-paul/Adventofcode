package net.eugenpaul.adventofcode.y2021.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testSolution2021Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(29989297949519L, event.getLargestModelNumber());
        assertEquals(19518121316118L, event.getSmallestModelNumber());
    }

}
