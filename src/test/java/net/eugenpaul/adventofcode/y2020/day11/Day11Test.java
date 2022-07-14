package net.eugenpaul.adventofcode.y2020.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2020Day11_testPuzzle() {
        testPuzzle(List.of(//
                "L.LL.LL.LL", //
                "LLLLLLL.LL", //
                "L.L.L..L..", //
                "LLLL.LL.LL", //
                "L.LL.LL.LL", //
                "L.LLLLL.LL", //
                "..L.L.....", //
                "LLLLLLLLLL", //
                "L.LLLLLL.L", //
                "L.LLLLL.LL" //
        ), 37);
    }

    private void testPuzzle(List<String> inputData, int occupiedSeats) {
        Day11 event = new Day11();

        assertTrue(event.doEvent(inputData));
        assertEquals(occupiedSeats, event.getOccupiedSeats());
    }

    @Test
    void testSolution2020Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2261, event.getOccupiedSeats());
        assertEquals(2039, event.getOccupiedSeats2());
    }

}
