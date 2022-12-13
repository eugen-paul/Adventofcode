package net.eugenpaul.adventofcode.y2022.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day13Test {

    @Test
    void testTest2022Day13() {
        testPuzzle("y2022/day13/test_puzzle1.txt", 13, 140);
    }

    private void testPuzzle(String inputFile, long stepsPart1, long stepsPart2) {
        Day13 event = new Day13();

        assertTrue(event.doPuzzleFromFile(inputFile));
        assertEquals(stepsPart1, event.getInTheRightOrder());
        assertEquals(stepsPart2, event.getDecoderKey());
    }

    @Test
    void testSolution2022Day13() {
        Day13 event = new Day13();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day13/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5208, event.getInTheRightOrder());
        assertEquals(25792, event.getDecoderKey());
    }

}
