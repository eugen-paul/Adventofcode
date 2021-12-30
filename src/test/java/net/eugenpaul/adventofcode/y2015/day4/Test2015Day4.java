package net.eugenpaul.adventofcode.y2015.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day4
 */
class Test2015Day4 {

    @Test
    void testTest2015Day4() throws NoSuchAlgorithmException {
        testPuzzle("abcdef", 609043, 6742839);
        testPuzzle("pqrstuv", 1048970, 5714438);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, long lowestNumber, long totalRibbon) throws NoSuchAlgorithmException {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);
        assertEquals(lowestNumber, event.getLowestFiveZeroNumber());
        // assertEquals(totalRibbon, event.getTotalRibbon());
    }

}