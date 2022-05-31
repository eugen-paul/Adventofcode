package net.eugenpaul.adventofcode.y2016.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2016Day16_puzzle1() {
        testPuzzle1(//
                "110010110100", //
                "100", //
                12 //
        );
        testPuzzle1(//
                "10000", //
                "01100", //
                20 //
        );
    }

    private void testPuzzle1(String inputData, String checksum, int length) {
        Day16 event = new Day16();

        event.setLength(length);
        event.setLength2(length);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(checksum, event.getChecksum());
    }

    @Test
    void testSolution2016Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("11100111011101111", event.getChecksum());
        assertEquals("10001110010000110", event.getChecksum2());
    }

}
