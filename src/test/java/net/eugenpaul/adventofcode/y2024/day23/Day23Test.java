package net.eugenpaul.adventofcode.y2024.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2024Day23() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day23/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day23 event = new Day23();
        assertEquals(7, event.doPuzzle1(testFilePath));
        assertEquals("co,de,ka,ta", event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1075, event.getTotalScore());
        // assertEquals(0, event.getTotalScore2());
    }
}
