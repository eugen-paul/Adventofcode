package net.eugenpaul.adventofcode.y2018.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2018Day16_testPuzzle1() {
        testPuzzle1(List.of(//
                "Before: [3, 2, 1, 1]", //
                "9 2 1 2", //
                "After:  [3, 2, 2, 1]" //
        ), 1);

    }

    private void testPuzzle1(List<String> inputData, int threeOrMoreOpcodes) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(threeOrMoreOpcodes, event.getThreeOrMoreOpcodes());
    }

    @Test
    void testSolution2018Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(588, event.getThreeOrMoreOpcodes());
        assertEquals(627, event.getRegister0());
    }

}
