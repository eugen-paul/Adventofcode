package net.eugenpaul.adventofcode.y2020.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2020Day14_puzzle1() {
        testPuzzle_1(List.of(//
                "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", //
                "mem[8] = 11", //
                "mem[7] = 101", //
                "mem[8] = 0" //
        ), 165L);
    }

    private void testPuzzle_1(List<String> inputData, long sum) {
        Day14 event = new Day14();

        event.setDoStep2(false);
        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
    }

    @Test
    void testTest2020Day14_puzzle2() {
        testPuzzle_2(List.of(//
                "mask = 000000000000000000000000000000X1001X", //
                "mem[42] = 100", //
                "mask = 00000000000000000000000000000000X0XX", //
                "mem[26] = 1" //
        ), 208L);
    }

    private void testPuzzle_2(List<String> inputData, long sum2) {
        Day14 event = new Day14();

        assertTrue(event.doEvent(inputData));
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2020Day13() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(14553106347726L, event.getSum());
        assertEquals(2737766154126L, event.getSum2());
    }

}
