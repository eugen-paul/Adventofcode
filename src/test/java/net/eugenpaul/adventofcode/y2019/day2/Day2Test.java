package net.eugenpaul.adventofcode.y2019.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2019Day2_testPuzzle1() {
        testPuzzle1(List.of("1,9,10,3,2,3,11,0,99,30,40,50"), 3500);
        testPuzzle1(List.of("1,0,0,0,99"), 2);
        testPuzzle1(List.of("2,3,0,3,99"), 2);
        testPuzzle1(List.of("2,4,4,5,99,0"), 2);
        testPuzzle1(List.of("1,1,1,4,99,5,6,0,99"), 30);
    }

    private void testPuzzle1(List<String> inputData, int reg0) {
        Day2 event = new Day2();

        event.setDoReplace(false);
        event.setDoPuzzele2(false);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(reg0, event.getReg0());
    }

    @Test
    void testSolution2019Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(8017076, event.getReg0());
        assertEquals(3146, event.getNounVerb());
    }

}
