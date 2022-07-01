package net.eugenpaul.adventofcode.y2020.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2020Day1_testPuzzle() {
        testPuzzle(List.of(//
                "1721", //
                "979", //
                "366", //
                "299", //
                "675", //
                "1456" //
        ), 514579, 241861950);
    }

    private void testPuzzle(List<String> inputData, int product, int product2) {
        Day1 event = new Day1();

        assertTrue(event.doEvent(inputData));
        assertEquals(product, event.getProduct());
        assertEquals(product2, event.getProduct2());
    }

    @Test
    void testSolution2020Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(567171, event.getProduct());
        assertEquals(212428694, event.getProduct2());
    }

}
