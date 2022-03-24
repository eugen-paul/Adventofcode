package net.eugenpaul.adventofcode.y2017.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day13Test {

    @Test
    void testTest2017Day13_puzzle1() {
        testPuzzle1(List.of(//
                "0: 3", //
                "1: 2", //
                "4: 4", //
                "6: 4" //
        ), 24, 10);
    }

    private void testPuzzle1(List<String> inputData, int severity, int delay) {
        Day13 event = new Day13();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(severity, event.getSeverity());
        assertEquals(delay, event.getDelay());
    }
}
