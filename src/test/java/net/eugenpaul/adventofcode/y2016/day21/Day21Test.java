package net.eugenpaul.adventofcode.y2016.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2016Day21_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "swap position 4 with position 0", //
                        "swap letter d with letter b", //
                        "reverse positions 0 through 4", //
                        "rotate left 1 step", //
                        "move position 1 to position 4", //
                        "move position 3 to position 0", //
                        "rotate based on position of letter b", //
                        "rotate based on position of letter d"//
                ), //
                "abcde", //
                "decab" //
        );
    }

    private void testPuzzle1(List<String> inputData, String inputText, String password) {
        Day21 event = new Day21();

        event.setText(inputText);
        event.setPwd(password);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(password, event.getPassword());
        // assertEquals(inputText, event.getUnScrambled());
    }

    @Test
    void testSolution2019Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("bfheacgd", event.getPassword());
        assertEquals("gcehdbfa", event.getUnScrambled());
    }

}
