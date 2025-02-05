package net.eugenpaul.adventofcode.y2023.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2023Day21() {
        testPuzzle(FileReaderHelper.readListStringFromFile("y2023/day21/test_puzzle.txt"));
    }

    private void testPuzzle(List<String> testFilePath) {
        Day21 event = new Day21();
        event.setSteps1(6);
        assertEquals(16, event.doPuzzle1(testFilePath));

        // event.setSteps2(6);
        // assertEquals(16, event.doPuzzle2(testFilePath));

        // event.setSteps2(10);
        // assertEquals(50, event.doPuzzle2(testFilePath));

        // event.setSteps2(50);
        // assertEquals(1594, event.doPuzzle2(testFilePath));

        // event.setSteps2(100);
        // assertEquals(6536, event.doPuzzle2(testFilePath));
        
        // event.setSteps2(500);
        // assertEquals(167004, event.doPuzzle2(testFilePath));
        
        // event.setSteps2(1000);
        // assertEquals(668697, event.doPuzzle2(testFilePath));
        
        // event.setSteps2(5000);
        // assertEquals(16733044, event.doPuzzle2(testFilePath));
    }
    
    @Test
    void testSolution2023Day21() {
        Day21 event = new Day21();
        
        List<String> eventData = FileReaderHelper.readListStringFromFile("y2023/Day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        
        assertEquals(3605, event.getTotalScore());
        assertEquals(596734624269210L, event.getTotalScore2());
    }
}
