package net.eugenpaul.adventofcode.y2020.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2020Day5_testPuzzle() {
        testPuzzle(List.of(//
                "BFFFBBFRRR", //
                "FFFBBBFRRR", //
                "BBFFBBFRLL" //
        ), 820);
    }

    private void testPuzzle(List<String> inputData, long maxId) {
        Day5 event = new Day5();

        assertTrue(event.doEvent(inputData));
        assertEquals(maxId, event.getMaxId());
    }

    @Test
    void testSolution2020Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(978, event.getMaxId());
        assertEquals(727, event.getId());
    }

}
