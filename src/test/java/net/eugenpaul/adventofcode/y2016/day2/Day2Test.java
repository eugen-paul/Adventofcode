package net.eugenpaul.adventofcode.y2016.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day2Test {

    @Test
    void testTest2016Day2_puzzle_1() {
        testPuzzle1(List.of(//
                "ULL", //
                "RRDDD", //
                "LURDL", //
                "UUUUD"//
        ), //
                "1985", //
                "5DB3"//
        );
    }

    private void testPuzzle1(List<String> inputData, String code, String codeExt) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(code, event.getCode());
        assertEquals(codeExt, event.getCodeExt());
    }

}
