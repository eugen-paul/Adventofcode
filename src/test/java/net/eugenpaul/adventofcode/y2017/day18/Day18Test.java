package net.eugenpaul.adventofcode.y2017.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day18Test {

    @Test
    void testTest2017Day18_puzzle1() {
        testPuzzle1(List.of(//
                "set a 1", //
                "add a 2", //
                "mul a a", //
                "mod a 5", //
                "snd a", //
                "set a 0", //
                "rcv a", //
                "jgz a -1", //
                "set a 1", //
                "jgz a -2" //
        ), 4);
    }

    private void testPuzzle1(List<String> inputData, int value) {
        Day18 event = new Day18();
        event.setDoPuzzle2(false);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(value, event.getValue());
    }

    @Test
    void testTest2017Day18_puzzle2() {
        testPuzzle2(List.of(//
                "set a 1", //
                "set p 1", //
                "snd 1", //
                "snd 2", //
                "snd p", //
                "rcv a", //
                "rcv b", //
                "rcv c", //
                "rcv d" //
        ), 3);
    }

    private void testPuzzle2(List<String> inputData, int sendCounter) {
        Day18 event = new Day18();
        event.setDoPuzzle1(false);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sendCounter, event.getSendCounter());
    }
}
