package net.eugenpaul.adventofcode.y2018.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.helper.SimplePos;

class Day11Test {

    @Test
    void testTest2018Day11_testGrid() {
        testGrid(57, new SimplePos(122, 79), -5L);
        testGrid(39, new SimplePos(217, 196), 0L);
        testGrid(71, new SimplePos(101, 153), 4L);
    }

    private void testGrid(int inputData, SimplePos pos, long powerLevel) {
        Day11 event = new Day11();

        event.computeGrid(inputData);
        assertEquals(powerLevel, event.getPowerLevel(pos));
    }

    @Test
    void testTest2018Day11_testPuzzle1() {
        testPuzzle1(List.of("18"), "33,45", "90,269,16");
        testPuzzle1(List.of("42"), "21,61", "232,251,12");
    }

    private void testPuzzle1(List<String> inputData, String bestPos, String bestPosEver) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(bestPos, event.getBestPos());
        assertEquals(bestPosEver, event.getBestPosEver());
    }

    @Test
    void testSolution2018Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("20,34", event.getBestPos());
        assertEquals("90,57,15", event.getBestPosEver());
    }

}
