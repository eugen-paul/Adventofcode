package net.eugenpaul.adventofcode.y2016.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day8Test {

    @Test
    void testTest2016Day8_puzzle1() {
        testPuzzle1(List.of(//
                "rect 3x2", //
                "rotate column x=1 by 1", //
                "rotate row y=0 by 4", //
                "rotate column x=1 by 1" //
        ), //
                6 //
        );
    }

    private void testPuzzle1(List<String> inputData, long pixelsLit) {
        Day8 event = new Day8();

        event.setSizeX(7);
        event.setSizeY(3);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(pixelsLit, event.getPixelsLit());
    }

    @Test
    void testSolution2016Day8() {
        Day8 event = new Day8();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day8/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(115, event.getPixelsLit());

        List<String> displayData = List.of(//
                "#### #### #### #   ##  # #### ###  ####  ###   ## ", //
                "#    #    #    #   ## #  #    #  # #      #     # ", //
                "###  ###  ###   # # ##   ###  #  # ###    #     # ", //
                "#    #    #      #  # #  #    ###  #      #     # ", //
                "#    #    #      #  # #  #    # #  #      #  #  # ", //
                "#### #    ####   #  #  # #    #  # #     ###  ##  " //
        );

        assertEquals(displayData, event.getDisplayData());
    }

}
