package net.eugenpaul.adventofcode.y2015.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day7
 */
class Test2015Day7 {

    @Test
    void testTest2015Day7() {
        testPuzzle(List.of(//
                "0 -> a", //
                "123 -> x", //
                "456 -> y", //
                "x AND y -> d", //
                "x OR y -> e", //
                "x LSHIFT 2 -> f", //
                "y RSHIFT 2 -> g", //
                "NOT x -> h", //
                "NOT y -> i" //
        ), Map.of(//
                "a", 0, //
                "d", 72, //
                "e", 507, //
                "f", 492, //
                "g", 114, //
                "h", 65412, //
                "i", 65079, //
                "x", 123, //
                "y", 456//
        ));

        testPuzzle(List.of(//
                "0 -> a", //
                "123 -> x", //
                "x LSHIFT 2 -> f", //
                "x OR y -> e", //
                "NOT x -> h", //
                "456 -> y", //
                "y RSHIFT 2 -> g", //
                "x AND y -> d", //
                "NOT y -> i" //
        ), Map.of(//
                "a", 0, //
                "d", 72, //
                "e", 507, //
                "f", 492, //
                "g", 114, //
                "h", 65412, //
                "i", 65079, //
                "x", 123, //
                "y", 456//
        ));

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, Map<String, Integer> signals) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);
        for (Entry<String, Integer> signal : signals.entrySet()) {
            Short value = (short) signal.getValue().intValue();
            assertEquals(value, event.getSignal(signal.getKey()), "Error: " + signal.getKey());
        }
    }

    @Test
    void testSolution2015Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3176, event.getAPuzzle1());
        assertEquals(14710, event.getAPuzzle2());
    }

}