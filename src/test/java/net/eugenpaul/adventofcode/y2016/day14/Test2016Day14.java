package net.eugenpaul.adventofcode.y2016.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Test2016Day14 {

    @Test
    void test2016Day13_testPuzzle() {
        Day14 puzzle = new Day14();

        puzzle.doPuzzleFromData("abc");

        assertEquals(22728, puzzle.getIndex());
        assertEquals(22551, puzzle.getIndex2());
    }

    @Test
    void testSolution2016Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(15035, event.getIndex());
        assertEquals(19968, event.getIndex2());
    }

}