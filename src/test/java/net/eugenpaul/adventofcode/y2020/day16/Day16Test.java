package net.eugenpaul.adventofcode.y2020.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2020Day16_puzzle1() {
        testPuzzle_1(List.of(//
                "class: 1-3 or 5-7", //
                "row: 6-11 or 33-44", //
                "seat: 13-40 or 45-50", //
                "", //
                "your ticket:", //
                "7,1,14", //
                "", //
                "nearby tickets:", //
                "7,3,47", //
                "40,4,50", //
                "55,2,20", //
                "38,6,12" //
        ), 71L);
    }

    private void testPuzzle_1(List<String> inputData, long errorRate) {
        Day16 event = new Day16();

        event.setDoPuzzle2(false);
        assertTrue(event.doEvent(inputData));
        assertEquals(errorRate, event.getErrorRate());
    }

    @Test
    void testSolution2020Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(29878, event.getErrorRate());
        assertEquals(855438643439L, event.getDepartures());
    }

}
