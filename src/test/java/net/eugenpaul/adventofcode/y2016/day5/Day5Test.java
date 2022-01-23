package net.eugenpaul.adventofcode.y2016.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class Day5Test {

    @Test
    void testTest2016Day5_puzzle_1() throws NoSuchAlgorithmException {
        testPuzzle1("abc", //
                "18f47a30", //
                "05ace8e3" //
        );
    }

    private void testPuzzle1(String inputData, String password, String secondPassword ) throws NoSuchAlgorithmException {
        Day5 event = new Day5();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(password, event.getPassword());
        assertEquals(secondPassword, event.getSecondPassword());
    }

}
