package net.eugenpaul.adventofcode.y2018.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2018Day9_puzzle1() {
        testPuzzle1(List.of(//
                "9 players; last marble is worth 25 points" //
        ), 32);

        testPuzzle1(List.of(//
                "10 players; last marble is worth 1618 points" //
        ), 8317);

        testPuzzle1(List.of(//
                "13 players; last marble is worth 7999 points" //
        ), 146373);

        testPuzzle1(List.of(//
                "17 players; last marble is worth 1104 points" //
        ), 2764);

        testPuzzle1(List.of(//
                "21 players; last marble is worth 6111 points" //
        ), 54718);

        testPuzzle1(List.of(//
                "30 players; last marble is worth 5807 points" //
        ), 37305);

    }

    private void testPuzzle1(List<String> inputData, int winningScore) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(winningScore, event.getWinningScore());
    }

    @Test
    void testSolution2018Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(434674L, event.getWinningScore());
        assertEquals(3653994575L, event.getWinningScore2());
    }

}
