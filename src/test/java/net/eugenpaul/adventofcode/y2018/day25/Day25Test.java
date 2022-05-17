package net.eugenpaul.adventofcode.y2018.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2018Day25_testPuzzle1() {
        testPuzzle1(//
                List.of(//
                        "0,0,0,0", //
                        "3,0,0,0", //
                        "0,3,0,0", //
                        "0,0,3,0", //
                        "0,0,0,3", //
                        "0,0,0,6", //
                        "9,0,0,0", //
                        "12,0,0,0" //
                )//
                , 2//
        );

        testPuzzle1(//
                List.of(//
                        "-1,2,2,0", //
                        "0,0,2,-2", //
                        "0,0,0,-2", //
                        "-1,2,0,0", //
                        "-2,-2,-2,2", //
                        "3,0,2,-1", //
                        "-1,3,2,2", //
                        "-1,0,-1,0", //
                        "0,2,1,-2", //
                        "3,0,0,0" //
                )//
                , 4//
        );

        testPuzzle1(//
                List.of(//
                        "1,-1,0,1", //
                        "2,0,-1,0", //
                        "3,2,-1,0", //
                        "0,0,3,1", //
                        "0,0,-1,-1", //
                        "2,3,-2,0", //
                        "-2,2,0,0", //
                        "2,-2,0,-1", //
                        "1,-1,0,-1", //
                        "3,2,0,2" //
                )//
                , 3//
        );

        testPuzzle1(//
                List.of(//
                        "1,-1,-1,-2", //
                        "-2,-2,0,1", //
                        "0,2,1,3", //
                        "-2,3,-2,1", //
                        "0,2,3,-2", //
                        "-1,-1,1,-2", //
                        "0,-2,-1,0", //
                        "-2,2,3,-1", //
                        "1,2,2,0", //
                        "-1,-2,0,-2" //
                )//
                , 8//
        );
    }

    private void testPuzzle1(List<String> inputData, int constellations) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(constellations, event.getConstellations());
    }

    @Test
    void testSolution2018Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(428, event.getConstellations());
    }

}
