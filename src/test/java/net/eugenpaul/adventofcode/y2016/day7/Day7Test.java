package net.eugenpaul.adventofcode.y2016.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day7Test {

    @Test
    void testTest2016Day7_puzzle1() {
        testPuzzle1(List.of(//
                "abba[mnop]qrst", //
                "abcd[bddb]xyyx", //
                "aaaa[qwer]tyui", //
                "ioxxoj[asdfgh]zxcvbn" //
        ), //
                2 //
        );
    }

    private void testPuzzle1(List<String> inputData, long ipWithTls) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(ipWithTls, event.getIpWithTls());
    }

    @Test
    void testTest2016Day7_puzzle2() {
        testPuzzle2(List.of(//
                "aba[bab]xyz", //
                "xyx[xyx]xyx", //
                "aaa[kek]eke", //
                "zazbz[bzb]cdb" //
        ), //
                3 //
        );
    }

    private void testPuzzle2(List<String> inputData, long ipWithSsl) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(ipWithSsl, event.getIpWithSsl());
    }

}
