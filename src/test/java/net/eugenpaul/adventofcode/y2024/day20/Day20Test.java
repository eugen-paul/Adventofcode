package net.eugenpaul.adventofcode.y2024.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2024Day20() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2024/day20/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day20 event = new Day20();
        event.setMinSave(65);
        assertEquals(0, event.doPuzzle1(testFilePath));
        event.setMinSave(64);
        assertEquals(1, event.doPuzzle1(testFilePath));
        event.setMinSave(20);
        assertEquals(5, event.doPuzzle1(testFilePath));

        event.setMinSave(76);
        assertEquals(3, event.doPuzzle2(testFilePath));
        event.setMinSave(74);
        assertEquals(7, event.doPuzzle2(testFilePath));
        event.setMinSave(72);
        assertEquals(29, event.doPuzzle2(testFilePath));
        event.setMinSave(70);
        assertEquals(41, event.doPuzzle2(testFilePath));
        event.setMinSave(68);
        assertEquals(55, event.doPuzzle2(testFilePath));
        event.setMinSave(66);
        assertEquals(67, event.doPuzzle2(testFilePath));
        event.setMinSave(64);
        assertEquals(86, event.doPuzzle2(testFilePath));
        event.setMinSave(62);
        assertEquals(106, event.doPuzzle2(testFilePath));
        event.setMinSave(60);
        assertEquals(129, event.doPuzzle2(testFilePath));
        event.setMinSave(58);
        assertEquals(154, event.doPuzzle2(testFilePath));
        event.setMinSave(56);
        assertEquals(193, event.doPuzzle2(testFilePath));
        event.setMinSave(54);
        assertEquals(222, event.doPuzzle2(testFilePath));
        event.setMinSave(52);
        assertEquals(253, event.doPuzzle2(testFilePath));
        event.setMinSave(50);
        assertEquals(285, event.doPuzzle2(testFilePath));
    }

    @Test
    void testSolution2024Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1415, event.getTotalScore());

        assertEquals(1022577, event.getTotalScore2());
    }
}
