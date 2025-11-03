package net.eugenpaul.adventofcode.y2024.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2024Day7() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day7/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day7 event = new Day7();
        assertEquals(3749L, event.doPuzzle1(testFilePath));
        assertEquals(11387L, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(2299996598890L, event.getTotalScore());
        assertEquals(362646859298554L, event.getTotalScore2());
    }
}
