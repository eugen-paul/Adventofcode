package net.eugenpaul.adventofcode.y2020.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2020Day7_testPuzzle() {
        testPuzzle(List.of(//
                "light red bags contain 1 bright white bag, 2 muted yellow bags.", //
                "dark orange bags contain 3 bright white bags, 4 muted yellow bags.", //
                "bright white bags contain 1 shiny gold bag.", //
                "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.", //
                "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.", //
                "dark olive bags contain 3 faded blue bags, 4 dotted black bags.", //
                "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.", //
                "faded blue bags contain no other bags.", //
                "dotted black bags contain no other bags." //
        ), 4, 32);
    }

    private void testPuzzle(List<String> inputData, long bagsWithShinyGold, long bagsInShinyGold) {
        Day7 event = new Day7();

        assertTrue(event.doEvent(inputData));
        assertEquals(bagsWithShinyGold, event.getBagsWithShinyGold());
        assertEquals(bagsInShinyGold, event.getBagsInShinyGold());
    }

    @Test
    void testSolution2020Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(233, event.getBagsWithShinyGold());
        assertEquals(421550, event.getBagsInShinyGold());
    }

}
