package net.eugenpaul.adventofcode.y2016.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2016Day23_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "cpy 2 a", //
                        "tgl a", //
                        "tgl a", //
                        "tgl a", //
                        "cpy 1 a", //
                        "dec a", //
                        "dec a" //
                ), //
                3 //
        );
    }

    private void testPuzzle1(List<String> inputData, int registerA) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(registerA, event.getRegisterA());
    }

    @Test
    void testSolution2016Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(11026, event.getRegisterA());
        assertEquals(479007586, event.getRegisterA2());
    }

}
