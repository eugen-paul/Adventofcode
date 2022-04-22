package net.eugenpaul.adventofcode.y2018.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2018Day8_puzzle1() {
        testPuzzle1(List.of(//
                "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2" //
        ), 138, 66);

    }

    private void testPuzzle1(List<String> inputData, int metadataSum, int rootValue) {
        Day8 event = new Day8();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(metadataSum, event.getMetadataSum());
        assertEquals(rootValue, event.getRootValue());
    }

    @Test
    void testSolution2018Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(44838, event.getMetadataSum());
        assertEquals(22198, event.getRootValue());
    }

}
