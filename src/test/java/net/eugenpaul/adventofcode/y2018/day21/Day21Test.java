package net.eugenpaul.adventofcode.y2018.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testSolution2018Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4797782, event.getLowestRegister0());
        assertEquals(6086461, event.getLowestRegister0ForMostInstructions());
    }

}
