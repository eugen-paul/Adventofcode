package net.eugenpaul.adventofcode.y2023.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2023Day7() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day7/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day7 event = new Day7();
        assertEquals(6440, event.doPuzzle1(testFilePath));
        assertEquals(5905, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(248453531, event.getTotalScore());
        assertEquals(248781813, event.getTotalScore2());
    }
}
