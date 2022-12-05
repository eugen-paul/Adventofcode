package net.eugenpaul.adventofcode.y2022.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2022Day5() {
        testPuzzle(List.of(//
        "    [D]    ", //
        "[N] [C]    ", //
        "[Z] [M] [P]", //
        " 1   2   3 ", //
        "", //
        "move 1 from 2 to 1", //
        "move 3 from 1 to 3", //
        "move 2 from 2 to 1", //
        "move 1 from 1 to 2" //
        ), "CMZ", "MCD");
    }

    private void testPuzzle(List<String> inputData, String crateEnds, String crateEnds2) {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(crateEnds, event.getCrateEnds());
        assertEquals(crateEnds2, event.getCrateEnds2());
    }

    @Test
    void testSolution2022Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("TWSGQHNHL", event.getCrateEnds());
        assertEquals("JNRSCDWPP", event.getCrateEnds2());
    }

}
