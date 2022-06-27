package net.eugenpaul.adventofcode.y2019.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testSolution2019Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(21897, event.getPuzzle1());
        assertEquals(16424, event.getPuzzle2());
    }

}
