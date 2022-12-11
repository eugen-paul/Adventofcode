package net.eugenpaul.adventofcode.y2022.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2022Day11() {
        testPuzzle("y2022/day11/test_puzzle1.txt", 10605, 2713310158L);
    }

    private void testPuzzle(String inputFile, long monkeyBusiness, long monkeyBusiness10000) {
        Day11 event = new Day11();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(monkeyBusiness, event.getMonkeyBusiness());
        assertEquals(monkeyBusiness10000, event.getMonkeyBusiness10000());
    }

    @Test
    void testSolution2022Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(110220L, event.getMonkeyBusiness());
        assertEquals(19457438264L, event.getMonkeyBusiness10000());
    }

}
