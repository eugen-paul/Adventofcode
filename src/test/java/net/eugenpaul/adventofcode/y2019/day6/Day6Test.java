package net.eugenpaul.adventofcode.y2019.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2019Day6_testPuzzle1() {
        testPuzzle1(List.of(//
                "COM)B", //
                "B)C", //
                "C)D", //
                "D)E", //
                "E)F", //
                "B)G", //
                "G)H", //
                "D)I", //
                "E)J", //
                "J)K", //
                "K)L" //
        ), 42);
    }

    private void testPuzzle1(List<String> inputData, int numberOfOrbits) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(numberOfOrbits, event.getNumberOfOrbits());
    }

    @Test
    void testTest2019Day6_testPuzzle2() {
        testPuzzle2(List.of(//
                "COM)B", //
                "B)C", //
                "C)D", //
                "D)E", //
                "E)F", //
                "B)G", //
                "G)H", //
                "D)I", //
                "E)J", //
                "J)K", //
                "K)L", //
                "K)YOU", //
                "I)SAN" //
        ), 4);
    }

    private void testPuzzle2(List<String> inputData, int youToSan) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(youToSan, event.getYouToSan());
    }

    @Test
    void testSolution2019Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(247089, event.getNumberOfOrbits());
        assertEquals(442, event.getYouToSan());
    }

}
