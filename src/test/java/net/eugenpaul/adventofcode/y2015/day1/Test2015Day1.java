package net.eugenpaul.adventofcode.y2015.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day1
 */
public class Test2015Day1 {

    @Test
    public void testTest2015Day1() {
        assertEquals(0, testPuzzle1("(())"));
        assertEquals(0, testPuzzle1("()()"));
        assertEquals(3, testPuzzle1("((("));
        assertEquals(3, testPuzzle1("(()(()("));
        assertEquals(3, testPuzzle1("))((((("));
        assertEquals(-1, testPuzzle1("())"));
        assertEquals(-1, testPuzzle1("))("));
        assertEquals(-3, testPuzzle1(")))"));
        assertEquals(-3, testPuzzle1(")())())"));

        assertEquals(1, testPuzzle2(")"));
        assertEquals(5, testPuzzle2("()())"));

        System.out.println("All tests OK.");
    }

    private int testPuzzle1(String inputData) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);

        return event.getOnFlor();
    }

    private int testPuzzle2(String inputData) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData), "Testdata " + inputData);

        return event.getFirstPositionOfbasement();
    }
}