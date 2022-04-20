package net.eugenpaul.adventofcode.y2015.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day12
 * 
 */
class Test2015Day12 {

    @Test
    void testTest2015Day12() {
        testPuzzle("[1,2,3]", 6, 6);
        testPuzzle("{\"a\":2,\"b\":4}", 6, 6);
        testPuzzle("[[[3]]]", 3, 3);
        testPuzzle("{\"a\":{\"b\":4},\"c\":-1}", 3, 3);
        testPuzzle("{\"a\":[-1,1]}", 0, 0);
        testPuzzle("[-1,{\"a\":1}]", 0, 0);
        testPuzzle("[]", 0, 0);
        testPuzzle("{}", 0, 0);
        testPuzzle("[1,{\"c\":\"red\",\"b\":2},3]", 6, 4);
        testPuzzle("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", 15, 0);
        testPuzzle("[1,\"red\",5]", 6, 6);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(String inputData, int sum, int sumWithoutRe) {
        Day12 event = new Day12();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(sumWithoutRe, event.getSumWithoutRed());
    }

    @Test
    void testSolution2015Day12() {
        Day12 event = new Day12();

        String eventData = FileReaderHelper.readStringFromFile("y2015/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(156366, event.getSum());
        assertEquals(96852, event.getSumWithoutRed());
    }

}