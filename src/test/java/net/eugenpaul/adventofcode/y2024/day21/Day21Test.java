package net.eugenpaul.adventofcode.y2024.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2024Day21() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day21/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day21 event = new Day21();
        assertEquals(126384, event.doPuzzle1(testFilePath));
        //assertEquals(0, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(212488L, event.getTotalScore());
        assertEquals(258263972600402L, event.getTotalScore2());
    }
}
