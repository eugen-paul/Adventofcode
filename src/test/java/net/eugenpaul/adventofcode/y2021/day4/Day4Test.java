package net.eugenpaul.adventofcode.y2021.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2021Day4() {
        testPuzzle(List.of(//
                "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1", //
                "", //
                "22 13 17 11  0", //
                " 8  2 23  4 24", //
                "21  9 14 16  7", //
                " 6 10  3 18  5", //
                " 1 12 20 15 19", //
                "", //
                " 3 15  0  2 22", //
                " 9 18 13 17  5", //
                "19  8  7 25 23", //
                "20 11 10 24  4", //
                "14 21 16 12  6", //
                "", //
                "14 21 17 24  4", //
                "10 16 15  9 19", //
                "18  8 23 26 20", //
                "22 11 13  6  5", //
                " 2  0 12  3  7" //
        ), 4512, 1924);
    }

    private void testPuzzle(List<String> inputData, long winScore, int lastWinScore) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(winScore, event.getWinScore());
        assertEquals(lastWinScore, event.getLastWinScore());
    }

    @Test
    void testSolution2021Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(45031, event.getWinScore());
        assertEquals(2568, event.getLastWinScore());
    }

}
