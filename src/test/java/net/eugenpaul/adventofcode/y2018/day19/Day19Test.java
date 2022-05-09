package net.eugenpaul.adventofcode.y2018.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2018Day19_testPuzzle1() {
        testPuzzle1(List.of(//
                "#ip 0", //
                "seti 5 0 1", //
                "seti 6 0 2", //
                "addi 0 1 0", //
                "addr 1 2 3", //
                "setr 1 0 0", //
                "seti 8 0 4", //
                "seti 9 0 5" //
        ), 7);

    }

    private void testPuzzle1(List<String> inputData, int register0) {
        Day19 event = new Day19();
        event.setDoPuzzle2(false);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(register0, event.getRegister0a());
    }

    @Test
    void testSolution2018Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(960, event.getRegister0a());
        assertEquals(10750428, event.getRegister0b());
    }

}
