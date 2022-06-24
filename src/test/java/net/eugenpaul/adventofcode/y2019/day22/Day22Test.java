package net.eugenpaul.adventofcode.y2019.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2019Day22_testNewStack() {
        testPuzzle1(List.of(//
                "deal into new stack" //
        ), List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
    }

    @Test
    void testTest2019Day22_testCut1() {
        testPuzzle1(List.of(//
                "cut 2" //
        ), List.of(2, 3, 4, 5, 6, 7, 8, 9, 0, 1));
    }

    @Test
    void testTest2019Day22_testCut2() {
        testPuzzle1(List.of(//
                "cut -2" //
        ), List.of(8, 9, 0, 1, 2, 3, 4, 5, 6, 7));
    }

    @Test
    void testTest2019Day22_testIncrement() {
        testPuzzle1(List.of(//
                "deal with increment 3" //
        ), List.of(0, 7, 4, 1, 8, 5, 2, 9, 6, 3));
    }

    @Test
    void testTest2019Day22_testPuzzle_1() {
        testPuzzle1(List.of(//
                "deal with increment 7", //
                "deal into new stack", //
                "deal into new stack" //
        ), List.of(0, 3, 6, 9, 2, 5, 8, 1, 4, 7));
    }

    @Test
    void testTest2019Day22_testPuzzle_2() {
        testPuzzle1(List.of(//
                "cut 6", //
                "deal with increment 7", //
                "deal into new stack" //
        ), List.of(3, 0, 7, 4, 1, 8, 5, 2, 9, 6));
    }

    @Test
    void testTest2019Day22_testPuzzle_3() {
        testPuzzle1(List.of(//
                "deal with increment 7", //
                "deal with increment 9", //
                "cut -2" //
        ), List.of(6, 3, 0, 7, 4, 1, 8, 5, 2, 9));
    }

    @Test
    void testTest2019Day22_testPuzzle_4() {
        testPuzzle1(List.of(//
                "deal into new stack", //
                "cut -2", //
                "deal with increment 7", //
                "cut 8", //
                "cut -4", //
                "deal with increment 7", //
                "cut 3", //
                "deal with increment 9", //
                "deal with increment 3", //
                "cut -1" //
        ), List.of(9, 2, 5, 8, 1, 4, 7, 0, 3, 6));
    }

    private void testPuzzle1(List<String> inputData, List<Integer> carts) {
        Day22 event = new Day22();

        event.setCardCount(10);

        assertTrue(event.testSlow(inputData));
        for (int i = 0; i < 10; i++) {
            assertEquals(carts.get(i), event.getCart(i));
            assertEquals(i, event.getPos(carts.get(i)));
        }
    }

    // @Test
    // void testSolution2019Day22() {
    // Day22 event = new Day22();

    // List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day22/puzzle1.txt");
    // assertTrue(event.doPuzzleFromData(eventData));
    // assertEquals(568, event.getWayAZ());
    // assertEquals(6546, event.getWayAZ2());
    // }

}
