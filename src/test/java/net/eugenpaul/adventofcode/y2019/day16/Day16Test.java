package net.eugenpaul.adventofcode.y2019.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2019Day16_testPuzzle1() {
        testPuzzle1("80871224585914546619083218645595", "24176176");
        testPuzzle1("19617804207202209144916044189917", "73745418");
        testPuzzle1("69317163492948606335995924319873", "52432133");
    }

    // @Test
    // void testTest2019Day16_testPuzzle2() {
    //     testPuzzle1("03036732577212944063491565474664", 24176176);
    // }

    private void testPuzzle1(String inputData, String output) {
        Day16 event = new Day16();

        assertTrue(event.doEvent(inputData));
        assertEquals(output, event.getOutput());
    }

    @Test
    void testSolution2019Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("37153056", event.getOutput());
        // assertEquals(13108426, event.getFuel41kkk());
    }

}
