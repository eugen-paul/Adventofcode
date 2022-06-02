package net.eugenpaul.adventofcode.y2016.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2016Day18_puzzle() {
        testPuzzle(//
                "..^^.", //
                6, //
                3 //
        );
        testPuzzle(//
                ".^^.^.^^^^", //
                38, //
                10 //
        );
    }

    private void testPuzzle(String inputData, int safeTiles, int rows) {
        Day18 event = new Day18();

        event.setRows(rows);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(safeTiles, event.getSafeTiles());
    }

    @Test
    void testSolution2016Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day18/puzzle1.txt");
        event.setRows(40);
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1926, event.getSafeTiles());

        event.setRows(400000);
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(19986699, event.getSafeTiles());
    }

}
