package net.eugenpaul.adventofcode.y2023.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2023Day14() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day14/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day14 event = new Day14();
        assertEquals(136, event.doPuzzle1(testFilePath));
        assertEquals(64, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(109661, event.getTotalScore());
        assertEquals(90176, event.getTotalScore2());
    }
}
