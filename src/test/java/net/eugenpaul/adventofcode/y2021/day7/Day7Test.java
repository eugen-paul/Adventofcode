package net.eugenpaul.adventofcode.y2021.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2021Day7() {
        testPuzzle(List.of(//
                "16,1,2,0,4,2,7,1,2,14" //
        ), 37, 168);
    }

    private void testPuzzle(List<String> inputData, long fuel, long fuel2) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData.get(0)));
        assertEquals(fuel, event.getFuel());
        assertEquals(fuel2, event.getFuel2());
    }

    @Test
    void testSolution2021Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(342730, event.getFuel());
        assertEquals(92335207, event.getFuel2());
    }

}
