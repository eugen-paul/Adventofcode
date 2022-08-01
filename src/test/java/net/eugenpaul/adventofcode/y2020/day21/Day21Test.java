package net.eugenpaul.adventofcode.y2020.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day21Test {

    @Test
    void testTest2020Day21() {
        testPuzzle(List.of(//
                "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)", //
                "trh fvjkl sbzzf mxmxvkd (contains dairy)", //
                "sqjhc fvjkl (contains soy)", //
                "sqjhc mxmxvkd sbzzf (contains fish)" //
        ), 5, "mxmxvkd,sqjhc,fvjkl");
    }

    private void testPuzzle(List<String> inputData, long ingredientsAppear, String dangerousList) {
        Day21 event = new Day21();

        assertTrue(event.doEvent(inputData));
        assertEquals(ingredientsAppear, event.getIngredientsAppear());
        assertEquals(dangerousList, event.getDangerousList());
    }

    @Test
    void testSolution2020Day21() {
        Day21 event = new Day21();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day21/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2798, event.getIngredientsAppear());
        assertEquals("gbt,rpj,vdxb,dtb,bqmhk,vqzbq,zqjm,nhjrzzj", event.getDangerousList());
    }

}
