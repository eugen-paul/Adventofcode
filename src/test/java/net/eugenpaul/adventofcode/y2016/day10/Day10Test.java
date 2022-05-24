package net.eugenpaul.adventofcode.y2016.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2016Day10_puzzle1() {
        testPuzzle1(List.of(//
                "value 5 goes to bot 2", //
                "bot 2 gives low to bot 1 and high to bot 0", //
                "value 3 goes to bot 1", //
                "bot 1 gives low to output 1 and high to bot 0", //
                "bot 0 gives low to output 2 and high to output 0", //
                "value 2 goes to bot 2" //
        ), //
                2, //
                2, //
                5 //
        );
    }

    private void testPuzzle1(List<String> inputData, int bot, int lowValue, int highValue) {
        Day10 event = new Day10();

        event.setLowValue(lowValue);
        event.setHighValue(highValue);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(bot, event.getBot());
    }

    @Test
    void testSolution2019Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(101, event.getBot());
        assertEquals(37789, event.getMultiplyValues());
    }

}
