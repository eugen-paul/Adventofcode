package net.eugenpaul.adventofcode.y2020.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2020Day23() {
        testPuzzle(List.of(//
                "389125467" //
        ), "67384529", 149245887792L);
    }

    private void testPuzzle(List<String> inputData, String label, long mult) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(label, event.getLabel());
        assertEquals(mult, event.getMultiply());
    }

    @Test
    void testSolution2020Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("49576328", event.getLabel());
        assertEquals(511780369955L, event.getMultiply());
    }

}
