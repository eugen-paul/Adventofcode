package net.eugenpaul.adventofcode.y2018.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2018Day20_testPuzzle1() {
        testPuzzle1(//
                "^WNE$" //
                , 3 //
        );
        testPuzzle1(//
                "^ENWWW(NEEE|SSE(EE|N))$" //
                , 10 //
        );
        testPuzzle1(//
                "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$" //
                , 18 //
        );

    }

    private void testPuzzle1(String inputData, int register0) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(register0, event.getLargestNumberOfDoors());
    }

    @Test
    void testSolution2018Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4406, event.getLargestNumberOfDoors());
        assertEquals(8468, event.getLocationsAtLeast1000Doors());
    }

}
