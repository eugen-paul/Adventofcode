package net.eugenpaul.adventofcode.y2019.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2019Day1_testPuzzle1() {
        testPuzzle1(List.of("12"), 2, 2);
        testPuzzle1(List.of("14"), 2, 2);
        testPuzzle1(List.of("1969"), 654, 966);
        testPuzzle1(List.of("100756"), 33583, 50346);
    }

    private void testPuzzle1(List<String> inputData, int sum, int sum2) {
        Day1 event = new Day1();

        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2019Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3336439, event.getSum());
        assertEquals(5001791, event.getSum2());
    }

}
