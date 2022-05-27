package net.eugenpaul.adventofcode.y2016.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Test2016Day13 {

    @Test
    void test2016Day13_testPuzzle1() {
        Day13 puzzle = new Day13();

        puzzle.setX(7);
        puzzle.setY(4);
        puzzle.setMaxSteps(2);

        puzzle.doEvent(10);

        assertEquals(11, puzzle.getSteps());
        assertEquals(5, puzzle.getDistinctLocations());
    }

    @Test
    void test2016Day13_Puzzle2() {
        Day13 puzzle = new Day13();

        puzzle.setX(7);
        puzzle.setY(4);
        puzzle.setMaxSteps(1);

        puzzle.doEvent(10);
        assertEquals(3, puzzle.getDistinctLocations());

        puzzle = new Day13();
        puzzle.setX(7);
        puzzle.setY(4);
        puzzle.setMaxSteps(2);

        puzzle.doEvent(10);
        assertEquals(5, puzzle.getDistinctLocations());

        puzzle = new Day13();
        puzzle.setX(7);
        puzzle.setY(4);
        puzzle.setMaxSteps(3);

        puzzle.doEvent(10);
        assertEquals(6, puzzle.getDistinctLocations());
    }

    @Test
    void testSolution2016Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(92, event.getSteps());
        assertEquals(124, event.getDistinctLocations());
    }

}