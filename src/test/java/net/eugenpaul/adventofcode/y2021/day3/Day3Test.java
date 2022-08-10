package net.eugenpaul.adventofcode.y2021.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2021Day3() {
        testPuzzle(List.of(//
                "00100", //
                "11110", //
                "10110", //
                "10111", //
                "10101", //
                "01111", //
                "00111", //
                "11100", //
                "10000", //
                "11001", //
                "00010", //
                "01010" //
        ), 198, 230);
    }

    private void testPuzzle(List<String> inputData, long powerConsumption, int lifeSupportRating) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(powerConsumption, event.getPowerConsumption());
        assertEquals(lifeSupportRating, event.getLifeSupportRating());
    }

    @Test
    void testSolution2021Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3813416, event.getPowerConsumption());
        assertEquals(2990784, event.getLifeSupportRating());
    }

}
