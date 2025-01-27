package net.eugenpaul.adventofcode.y2023.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testTest2023Day11() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day11/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day11 event = new Day11();
        assertEquals(374, event.doPuzzle1(testFilePath));
        event.setExp(10);
        assertEquals(1030, event.doPuzzle2(testFilePath));
        event.setExp(100);
        assertEquals(8410, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2023Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(9550717, event.getTotalScore());
        assertEquals(648458253817L, event.getTotalScore2());
    }
}
