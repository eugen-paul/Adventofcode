package net.eugenpaul.adventofcode.y2016.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2016Day9_puzzle1() {
        testPuzzle1("ADVENT", 6);
        testPuzzle1("ADVENT ADVENT", 12);
        testPuzzle1("A(1x5)BC", 7);
        testPuzzle1("(3x3)XYZ", 9);
        testPuzzle1("A(2x2)BCD(2x2)EFG", 11);
        testPuzzle1("(6x1)(1x3)A", 6);
        testPuzzle1("X(8x2)(3x3)ABCY", 18);
        testPuzzle1("X(8x2)(3x3)ABCY X(8x2)(3x3)ABCY", 36);
    }

    private void testPuzzle1(String inputData, long textLength) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(textLength, event.getTextLength());
    }

    @Test
    void testTest2016Day9_puzzle2() {
        testPuzzle2("(3x3)XYZ", 9);
        testPuzzle2("X(8x2)(3x3)ABCY", "XABCABCABCABCABCABCY".length());
        testPuzzle2("(27x12)(20x12)(13x14)(7x10)(1x12)A", 241920);
        testPuzzle2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN", 445);
    }

    private void testPuzzle2(String inputData, long textLengthV2) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(textLengthV2, event.getTextLengthV2());
    }

    @Test
    void testSolution2019Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(99145L, event.getTextLength());
        assertEquals(10943094568L, event.getTextLengthV2());
    }
}
