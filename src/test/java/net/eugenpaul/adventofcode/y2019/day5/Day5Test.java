package net.eugenpaul.adventofcode.y2019.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2019Day5_testPuzzle() {
        testPuzzle("3,0,4,0,99", 1);
    }

    private void testPuzzle(String inputData, int diagnosticCode) {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(diagnosticCode, event.getDiagnosticCode());
    }

    @Test
    void testSolution2019Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(16489636, event.getDiagnosticCode());
        assertEquals(9386583, event.getDiagnosticCode2());
    }

}
