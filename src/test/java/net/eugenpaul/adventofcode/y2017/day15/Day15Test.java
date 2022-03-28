package net.eugenpaul.adventofcode.y2017.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day15Test {

    @Test
    void testTest2017Day15_puzzle1() {
        testPuzzle1(List.of(//
                "Generator A starts with 65", //
                "Generator B starts with 8921" //
        ), 588, 309);
    }

    private void testPuzzle1(List<String> inputData, int judge, int judge2) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(judge, event.getJudge());
        assertEquals(judge2, event.getJudge2());
    }
}
