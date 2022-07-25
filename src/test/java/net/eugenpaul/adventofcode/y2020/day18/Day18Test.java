package net.eugenpaul.adventofcode.y2020.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2020Day18() {
        testPuzzle(List.of("1 + 2 * 3 + 4 * 5 + 6"), 71, 231);
        testPuzzle(List.of("1 + (2 * 3) + (4 * (5 + 6))"), 51, 51);
        testPuzzle(List.of("2 * 3 + (4 * 5)"), 26, 46);
        testPuzzle(List.of("5 + (8 * 3 + 9 + 3 * 4 * 3)"), 437, 1445);
        testPuzzle(List.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), 12240, 669060);
        testPuzzle(List.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), 13632, 23340);
    }

    private void testPuzzle(List<String> inputData, long sum, long sum2) {
        Day18 event = new Day18();

        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2020Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(45840336521334L, event.getSum());
        assertEquals(328920644404583L, event.getSum2());
    }

}
