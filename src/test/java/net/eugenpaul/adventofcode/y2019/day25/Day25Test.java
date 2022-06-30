package net.eugenpaul.adventofcode.y2019.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testSolution2019Day24() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("2147485856", event.getPassword());
    }

}
