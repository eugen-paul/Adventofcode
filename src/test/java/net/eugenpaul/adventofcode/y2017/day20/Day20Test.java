package net.eugenpaul.adventofcode.y2017.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day20Test {

    @Test
    void testTest2017Day20_puzzle1() {
        testPuzzle1(List.of(//
                "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>", //
                "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>" //
        ), 0);
    }

    private void testPuzzle1(List<String> inputData, int slowestNumber) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(slowestNumber, event.getSlowestNumber());
    }

    @Test
    void testTest2017Day20_puzzle2() {
        testPuzzle2(List.of(//
                "p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>", //
                "p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>", //
                "p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>", //
                "p=<3,0,0>, v=<-1,0,0>, a=<0,0,0>" //
        ), 1);
    }

    private void testPuzzle2(List<String> inputData, int counter) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(counter, event.getParticlesLeft());
    }

}
