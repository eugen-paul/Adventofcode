package net.eugenpaul.adventofcode.y2016.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day4Test {

    @Test
    void testTest2016Day4_puzzle_1() {
        testPuzzle1(List.of(//
                "aaaaa-bbb-z-y-x-123[abxyz]", //
                "a-b-c-d-e-f-g-h-987[abcde]", //
                "not-a-real-room-404[oarel]", //
                "totally-real-room-200[decoy]" //
        ), //
                1514 //
        );
    }

    private void testPuzzle1(List<String> inputData, int sumOfTheSectorIds) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sumOfTheSectorIds, event.getSumOfTheSectorIds());
    }

}
