package net.eugenpaul.adventofcode.y2021.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2021Day10() {
        testPuzzle(List.of(//
                "[({(<(())[]>[[{[]{<()<>>", //
                "[(()[<>])]({[<{<<[]>>(", //
                "{([(<{}[<>[]}>{[]{[(<()>", //
                "(((({<>}<{<{<>}{[]{[]{}", //
                "[[<[([]))<([[{}[[()]]]", //
                "[{[{({}]{}}([{[{{{}}([]", //
                "{<[[]]>}<{[{[{[]{()[[[]", //
                "[<(<(<(<{}))><([]([]()", //
                "<{([([[(<>()){}]>(<<{{", //
                "<{([{{}}[<[[[<>{}]]]>[]]" //
        ), 26397, 288957);
    }

    private void testPuzzle(List<String> inputData, long score, long middleScore) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(score, event.getScore());
        assertEquals(middleScore, event.getMiddleScore());
    }

    @Test
    void testSolution2021Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(339537, event.getScore());
        assertEquals(2412013412L, event.getMiddleScore());
    }

}
