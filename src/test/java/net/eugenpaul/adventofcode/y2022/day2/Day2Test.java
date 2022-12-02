package net.eugenpaul.adventofcode.y2022.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2022Day2() {
        testPuzzle(List.of(//
                "A Y", //
                "B X", //
                "C Z" //
        ), 15, 12);
    }

    private void testPuzzle(List<String> inputData, int totalScore, int totalScore2) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(totalScore, event.getTotalScore());
        assertEquals(totalScore2, event.getTotalScore2());
    }

    @Test
    void testSolution2022Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(10310, event.getTotalScore());
        assertEquals(14859, event.getTotalScore2());
    }

}
