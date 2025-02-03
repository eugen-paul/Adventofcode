package net.eugenpaul.adventofcode.y2023.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2023Day19() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day19/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day19 event = new Day19();
        assertEquals(19114, event.doPuzzle1(testFilePath));
        assertEquals(167_409_079_868_000L, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(331208, event.getTotalScore());
        assertEquals(121_464_316_215_623L, event.getTotalScore2());
    }
}
