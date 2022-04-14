package net.eugenpaul.adventofcode.y2015.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day8
 */
class Test2015Day8 {

    @Test
    void testTest2015Day8() {
        testPuzzle("\"\"", 2, 0, 6);
        testPuzzle("\"abc\"", 5, 3, 9);
        testPuzzle("\"aaa\\\"aaa\"", 10, 7, 16);
        testPuzzle("\"\\x27\"", 6, 1, 11);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, int charactersOfCode, int charactersOfString, int charactersOfEscapedString) {
        Day8 event = new Day8();

        assertTrue(event.doEvent(List.of(inputData)));

        assertEquals(charactersOfCode, event.getCharactersOfCode());
        assertEquals(charactersOfString, event.getCharactersOfString());
        assertEquals(charactersOfEscapedString, event.getCharactersOfEscapedString());
    }

    @Test
    void testSolution2015Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1371, event.getCharactersOfCode() - event.getCharactersOfString());
        assertEquals(2117, event.getCharactersOfEscapedString() - event.getCharactersOfCode());
    }

}