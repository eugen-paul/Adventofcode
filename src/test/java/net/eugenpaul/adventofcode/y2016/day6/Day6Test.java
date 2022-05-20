package net.eugenpaul.adventofcode.y2016.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2016Day6_testPuzzle() {
        testPuzzle(List.of(//
                "eedadn", //
                "drvtee", //
                "eandsr", //
                "raavrd", //
                "atevrs", //
                "tsrnev", //
                "sdttsa", //
                "rasrtv", //
                "nssdts", //
                "ntnada", //
                "svetve", //
                "tesnvt", //
                "vntsnd", //
                "vrdear", //
                "dvrsen", //
                "enarar" //
        ), //
                "easter", //
                "advent" //
        );
    }

    private void testPuzzle(List<String> inputData, String errorCorrectedInput, String originalMessage) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(errorCorrectedInput, event.getErrorCorrectedInput());
        assertEquals(originalMessage, event.getOriginalMessage());
    }

    @Test
    void testSolution2016Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("asvcbhvg", event.getErrorCorrectedInput());
        assertEquals("odqnikqv", event.getOriginalMessage());
    }

}
