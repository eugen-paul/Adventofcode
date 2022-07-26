package net.eugenpaul.adventofcode.y2020.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day19Test {

    @Test
    void testTest2020Day19_1() {
        testPuzzle_1(List.of(//
                "0: 4 1 5", //
                "1: 2 3 | 3 2", //
                "2: 4 4 | 5 5", //
                "3: 4 5 | 5 4", //
                "4: \"a\"", //
                "5: \"b\"", //
                "", //
                "ababbb", //
                "bababa", //
                "abbbab", //
                "aaabbb", //
                "aaaabbb" //
        ), 2, 231);
    }

    private void testPuzzle_1(List<String> inputData, long sum, long sum2) {
        Day19 event = new Day19();

        event.setDoStep2(false);
        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
    }

    @Test
    void testTest2020Day19_2() {
        testPuzzle_2(List.of(//
                "42: 9 14 | 10 1", //
                "9: 14 27 | 1 26", //
                "10: 23 14 | 28 1", //
                "1: \"a\"", //
                "11: 42 31", //
                "5: 1 14 | 15 1", //
                "19: 14 1 | 14 14", //
                "12: 24 14 | 19 1", //
                "16: 15 1 | 14 14", //
                "31: 14 17 | 1 13", //
                "6: 14 14 | 1 14", //
                "2: 1 24 | 14 4", //
                "0: 8 11", //
                "13: 14 3 | 1 12", //
                "15: 1 | 14", //
                "17: 14 2 | 1 7", //
                "23: 25 1 | 22 14", //
                "28: 16 1", //
                "4: 1 1", //
                "20: 14 14 | 1 15", //
                "3: 5 14 | 16 1", //
                "27: 1 6 | 14 18", //
                "14: \"b\"", //
                "21: 14 1 | 1 14", //
                "25: 1 1 | 1 14", //
                "22: 14 14", //
                "8: 42", //
                "26: 14 22 | 1 20", //
                "18: 15 15", //
                "7: 14 5 | 1 21", //
                "24: 14 1", //
                "", //
                "abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa", //
                "bbabbbbaabaabba", //
                "babbbbaabbbbbabbbbbbaabaaabaaa", //
                "aaabbbbbbaaaabaababaabababbabaaabbababababaaa", //
                "bbbbbbbaaaabbbbaaabbabaaa", //
                "bbbababbbbaaaaaaaabbababaaababaabab", //
                "ababaaaaaabaaab", //
                "ababaaaaabbbaba", //
                "baabbaaaabbaaaababbaababb", //
                "abbbbabbbbaaaababbbbbbaaaababb", //
                "aaaaabbaabaaaaababaa", //
                "aaaabbaaaabbaaa", //
                "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa", //
                "babaaabbbaaabaababbaabababaaab", //
                "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba" //
        ), 3, 12);
    }

    private void testPuzzle_2(List<String> inputData, long sum, long sum2) {
        Day19 event = new Day19();

        assertTrue(event.doEvent(inputData));
        assertEquals(sum, event.getSum());
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2020Day19() {
        Day19 event = new Day19();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day19/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(144, event.getSum());
        assertEquals(260, event.getSum2());
    }

}
