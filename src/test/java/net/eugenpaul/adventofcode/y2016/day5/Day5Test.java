package net.eugenpaul.adventofcode.y2016.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day5Test {

    @Test
    void testTest2016Day5_testPuzzle() throws NoSuchAlgorithmException {
        testPuzzle("abc", //
                "18f47a30", //
                "05ace8e3" //
        );
    }

    private void testPuzzle(String inputData, String password, String secondPassword) throws NoSuchAlgorithmException {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(password, event.getPassword());
        assertEquals(secondPassword, event.getSecondPassword());
    }

    @Test
    void testSolution2016Day5() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("801b56a7", event.getPassword());
        assertEquals("424a0197", event.getSecondPassword());
    }

}
