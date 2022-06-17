package net.eugenpaul.adventofcode.y2019.day17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day17Test {

    @Test
    void testSolution2019Day17() {
        Day17 event = new Day17();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day17/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5068L, event.getSumOfAlignmentParameters());
        assertEquals(1415975, event.getDust());
    }

}
