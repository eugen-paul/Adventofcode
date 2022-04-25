package net.eugenpaul.adventofcode.y2015.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day15
 */
class Test2015Day15 {

    @Test
    void testTest2015Day15() {
        testPuzzle(List.of(//
                "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8", //
                "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3" //
        ), 62842880, 57600000);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, long totalscore, long scoreWithCalorieLimit) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(totalscore, event.getMaxTotalscore());
        assertEquals(scoreWithCalorieLimit, event.getMaxTotalscoreWithCalorieLimit());
    }

    @Test
    void testSolution2018Day9() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(18965440, event.getMaxTotalscore());
        assertEquals(15862900, event.getMaxTotalscoreWithCalorieLimit());
    }

}