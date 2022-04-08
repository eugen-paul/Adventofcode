package net.eugenpaul.adventofcode.y2017.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day25Test {

    @Test
    void testTest2017Day25_puzzle1() {
        testPuzzle1(List.of(//
                "Begin in state A.", //
                "Perform a diagnostic checksum after 6 steps.", //
                "", //
                "In state A:", //
                "  If the current value is 0:", //
                "    - Write the value 1.", //
                "    - Move one slot to the right.", //
                "    - Continue with state B.", //
                "  If the current value is 1:", //
                "    - Write the value 0.", //
                "    - Move one slot to the left.", //
                "    - Continue with state B.", //
                "", //
                "In state B:", //
                "  If the current value is 0:", //
                "    - Write the value 1.", //
                "    - Move one slot to the left.", //
                "    - Continue with state A.", //
                "  If the current value is 1:", //
                "    - Write the value 1.", //
                "    - Move one slot to the right.", //
                "    - Continue with state A." //
        ), 3);
    }

    private void testPuzzle1(List<String> inputData, long diagnosticChecksum) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(diagnosticChecksum, event.getDiagnosticChecksum());
    }

}
