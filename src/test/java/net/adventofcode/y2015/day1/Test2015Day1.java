package net.adventofcode.y2015.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day1
 */
public class Test2015Day1 {

    @Test
    public void test(){
        Day1 event = new Day1();
        String testData = "(()(()(";

        assertTrue(event.doPuzzleFromData(testData), "Test Nr. 1");
        assertEquals(3, event.getOnFlor());

        System.out.println("All tests OK.");
    }
}