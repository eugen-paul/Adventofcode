package net.eugenpaul.adventofcode.y2016.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2016Day20_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "5-8", //
                        "0-2", //
                        "4-7"//
                ), //
                3L, //
                8L //
        );

        testPuzzle1(//
                List.of(//
                        "0-5", //
                        "4-8", //
                        "10-12"//
                ), //
                9L, //
                4L //
        );

        testPuzzle1(//
                List.of(//
                        "1-5", //
                        "4-8", //
                        "10-12"//
                ), //
                0, //
                5L //
        );

        testPuzzle1(//
                List.of(//
                        "0-5", //
                        "2-4", //
                        "10-15"//
                ), //
                6L, //
                4L //
        );

        testPuzzle1(//
                List.of(//
                        "0-5", //
                        "4-10", //
                        "8-12"//
                ), //
                13L, //
                3L //
        );

        testPuzzle1(//
                List.of(//
                        "0-0", //
                        "4-10", //
                        "10-15"//
                ), //
                1L, //
                3L //
        );
    }

    private void testPuzzle1(List<String> inputData, long lowestIp, long allowedIpsCount) {
        Day20 event = new Day20();

        event.setMin(0);
        event.setMax(15);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(lowestIp, event.getLowestIp());
        assertEquals(allowedIpsCount, event.getIpCount());
    }

    @Test
    void testSolution2016Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(14975795, event.getLowestIp());
        assertEquals(101, event.getIpCount());
    }

}
