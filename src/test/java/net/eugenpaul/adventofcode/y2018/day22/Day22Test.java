package net.eugenpaul.adventofcode.y2018.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2018Day22_testPuzzle1() {
        testPuzzle1(//
                List.of(//
                        "depth: 510", //
                        "target: 10,10" //
                )//
                , 114, 45 //
        );

    }

    private void testPuzzle1(List<String> inputData, int riskLevel, int cost) {
        Day22 event = new Day22();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(riskLevel, event.getRiskLevel());
        assertEquals(cost, event.getCost());
    }

    @Test
    void testSolution2018Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(11575, event.getRiskLevel());
        assertEquals(1068, event.getCost());
    }

}
