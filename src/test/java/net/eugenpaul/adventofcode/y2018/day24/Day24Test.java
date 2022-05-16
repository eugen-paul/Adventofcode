package net.eugenpaul.adventofcode.y2018.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2018Day24_testPuzzle1() {
        testPuzzle1(//
                List.of(//
                        "Immune System:", //
                        "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2", //
                        "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3", //
                        "", //
                        "Infection:", //
                        "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1", //
                        "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4" //
                )//
                , 5216 , 51//
        );
    }

    private void testPuzzle1(List<String> inputData, int units, int unitsWithBoost) {
        Day24 event = new Day24();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(units, event.getUnits());
        assertEquals(unitsWithBoost, event.getUnitsWithBoost());
    }

    @Test
    void testSolution2018Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(22859, event.getUnits());
        assertEquals(2834, event.getUnitsWithBoost());
    }

}
