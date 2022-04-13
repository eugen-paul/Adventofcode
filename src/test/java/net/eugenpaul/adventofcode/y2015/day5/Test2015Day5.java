package net.eugenpaul.adventofcode.y2015.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day5
 */
class Test2015Day5 {

    @Test
    void testTest2015Day5() {
        testPuzzle1("ugknbfddgicrmopn", 1);
        testPuzzle1("aaa", 1);
        testPuzzle1("jchzalrnumimnmhp", 0);
        testPuzzle1("haegwjzuvuyypxyu", 0);
        testPuzzle1("dvszwmarrgswjxmb", 0);

        testPuzzle2("qjhvhtzxzqqjkmpb", 1);
        testPuzzle2("xxyxx", 1);
        testPuzzle2("aaa", 0);
        testPuzzle2("uurcxstgmygtbstg", 0);
        testPuzzle2("ieodomkazucvgmuy", 0);

        System.out.println("All tests OK.");
    }

    private void testPuzzle1(String inputData, long niceStringCount) {
        Day5 event = new Day5();

        assertTrue(event.doEvent(List.of(inputData)), "Testdata " + inputData);
        assertEquals(niceStringCount, event.getNiceStringCount());
    }

    private void testPuzzle2(String inputData, long niceStringCount) {
        Day5 event = new Day5();

        assertTrue(event.doEvent(List.of(inputData)), "Testdata " + inputData);
        assertEquals(niceStringCount, event.getAdvancedNiceStringCount());
    }

    @Test
    void testSolution2018Day3() {
        Day5 event = new Day5();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day5/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(236, event.getNiceStringCount());
        assertEquals(51, event.getAdvancedNiceStringCount());
    }

}