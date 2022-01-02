package net.eugenpaul.adventofcode.y2015.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day15
 */
class Test2015Day15 {

    @Test
    void testTest2015Day15() {
        testPuzzle(List.of(//
                "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8", //
                "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3" //
        ), 62842880, 57600000, 500);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, long totalscore, long scoreWithCalorieLimit, int calorie) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData, calorie));
        assertEquals(totalscore, event.getMaxTotalscore());
        assertEquals(scoreWithCalorieLimit, event.getMaxTotalscoreWithCalorieLimit());
    }

}