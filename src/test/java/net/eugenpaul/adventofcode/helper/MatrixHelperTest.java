package net.eugenpaul.adventofcode.helper;

import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class MatrixHelperTest {

    @Test
    void testTurnLeft_squareMatrix() {
        String[][] in = {
            {"1", "2"},
            {"3", "4"}
        };
        String[][] expected = {
            {"3", "1"},
            {"4", "2"}
        };
        String[][] out = turnRight(in);
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, out);
    }

    @Test
    void testTurnLeft_rectangularMatrix() {
        String[][] in = {
            {"a", "b", "c"},
            {"d", "e", "f"}
        };
        String[][] expected = {
            {"d", "a"},
            {"e", "b"},
            {"f", "c"}
        };
        String[][] out = turnRight(in);
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, out);
    }
    
    @Test
    void testTurnLeft_oneLine() {
        String[][] in = {
            {"a", "b", "c"}
        };
        String[][] expected = {
            {"a"},
            {"b"},
            {"c"}
        };
        String[][] out = turnRight(in);
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, out);
    }
    
    
    @Test
    void testTurnLeft_oneRow() {
        String[][] in = {
            {"a"},
            {"d"}
        };
        String[][] expected = {
            {"d", "a"}
        };
        String[][] out = turnRight(in);
        org.junit.jupiter.api.Assertions.assertArrayEquals(expected, out);
    }

    @Test
    void testTurnLeft_emptyAndNull() {
        String[][] empty = new String[0][0];
        String[][] outEmpty = turnRight(empty);
        org.junit.jupiter.api.Assertions.assertEquals(0, outEmpty.length);

        String[][] outNull = turnRight((String[][])null);
        org.junit.jupiter.api.Assertions.assertNull(outNull);
    }
    
    @Test
    void testTurnLeftList_squareMatrix() {
        List<List<String>> in = List.of(
            List.of("1", "2"),
            List.of("3", "4")
        );
        List<List<String>> expected = List.of(
            List.of("3", "1"),
            List.of("4", "2")
        );
        List<List<String>> out = turnRight(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftList_rectangularMatrix() {
        List<List<String>> in = List.of(
            List.of("a", "b", "c"),
            List.of("d", "e", "f")
        );
        List<List<String>> expected = List.of(
            List.of("d", "a"),
            List.of("e", "b"),
            List.of("f", "c")
        );
        List<List<String>> out = turnRight(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftList_oneLine() {
        List<List<String>> in = List.of(
            List.of("a", "b", "c")
        );
        List<List<String>> expected = List.of(
            List.of("a"),
            List.of("b"),
            List.of("c")
        );
        List<List<String>> out = turnRight(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftList_oneRow() {
        List<List<String>> in = List.of(
            List.of("a"),
            List.of("d")
        );
        List<List<String>> expected = List.of(
            List.of("d", "a")
        );
        List<List<String>> out = turnRight(in);
        assertEquals(expected, out);
    }


    @Test
    void testTurnLeftList_emptyAndNull() {
        List<List<String>> empty = Collections.emptyList();
        List<List<String>> outEmpty = turnRight(empty);
        assertEquals(0, outEmpty.size());

        List<List<String>> outNull = turnRight((List<List<String>> )null);
        assertNull(outNull);
    }
    
    @Test
    void testTurnLeftString_squareMatrix() {
        List<String> in = List.of(
            "12",
            "34"
        );
        List<String> expected = List.of(
            "31",
            "42"
        );
        List<String> out = turnRightStrings(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftString_rectangularMatrix() {
        List<String> in = List.of(
            "abc",
            "def"
        );
        List<String> expected = List.of(
            "da",
            "eb",
            "fc"
        );
        List<String> out = turnRightStrings(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftString_oneLine() {
        List<String> in = List.of(
            "abc"
        );
        List<String> expected = List.of(
            "a",
            "b",
            "c"
        );
        List<String> out = turnRightStrings(in);
        assertEquals(expected, out);
    }

    @Test
    void testTurnLeftString_oneRow() {
        List<String> in = List.of(
            "a",
            "d"
        );
        List<String> expected = List.of(
            "da"
        );
        List<String> out = turnRightStrings(in);
        assertEquals(expected, out);
    }


    @Test
    void testTurnLeftString_emptyAndNull() {
        List<String> empty = Collections.emptyList();
        List<String> outEmpty = turnRightStrings(empty);
        assertEquals(0, outEmpty.size());

        List<String> outNull = turnRightStrings((List<String> )null);
        assertNull(outNull);
    }
}
