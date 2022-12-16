package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
