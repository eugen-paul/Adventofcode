package net.eugenpaul.adventofcode.y2019.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testSolution2019Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(291, event.getBlockCount());
        assertEquals(14204, event.getScore());
    }

}
