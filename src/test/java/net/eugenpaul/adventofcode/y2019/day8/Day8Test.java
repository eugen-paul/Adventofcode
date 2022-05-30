package net.eugenpaul.adventofcode.y2019.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2019Day8_testPuzzle1() {
        testPuzzle1("123456789012", 1);
    }

    private void testPuzzle1(String inputData, int response1) {
        Day8 event = new Day8();

        event.setWidth(3);
        event.setHeight(2);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(response1, event.getResponse1());
    }

    @Test
    void testTest2019Day8_testPuzzle2() {
        testPuzzle2("0222112222120000", 1);
    }

    private void testPuzzle2(String inputData, int response1) {
        Day8 event = new Day8();

        event.setWidth(2);
        event.setHeight(2);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(" #\n# ", event.getMessage());
    }

    @Test
    void testSolution2019Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2176, event.getResponse1());
        assertEquals(//
                "" //
                        + " ##  #   ##  # ###  #   #\n" //
                        + "#  # #   ## #  #  # #   #\n" //
                        + "#     # # ##   ###   # # \n" //
                        + "#      #  # #  #  #   #  \n" //
                        + "#  #   #  # #  #  #   #  \n" //
                        + " ##    #  #  # ###    #  ", //
                event.getMessage());
    }

}
