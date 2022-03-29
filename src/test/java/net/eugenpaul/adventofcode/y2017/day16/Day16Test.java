package net.eugenpaul.adventofcode.y2017.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day16Test {

    @Test
    void testTest2017Day16_puzzle1() {
        testPuzzle1(List.of(//
                "s1,x3/4,pe/b" //
        ), "abcde", "baedc");
    }

    private void testPuzzle1(List<String> inputData, String line, String endOrder) {
        Day16 event = new Day16();

        event.setInput(line);
        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(endOrder, event.getEndOrder());
    }
}
