package net.eugenpaul.adventofcode.y2020.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2020Day8_testPuzzle() {
        testPuzzle(List.of(//
                "nop +0", //
                "acc +1", //
                "jmp +4", //
                "acc +3", //
                "jmp -3", //
                "acc -99", //
                "acc +1", //
                "jmp -4", //
                "acc +6" //
        ), 5, 8);
    }

    private void testPuzzle(List<String> inputData, long acc, long acc2) {
        Day8 event = new Day8();

        assertTrue(event.doEvent(inputData));
        assertEquals(acc, event.getAcc());
        assertEquals(acc2, event.getAcc2());
    }

    @Test
    void testSolution2020Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2051, event.getAcc());
        assertEquals(2304, event.getAcc2());
    }

}
