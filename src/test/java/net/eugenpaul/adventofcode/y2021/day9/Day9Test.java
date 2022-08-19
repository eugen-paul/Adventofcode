package net.eugenpaul.adventofcode.y2021.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2021Day9() {
        testPuzzle(List.of(//
                "2199943210", //
                "3987894921", //
                "9856789892", //
                "8767896789", //
                "9899965678" //
        ), 15, 1134);
    }

    private void testPuzzle(List<String> inputData, long sum, long mult) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(mult, event.getMult());
    }

    @Test
    void testSolution2021Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(500, event.getSum());
        assertEquals(970200, event.getMult());
    }

}
