package net.eugenpaul.adventofcode.y2017.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day9Test {

    @Test
    void testTest2017Day9_puzzle1() {
        testPuzzle1(List.of("{}"), 1);
        testPuzzle1(List.of("{{{}}}"), 6);
        testPuzzle1(List.of("{{},{}}"), 5);
        testPuzzle1(List.of("{{{},{},{{}}}}"), 16);
        testPuzzle1(List.of("{<a>,<a>,<a>,<a>}"), 1);
        testPuzzle1(List.of("{{<ab>},{<ab>},{<ab>},{<ab>}}"), 9);
        testPuzzle1(List.of("{{<!!>},{<!!>},{<!!>},{<!!>}}"), 9);
        testPuzzle1(List.of("{{<a!>},{<a!>},{<a!>},{<ab>}}"), 3);
    }

    private void testPuzzle1(List<String> inputData, int score) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(score, event.getScore());
    }

    @Test
    void testTest2017Day9_puzzle2() {
        testPuzzle2(List.of("<>"), 0);
        testPuzzle2(List.of("<random characters>"), 17);
        testPuzzle2(List.of("<<<<>"), 3);
        testPuzzle2(List.of("<{!>}>"), 2);
        testPuzzle2(List.of("<!!>"), 0);
        testPuzzle2(List.of("<!!!>>"), 0);
        testPuzzle2(List.of("<{o\"i!a,<{i<a>"), 10);
    }

    private void testPuzzle2(List<String> inputData, int nonCanceledCharacters) {
        Day9 event = new Day9();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(nonCanceledCharacters, event.getNonCanceledCharacters());
    }
}
