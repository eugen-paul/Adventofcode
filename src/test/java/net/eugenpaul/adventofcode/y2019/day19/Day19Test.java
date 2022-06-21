package net.eugenpaul.adventofcode.y2019.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testSolution2019Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(213, event.getPulled());
        assertEquals(7830987, event.getCoordinate2());
    }

}
