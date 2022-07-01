package net.eugenpaul.adventofcode.y2020.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2020Day2_testPuzzle() {
        testPuzzle(List.of(//
                "1-3 a: abcde", //
                "1-3 b: cdefg", //
                "2-9 c: ccccccccc" //
        ), 2, 1);
    }

    private void testPuzzle(List<String> inputData, int okPassCount, int okPass2Count) {
        Day2 event = new Day2();

        assertTrue(event.doEvent(inputData));
        assertEquals(okPassCount, event.getOkPassCount());
        assertEquals(okPass2Count, event.getOkPass2Count());
    }

    @Test
    void testSolution2020Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(500, event.getOkPassCount());
        assertEquals(313, event.getOkPass2Count());
    }

}
