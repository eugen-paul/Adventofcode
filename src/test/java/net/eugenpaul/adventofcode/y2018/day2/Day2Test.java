package net.eugenpaul.adventofcode.y2018.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2018Day2_puzzle1() {
        testPuzzle1(List.of(//
                "abcdef", //
                "bababc", //
                "abbcde", //
                "abcccd", //
                "aabcdd", //
                "abcdee", //
                "ababab" //
        ), 12);
    }

    private void testPuzzle1(List<String> inputData, long checksum) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(checksum, event.getChecksum());
    }

    @Test
    void testTest2018Day2_puzzle2() {
        testPuzzle2(List.of(//
                "abcde", //
                "fghij", //
                "klmno", //
                "pqrst", //
                "fguij", //
                "axcye", //
                "wvxyz"//
        ), "fgij");
    }

    private void testPuzzle2(List<String> inputData, String letters) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(letters, event.getLetters());
    }

    @Test
    void testSolution2018Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(6000, event.getChecksum());
        assertEquals("pbykrmjmizwhxlqnasfgtycdv", event.getLetters());
    }

}
