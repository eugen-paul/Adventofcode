package net.eugenpaul.adventofcode.y2020.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2020Day15() {
        testPuzzle("0,3,6", 436, 175594);
        testPuzzle("1,3,2", 1, 2578);
        testPuzzle("1,2,3", 27, 261214);
        testPuzzle("2,3,1", 78, 6895259);
        testPuzzle("3,2,1", 438, 18);
        testPuzzle("3,1,2", 1836, 362);
    }

    private void testPuzzle(String inputData, int numberAt2020, int numberAt30000000) {
        Day15 event = new Day15();

        assertTrue(event.doEvent(inputData));
        assertEquals(numberAt2020, event.getNumberAt2020());
        assertEquals(numberAt30000000, event.getNumberAt30000000());
    }

    @Test
    void testSolution2020Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(240, event.getNumberAt2020());
        assertEquals(505, event.getNumberAt30000000());
    }

}
