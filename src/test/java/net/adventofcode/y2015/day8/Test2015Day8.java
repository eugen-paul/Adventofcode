package net.adventofcode.y2015.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day8
 */
public class Test2015Day8 {

    @Test
    public void test() {
        testPuzzle("\"\"", 2, 0, 6);
        testPuzzle("\"abc\"", 5, 3, 9);
        testPuzzle("\"aaa\\\"aaa\"", 10, 7, 16);
        testPuzzle("\"\\x27\"", 6, 1, 11);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, int charactersOfCode, int charactersOfString, int charactersOfEscapedString) {
        Day8 event = new Day8();

        assertTrue(event.doPuzzleFromData(List.of(inputData)), "Testdata " + inputData);
        assertEquals(charactersOfCode, event.getCharactersOfCode());
        assertEquals(charactersOfString, event.getCharactersOfString());
        assertEquals(charactersOfEscapedString, event.getCharactersOfEscapedString());
    }

}