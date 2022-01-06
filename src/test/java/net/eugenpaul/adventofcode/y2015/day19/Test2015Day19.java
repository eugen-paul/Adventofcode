package net.eugenpaul.adventofcode.y2015.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day19
 */
class Test2015Day19 {

    @Test
    void testTest2015Day19() {
        testPuzzle(//
                List.of("H => HO", //
                        "H => OH", //
                        "O => HH", //
                        "", //
                        "HOH"//
                ), //
                4//
        );
        testPuzzle(//
                List.of("H => HO", //
                        "H => OH", //
                        "O => HH", //
                        "", //
                        "HOHOHO"//
                ), //
                7//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int distinctMolecules) {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(distinctMolecules, event.getDistinctMolecules());
    }

    @Test
    void testTest2015Day19Puzzle2() {
        testPuzzle2(//
                List.of("e => H", //
                        "e => O", //
                        "H => HO", //
                        "H => OH", //
                        "O => HH", //
                        "", //
                        "HOH"//
                ), //
                3//
        );

        testPuzzle2(//
                List.of("e => H", //
                        "e => O", //
                        "H => HO", //
                        "H => OH", //
                        "O => HH", //
                        "", //
                        "HOHOHO"//
                ), //
                6//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle2(List<String> inputData, int fewestNumberOfSteps) {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(fewestNumberOfSteps, event.getFewestNumberOfSteps());
    }

}