package net.eugenpaul.adventofcode.y2022.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2022Day1() {
        testPuzzle(List.of(//
                "1000", //
                "2000", //
                "3000", //
                "", //
                "4000", //
                "", //
                "5000", //
                "6000", //
                "", //
                "7000", //
                "8000", //
                "9000", //
                "", //
                "10000" //
        ), 24000, 45000);
    }

    private void testPuzzle(List<String> inputData, int mostCalories, int top3Calories) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(mostCalories, event.getMostCalories());
        assertEquals(top3Calories, event.getTop3Calories());
    }

    @Test
    void testSolution2022Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(66487, event.getMostCalories());
        assertEquals(197301, event.getTop3Calories());
    }

}
