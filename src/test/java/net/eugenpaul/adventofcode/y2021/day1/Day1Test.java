package net.eugenpaul.adventofcode.y2021.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2021Day1() {
        testPuzzle(List.of(//
                "199", //
                "200", //
                "208", //
                "210", //
                "200", //
                "207", //
                "240", //
                "269", //
                "260", //
                "263" //
        ), 7, 5);
    }

    private void testPuzzle(List<String> inputData, int increases, int increasesOf3) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(increases, event.getIncreases());
        assertEquals(increasesOf3, event.getIncreasesOf3());
    }

    @Test
    void testSolution2021Day1() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1502, event.getIncreases());
        assertEquals(1538, event.getIncreasesOf3());
    }

}
