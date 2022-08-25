package net.eugenpaul.adventofcode.y2021.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2021Day18_a() {
        testPuzzle(List.of(//
                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", //
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]", //
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]", //
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]", //
                "[7,[5,[[3,8],[1,4]]]]", //
                "[[2,[2,2]],[8,[8,1]]]", //
                "[2,9]", //
                "[1,[[[9,3],9],[[9,0],[0,7]]]]", //
                "[[[5,[7,4]],7],1]", //
                "[[[[4,2],2],6],[8,7]]" //
        ), 3488, -1);
    }

    @Test
    void testTest2021Day18_b() {
        testPuzzle(List.of(//
                "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]", //
                "[[[5,[2,8]],4],[5,[[9,9],0]]]", //
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]", //
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]", //
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]", //
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]", //
                "[[[[5,4],[7,7]],8],[[8,3],8]]", //
                "[[9,3],[[9,9],[6,[4,9]]]]", //
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]", //
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]" //
        ), 4140, 3993);
    }

    private void testPuzzle(List<String> inputData, long magnitude, long largestMagnitude) {
        Day18 event = new Day18();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(magnitude, event.getMagnitude());
        if (largestMagnitude > 0) {
            assertEquals(largestMagnitude, event.getLargestMagnitude());
        }
    }

    @Test
    void testSolution2021Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3725, event.getMagnitude());
        assertEquals(4832, event.getLargestMagnitude());
    }

}
