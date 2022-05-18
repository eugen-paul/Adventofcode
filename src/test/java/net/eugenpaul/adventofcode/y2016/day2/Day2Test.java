package net.eugenpaul.adventofcode.y2016.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2016Day2_puzzle() {
        testPuzzle(List.of(//
                "ULL", //
                "RRDDD", //
                "LURDL", //
                "UUUUD"//
        ), //
                "1985", //
                "5DB3"//
        );
    }

    private void testPuzzle(List<String> inputData, String code, String codeExt) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(code, event.getCode());
        assertEquals(codeExt, event.getCodeExt());
    }

    @Test
    void testSolution2019Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("84452", event.getCode());
        assertEquals("D65C3", event.getCodeExt());
    }

}
