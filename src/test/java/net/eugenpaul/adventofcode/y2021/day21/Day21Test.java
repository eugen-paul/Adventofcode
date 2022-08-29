package net.eugenpaul.adventofcode.y2021.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2021Day21() {
        testPuzzle(List.of(//
                "Player 1 starting position: 4", //
                "Player 2 starting position: 8" //
        ), 739785L, 444356092776315L);
    }

    private void testPuzzle(List<String> inputData, long score, long wins) {
        Day21 event = new Day21();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(score, event.getScore());
        assertEquals(wins, event.getWins());
    }

    @Test
    void testSolution2021Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(711480L, event.getScore());
        assertEquals(265845890886828L, event.getWins());
    }

}
