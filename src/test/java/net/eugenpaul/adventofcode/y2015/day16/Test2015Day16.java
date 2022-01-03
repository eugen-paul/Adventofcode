package net.eugenpaul.adventofcode.y2015.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day16
 */
class Test2015Day16 {

    @Test
    void testTest2015Day16() {
        testPuzzle(//
                List.of("Sue 1: goldfish: 9, cars: 0, samoyeds: 9", //
                        "Sue 2: perfumes: 5, trees: 8, goldfish: 8", //
                        "Sue 3: trees: 10, goldfish: 1, perfumes: 5"), //
                List.of(//
                        "children: 3", //
                        "cats: 7", //
                        "samoyeds: 2", //
                        "pomeranians: 3", //
                        "akitas: 0", //
                        "vizslas: 0", //
                        "goldfish: 8", //
                        "trees: 8", //
                        "cars: 2", //
                        "perfumes: 5"//
                ), //
                2, 3);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, List<String> inputMFCSAM, int sueNumber, int sueNumberRanges) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromData(inputData, inputMFCSAM));
        assertEquals(sueNumber, event.getSueNumber());
        assertEquals(sueNumberRanges, event.getSueNumberRanges());
    }

}