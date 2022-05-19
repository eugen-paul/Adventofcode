package net.eugenpaul.adventofcode.y2016.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2016Day4_testPuzzle1() {
        testPuzzle1(List.of(//
                "aaaaa-bbb-z-y-x-123[abxyz]", //
                "a-b-c-d-e-f-g-h-987[abcde]", //
                "not-a-real-room-404[oarel]", //
                "totally-real-room-200[decoy]" //
        ), //
                1514 //
        );
    }

    private void testPuzzle1(List<String> inputData, int sumOfTheSectorIds) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sumOfTheSectorIds, event.getSumOfTheSectorIds());
    }

    @Test
    void testSolution2016Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(361724, event.getSumOfTheSectorIds());
        assertEquals(482, event.getIdOfNorthPole());
    }

}
