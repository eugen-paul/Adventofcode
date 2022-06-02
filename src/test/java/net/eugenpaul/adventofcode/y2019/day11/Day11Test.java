package net.eugenpaul.adventofcode.y2019.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day11Test {

    @Test
    void testSolution2019Day11() {
        Day11 event = new Day11();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day11/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(//
                "" //
                        + " ###    ## #    #### ###  #  #  ##  #  #  #" //
                        + "##  #    # #    #    #  # #  # #  # #  #   " //
                        + "##  #    # #    ###  ###  #  # #    #  #   " //
                        + " ###     # #    #    #  # #  # #    #  #  #" //
                        + " # #  #  # #    #    #  # #  # #  # #  # ##" //
                        + "##  #  ##  #### #    ###   ##   ##   ##  ##",
                event.getText());
    }

}
