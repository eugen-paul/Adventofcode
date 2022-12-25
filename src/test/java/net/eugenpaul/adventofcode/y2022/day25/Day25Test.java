package net.eugenpaul.adventofcode.y2022.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day25Test {

    @Test
    void testTest2022Day25() {
        testPuzzle("y2022/day25/test_puzzle1.txt", "2=-1=0");
    }

    private void testPuzzle(String inputFile, String part1) {
        Day25 event = new Day25();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
    }

    @Test
    void testSolution2022Day25() {
        Day25 event = new Day25();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day25/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("2-121-=10=200==2==21", event.getPart1());
    }

}
