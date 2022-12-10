package net.eugenpaul.adventofcode.y2018.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2018Day14_testPuzzle1() {
        testPuzzle1(List.of("5"), "0124515891", 9);
        testPuzzle1(List.of("9"), "5158916779", 13);
        testPuzzle1(List.of("18"), "9251071085", 48);
        testPuzzle1(List.of("2018"), "5941429882", 86764);
    }

    private void testPuzzle1(List<String> inputData, String score, int recipesBevorScore) {
        Day14 event = new Day14();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(score, event.getScore());
        assertEquals(recipesBevorScore, event.getRecipesBevorScore());
    }

    @Test
    void testSolution2018Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("1044257397", event.getScore());
        assertEquals(20185425, event.getRecipesBevorScore());
    }

}
