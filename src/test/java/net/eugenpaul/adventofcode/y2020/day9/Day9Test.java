package net.eugenpaul.adventofcode.y2020.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2020Day9_testPuzzle() {
        testPuzzle(List.of(//
                "35", //
                "20", //
                "15", //
                "25", //
                "47", //
                "40", //
                "62", //
                "55", //
                "65", //
                "95", //
                "102", //
                "117", //
                "150", //
                "182", //
                "127", //
                "219", //
                "299", //
                "277", //
                "309", //
                "576" //
        ), 127, 62);
    }

    private void testPuzzle(List<String> inputData, long number, long number2) {
        Day9 event = new Day9();

        event.setPreamble(5);
        assertTrue(event.doEvent(inputData));
        assertEquals(number, event.getNumber());
        assertEquals(number2, event.getNumber2());
    }

    @Test
    void testSolution2020Day7() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(542529149L, event.getNumber());
        assertEquals(75678618L, event.getNumber2());
    }

}
