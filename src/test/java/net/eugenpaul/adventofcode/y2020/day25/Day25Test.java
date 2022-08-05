package net.eugenpaul.adventofcode.y2020.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2020Day25() {
        testPuzzle(List.of(//
                "5764801", //
                "17807724" //
        ), 14897079);
    }

    private void testPuzzle(List<String> inputData, int key) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(key, event.getKey());
    }

    @Test
    void testSolution2020Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(42668, event.getKey());
    }

}
