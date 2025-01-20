package net.eugenpaul.adventofcode.y2023.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2023Day3() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day3/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day3 event = new Day3();
        assertEquals(4361, event.doPuzzle1(testFilePath));
        assertEquals(467835, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(528819, event.getTotalScore());
        assertEquals(80403602, event.getTotalScore2());
    }
}
