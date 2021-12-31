package net.eugenpaul.adventofcode.y2015.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day13
 */
class Test2015Day13 {

    @Test
    void testTest2015Day13() {
        testPuzzle(List.of(//
                "Alice would gain 54 happiness units by sitting next to Bob.", //
                "Alice would lose 79 happiness units by sitting next to Carol.", //
                "Alice would lose 2 happiness units by sitting next to David.", //
                "Bob would gain 83 happiness units by sitting next to Alice.", //
                "Bob would lose 7 happiness units by sitting next to Carol.", //
                "Bob would lose 63 happiness units by sitting next to David.", //
                "Carol would lose 62 happiness units by sitting next to Alice.", //
                "Carol would gain 60 happiness units by sitting next to Bob.", //
                "Carol would gain 55 happiness units by sitting next to David.", //
                "David would gain 46 happiness units by sitting next to Alice.", //
                "David would lose 7 happiness units by sitting next to Bob.", //
                "David would gain 41 happiness units by sitting next to Carol."//
        ), 330);

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int maxHappiness ) {
        Day13 event = new Day13();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(maxHappiness, event.getMaxHappiness());
    }

}