package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class RangeTest {

    @Test
    void testSubtractNothing() {
        Range a = new Range(0, 10);
        Range b = new Range(11, 15);

        var sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(a, sub.get(0));
    }

    @Test
    void testSubtractAll() {
        Range a = new Range(0, 10);
        Range b = new Range(-5, 15);

        var sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(0, sub.size());

        a = new Range(0, 10);
        b = new Range(0, 15);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(0, sub.size());

        a = new Range(0, 10);
        b = new Range(-1, 10);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(0, sub.size());

        a = new Range(0, 10);
        b = new Range(0, 10);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(0, sub.size());
    }

    @Test
    void testSubtractBegin() {
        Range a = new Range(0, 10);
        Range b = new Range(0, 5);

        var sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Range(6, 10), sub.get(0));

        a = new Range(0, 10);
        b = new Range(-5, 5);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Range(6, 10), sub.get(0));

        a = new Range(0, 10);
        b = new Range(-5, 0);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Range(1, 10), sub.get(0));
    }

    @Test
    void testSubtractEnd() {
        Range a = new Range(0, 10);
        Range b = new Range(5, 12);

        var sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Range(0, 4), sub.get(0));

        a = new Range(0, 10);
        b = new Range(10, 12);

        sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Range(0, 9), sub.get(0));
    }

    @Test
    void testSubtractMiddle() {
        Range a = new Range(0, 10);
        Range b = new Range(2, 8);

        var sub = a.subtract(b);
        assertNotNull(sub);
        assertEquals(2, sub.size());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Range(0, 1))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Range(9, 10))).count());
    }

    @Test
    void testMergeContain() {
        Range a = new Range(0, 10);
        Range b = new Range(2, 8);

        var merge = Range.merge(a, b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
    }

    @Test
    void testMergeOverlap1() {
        Range a = new Range(0, 10);
        Range b = new Range(2, 20);

        var merge = Range.merge(a, b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 20))).count());
    }

    @Test
    void testMergeOverlap2() {
        Range a = new Range(0, 10);
        Range b = new Range(-5, 3);

        var merge = Range.merge(a, b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(-5, 10))).count());
    }

    @Test
    void testMergeWithoutOverlap() {
        Range a = new Range(0, 10);
        Range b = new Range(15, 30);

        var merge = Range.merge(a, b);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(15, 30))).count());
    }

    @Test
    void testMergeListNew() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range b = new Range(40, 50);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(3, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(20, 30))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(40, 50))).count());
    }

    @Test
    void testMergeListOverlap1() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range b = new Range(25, 50);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(20, 50))).count());
    }

    @Test
    void testMergeListOverlap2() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range a3 = new Range(35, 40);
        Range b = new Range(15, 50);

        var merge = Range.merge(List.of(a1, a2, a3), b);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(15, 50))).count());
    }

    @Test
    void testMergeListOverlap15() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range a3 = new Range(35, 40);
        Range b = new Range(15, 36);

        var merge = Range.merge(List.of(a1, a2, a3), b);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(15, 40))).count());
    }

    @Test
    void testMergeListOverlapAll() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range b = new Range(-5, 50);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(-5, 50))).count());
    }
}
