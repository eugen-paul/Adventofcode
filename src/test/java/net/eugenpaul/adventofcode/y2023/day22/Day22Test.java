package net.eugenpaul.adventofcode.y2023.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2023Day22() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day22/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day22 event = new Day22();
        assertEquals(5, event.doPuzzle1(testFilePath));
        assertEquals(7, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(534, event.getTotalScore());
        assertEquals(88156, event.getTotalScore2());
    }
}
