package net.eugenpaul.adventofcode.y2024.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2024Day14() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day14/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day14 event = new Day14();
        event.setRoomSizeX(11);
        event.setRoomSizeY(7);
        assertEquals(12, event.doPuzzle1(testFilePath));
        // assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(230461440, event.getTotalScore());
        event.getTotalScore2();
    }
}
