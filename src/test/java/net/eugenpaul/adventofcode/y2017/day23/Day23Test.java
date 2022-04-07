package net.eugenpaul.adventofcode.y2017.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day23Test {

    @Test
    void testTest2017Day23_puzzle1() {
        testPuzzle1(List.of(//
                "set b 99", //
                "set c b", //
                "mul b 100", //
                "mul a 100", //
                "sub b -100000", //
                "set c b"//
        ), 2);
    }

    private void testPuzzle1(List<String> inputData, long mulInvoked) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(mulInvoked, event.getMulInvoked());
    }

}
