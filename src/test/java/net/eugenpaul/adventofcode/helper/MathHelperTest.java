package net.eugenpaul.adventofcode.helper;

import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class MathHelperTest {

    @Test
    void testMax() {
        assertEquals(55L, max(12L, 34L, 55L));
        assertEquals(55L, max(55L, 34L, 12L));
        assertEquals(12L, max(12L));
    }

    @Test
    void testMax2() {
        assertEquals(33L, max(List.of(12, 22, 33)));
        assertEquals(33L, max(List.of(33, 12, 3)));

        assertEquals(33L, max(List.of(33L, 12L, 3L)));

        assertEquals(33L, max(Set.of(4, 22, 33, 10)));
    }

    @Test
    void testMax3() {
        assertEquals(4L, max(List.of("12", "223", "3341"), v -> (long) v.length()));
    }

    @Test
    void testMin() {
        assertEquals(12L, min(12L, 34L, 55L));
        assertEquals(12L, min(55L, 34L, 12L));
        assertEquals(12L, min(12L));
    }

    @Test
    void testMin2() {
        assertEquals(12L, min(List.of(12, 22, 33)));
        assertEquals(3L, min(List.of(33, 12, 3)));

        assertEquals(3L, min(List.of(33L, 12L, 3L)));

        assertEquals(4L, min(Set.of(4, 22, 33, 10)));
    }

    @Test
    void testMin3() {
        assertEquals(2L, min(List.of("12", "223", "3341"), v -> (long) v.length()));
    }

    @Test
    void testModularExponentiation() {
        assertEquals(1, pow(1, 1, 2));
        assertEquals(4, pow(4, 7, 9));
    }

    @Test
    void testSum() {
        assertEquals(1, sumRange(1, 1));
        assertEquals(3, sumRange(1, 2));
        assertEquals(55, sumRange(1, 10));
    }

    @Test
    void testSum2() {
        assertEquals(66L, sum(List.of(11, 22, 33)));
        assertEquals(48L, sum(List.of(33, 12, 3)));
        assertEquals(69L, sum(Set.of(4, 22, 33, 10)));
    }

    @Test
    void testSum3() {
        assertEquals(6L, sum(List.of("11", "22", "33"), v -> (long) v.length()));
    }
}
