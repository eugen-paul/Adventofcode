package net.eugenpaul.adventofcode.y2017.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day12Test {

    @Test
    void testTest2017Day12_puzzle1() {
        testPuzzle1(List.of(//
                "0 <-> 2", //
                "1 <-> 1", //
                "2 <-> 0, 3, 4", //
                "3 <-> 2, 4", //
                "4 <-> 2, 3, 6", //
                "5 <-> 6", //
                "6 <-> 4, 5"//
        ), 6, 2);
    }

    private void testPuzzle1(List<String> inputData, int programs0, int group) {
        Day12 event = new Day12();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(programs0, event.getPrograms0());
        assertEquals(group, event.getGroups());
    }
}
