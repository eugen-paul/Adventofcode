package net.eugenpaul.adventofcode.y2020.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2020Day22() {
        testPuzzle(List.of(//
                "Player 1:", //
                "9", //
                "2", //
                "6", //
                "3", //
                "1", //
                "", //
                "Player 2:", //
                "5", //
                "8", //
                "4", //
                "7", //
                "10" //
        ), 306, 291);
    }

    private void testPuzzle(List<String> inputData, long score, long score2) {
        Day22 event = new Day22();

        assertTrue(event.doEvent(inputData));
        assertEquals(score, event.getScore());
        assertEquals(score2, event.getScore2());
    }

    @Test
    void testSolution2020Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(32401, event.getScore());
        assertEquals(31436, event.getScore2());
    }

}
