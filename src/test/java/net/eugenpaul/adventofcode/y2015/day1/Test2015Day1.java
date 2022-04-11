package net.eugenpaul.adventofcode.y2015.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day1
 */
class Test2015Day1 {

    @Test
    void testTest2015Day1() {
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

    @Test
    void testSolution2015Day1(){
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(138, event.getOnFlor());
        assertEquals(1771, event.getFirstPositionOfbasement());
    }
}