package net.eugenpaul.adventofcode.y2022.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2022Day21() {
        testPuzzle("y2022/day21/test_puzzle1.txt", 152, 301);
    }

    private void testPuzzle(String inputFile, long part1, long part2) {
        Day21 event = new Day21();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(part1, event.getPart1());
        assertEquals(part2, event.getPart2());
    }

    @Test
    void testSolution2022Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(21120928600114L, event.getPart1());
        assertEquals(3453748220116L, event.getPart2());
    }

}
