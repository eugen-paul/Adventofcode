package net.eugenpaul.adventofcode.y2016.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2016Day19_puzzle1() {
        testPuzzle1(//
                "5", //
                3, //
                2);
    }

    private void testPuzzle1(String inputData, int elfWithAllPresents, int elfWithAllPresents2) {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(elfWithAllPresents, event.getElfWithAllPresents());
        assertEquals(elfWithAllPresents2, event.getElfWithAllPresents2());
    }

    @Test
    void testTest2016Day19_test_slow() {
        Day19 event = new Day19();

        assertTrue(event.doPuzzleFromData("5"));
        assertTrue(event.doPuzzleFromData("10"));
        assertTrue(event.doPuzzleFromData("22"));
        assertTrue(event.doPuzzleFromData("50"));
        assertTrue(event.doPuzzleFromData("100"));
        assertTrue(event.doPuzzleFromData("101"));
        assertTrue(event.doPuzzleFromData("102"));
        assertTrue(event.doPuzzleFromData("103"));
        assertTrue(event.doPuzzleFromData("104"));
    }

    @Test
    void testSolution2016Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1834903, event.getElfWithAllPresents());
        assertEquals(1420280, event.getElfWithAllPresents2());
    }

}
