package net.eugenpaul.adventofcode.y2021.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2021Day25() {
        testPuzzle(List.of(//
                "v...>>.vv>", //
                ".vv>>.vv..", //
                ">>.>v>...v", //
                ">>v>>.>.v.", //
                "v>v.vv.v..", //
                ">.>>..v...", //
                ".vv..>.>v.", //
                "v.v..>>v.v", //
                "....v..v.>" //
        ), 58);
    }

    private void testPuzzle(List<String> inputData, long steps) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getSteps());
    }

    @Test
    void testSolution2021Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(414, event.getSteps());
    }

}
