package net.eugenpaul.adventofcode.y2022.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2022Day3() {
        testPuzzle(List.of(//
                "vJrwpWtwJgWrhcsFMMfFFhFp", //
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", //
                "PmmdzqPrVvPwwTWBwg", //
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", //
                "ttgJtRGJQctTZtZT", //
                "CrZsJsPPZsGzwwsLwLmpwMDw" //
        ), 157, 70);
    }

    private void testPuzzle(List<String> inputData, int sumOfThePriorities, int sumOfTheGroupsPriorities) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sumOfThePriorities, event.getSumOfThePriorities());
        assertEquals(sumOfTheGroupsPriorities, event.getSumOfTheGroupsPriorities());
    }

    @Test
    void testSolution2022Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(7568, event.getSumOfThePriorities());
        assertEquals(2780, event.getSumOfTheGroupsPriorities());
    }

}
