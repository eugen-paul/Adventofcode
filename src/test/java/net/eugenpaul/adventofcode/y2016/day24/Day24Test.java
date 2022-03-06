package net.eugenpaul.adventofcode.y2016.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day24Test {

    @Test
    void testTest2016Day24_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "###########", //
                        "#0.1.....2#", //
                        "#.#######.#", //
                        "#4.......3#", //
                        "###########" //
                ), //
                14 //
        );
    }

    private void testPuzzle1(List<String> inputData, int shortestRoute) {
        Day24 event = new Day24();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(shortestRoute, event.getShortestRoute());
    }

}
