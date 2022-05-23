package net.eugenpaul.adventofcode.y2019.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2019Day4_testPassword() {
        Day4 event = new Day4();
        assertTrue(event.isPasswordOk(new int[] { 1, 1, 1, 1, 1, 1 }));
        assertFalse(event.isPasswordOk(new int[] { 2, 2, 3, 4, 5, 0 }));
        assertFalse(event.isPasswordOk(new int[] { 1, 2, 3, 7, 8, 9 }));
    }

    @Test
    void testTest2019Day4_testPassword2() {
        Day4 event = new Day4();
        assertFalse(event.isPasswordOkExtra(new int[] { 1, 1, 1, 1, 1, 1 }));
        assertTrue(event.isPasswordOkExtra(new int[] { 1, 1, 2, 2, 3, 3 }));
        assertFalse(event.isPasswordOkExtra(new int[] { 1, 2, 3, 4, 4, 4 }));
        assertTrue(event.isPasswordOkExtra(new int[] { 1, 1, 1, 1, 2, 2 }));
    }

    @Test
    void testTest2019Day4_testPuzzle() {
        testPuzzle("111111-111111", 1);
        testPuzzle("111111-111112", 2);
    }

    private void testPuzzle(String inputData, int passwordsCount) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(passwordsCount, event.getDiffPasswords());
    }

    @Test
    void testSolution2019Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1610, event.getDiffPasswords());
        assertEquals(1104, event.getDiffPasswords2());
    }

}
