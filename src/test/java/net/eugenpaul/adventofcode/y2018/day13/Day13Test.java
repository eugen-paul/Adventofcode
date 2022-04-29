package net.eugenpaul.adventofcode.y2018.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.helper.SimplePos;

class Day13Test {

    @Test
    void testTest2018Day13_testPuzzle1() {
        testPuzzle1(List.of(//
                "/->-\\        ", //
                "|   |  /----\\", //
                "| /-+--+-\\  |", //
                "| | |  | v  |", //
                "\\-+-/  \\-+--/", //
                "  \\------/   " //
        ), //
                new SimplePos(7, 3));
    }

    private void testPuzzle1(List<String> inputData, SimplePos firstCrash) {
        Day13 event = new Day13();

        assertEquals(firstCrash, event.doPuzzle1(inputData));
    }

    @Test
    void testTest2018Day13_testPuzzle2() {
        testPuzzle2(List.of(//
                "/>-<\\  ", //
                "|   |  ", //
                "| /<+-\\", //
                "| | | v", //
                "\\>+</ |", //
                "  |   ^", //
                "  \\<->/" //
        ), //
                new SimplePos(6, 4));
    }

    private void testPuzzle2(List<String> inputData, SimplePos firstCrash) {
        Day13 event = new Day13();

        assertEquals(firstCrash, event.doPuzzle2(inputData));
    }

    @Test
    void testSolution2018Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(new SimplePos(124, 90), event.getFirstCrash());
        assertEquals(new SimplePos(145, 88), event.getLastCarPos());
    }

}
