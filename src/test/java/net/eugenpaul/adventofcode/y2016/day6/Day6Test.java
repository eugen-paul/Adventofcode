package net.eugenpaul.adventofcode.y2016.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day6Test {

    @Test
    void testTest2016Day6() {
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

}
