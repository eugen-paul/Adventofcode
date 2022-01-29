package net.eugenpaul.adventofcode.y2016.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day11Test {

    @Test
    void testTest2016Day11_puzzle1() {
        testPuzzle1(List.of(//
                "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.", //
                "The second floor contains a hydrogen generator.", //
                "The third floor contains a lithium generator.", //
                "The fourth floor contains nothing relevant." //
        ), //
                11 //
        );
    }

    private void testPuzzle1(List<String> inputData, int steps) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getMinSteps());
    }

    @Test
    void testTest2016Day11_puzzle1_test2() {
        testPuzzle1(List.of(//
                "The first floor contains a lithium generator and a lithium-compatible microchip.", //
                "The second floor contains nothing relevant.", //
                "The third floor contains nothing relevant.", //
                "The fourth floor contains nothing relevant." //
        ), //
                3 //
        );
    }

}
