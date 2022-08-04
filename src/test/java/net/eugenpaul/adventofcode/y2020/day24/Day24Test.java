package net.eugenpaul.adventofcode.y2020.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2020Day24() {
        testPuzzle(List.of(//
                "sesenwnenenewseeswwswswwnenewsewsw", //
                "neeenesenwnwwswnenewnwwsewnenwseswesw", //
                "seswneswswsenwwnwse", //
                "nwnwneseeswswnenewneswwnewseswneseene", //
                "swweswneswnenwsewnwneneseenw", //
                "eesenwseswswnenwswnwnwsewwnwsene", //
                "sewnenenenesenwsewnenwwwse", //
                "wenwwweseeeweswwwnwwe", //
                "wsweesenenewnwwnwsenewsenwwsesesenwne", //
                "neeswseenwwswnwswswnw", //
                "nenwswwsewswnenenewsenwsenwnesesenew", //
                "enewnwewneswsewnwswenweswnenwsenwsw", //
                "sweneswneswneneenwnewenewwneswswnese", //
                "swwesenesewenwneswnwwneseswwne", //
                "enesenwswwswneneswsenwnewswseenwsese", //
                "wnwnesenesenenwwnenwsewesewsesesew", //
                "nenewswnwewswnenesenwnesewesw", //
                "eneswnwswnwsenenwnwnwwseeswneewsenese", //
                "neswnwewnwnwseenwseesewsenwsweewe", //
                "wseweeenwnesenwwwswnew" //
        ), 10, 2208);
    }

    private void testPuzzle(List<String> inputData, int black, int black2) {
        Day24 event = new Day24();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(black, event.getBlack());
        assertEquals(black2, event.getBlack2());
    }

    @Test
    void testSolution2020Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(459, event.getBlack());
        assertEquals(4150, event.getBlack2());
    }

}
