package net.eugenpaul.adventofcode.y2019.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testSolution2019Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(19359533, event.getPuzzle1());
        assertEquals(1140310551, event.getPuzzle2());
    }

}
