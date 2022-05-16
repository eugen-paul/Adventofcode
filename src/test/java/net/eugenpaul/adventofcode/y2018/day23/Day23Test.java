package net.eugenpaul.adventofcode.y2018.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2018Day23_testPuzzle1() {
        testPuzzle1(//
                List.of(//
                        "pos=<0,0,0>, r=4", //
                        "pos=<1,0,0>, r=1", //
                        "pos=<4,0,0>, r=3", //
                        "pos=<0,2,0>, r=1", //
                        "pos=<0,5,0>, r=3", //
                        "pos=<0,0,3>, r=1", //
                        "pos=<1,1,1>, r=1", //
                        "pos=<1,1,2>, r=1", //
                        "pos=<1,3,1>, r=1" //
                )//
                , 7 //
        );
    }

    private void testPuzzle1(List<String> inputData, int inRange) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(inRange, event.getNanobotsInRange());
    }

    @Test
    void testTest2018Day23_testPuzzle2() {
        testPuzzle2(//
                List.of(//
                        "pos=<10,12,12>, r=2", //
                        "pos=<12,14,12>, r=2", //
                        "pos=<16,12,12>, r=4", //
                        "pos=<14,14,14>, r=6", //
                        "pos=<50,50,50>, r=200", //
                        "pos=<10,10,10>, r=5" //
                )//
                , 6, 36 //
        );
    }

    private void testPuzzle2(List<String> inputData, int inRange, int manhattanDistance) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(inRange, event.getNanobotsInRange());
        assertEquals(manhattanDistance, event.getManhattanDistance());
    }

    @Test
    void testSolution2018Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(580, event.getNanobotsInRange());
        assertEquals(97816347, event.getManhattanDistance());
    }

}
