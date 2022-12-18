package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Area3dTest {

    private Area3d a;

    @BeforeEach
    void init() {
        // min = 0, max = 9
        a = new Area3d(0, 0, 0, 10, 10, 10);
    }

    @Test
    void testSubstract() {
        Area3d b = new Area3d(10, 0, 0, 1, 1, 1);
        var sub = a.substract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(a, sub.get(0));
    }

    @Test
    void testSubstractX() {
        Area3d b = new Area3d(5, -2, -2, 20, 20, 20);
        var sub = a.substract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Area3d(0, 0, 0, 5, 10, 10), sub.get(0));
    }

    @Test
    void testSubstractY() {
        Area3d b = new Area3d(-2, 5, -2, 20, 20, 20);
        var sub = a.substract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Area3d(0, 0, 0, 10, 5, 10), sub.get(0));
    }

    @Test
    void testSubstractZ() {
        Area3d b = new Area3d(-2, -2, 5, 20, 20, 20);
        var sub = a.substract(b);
        assertNotNull(sub);
        assertEquals(1, sub.size());
        assertEquals(new Area3d(0, 0, 0, 10, 10, 5), sub.get(0));
    }

    @Test
    void testSubstractMiddle() {
        Area3d b = new Area3d(2, 3, 4, 1, 2, 3);
        var sub = a.substract(b);
        assertNotNull(sub);
        assertEquals(26, sub.size());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 0, 0, 2, 3, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 0, 4, 2, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 0, 7, 2, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 3, 0, 2, 2, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 3, 4, 2, 2, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 3, 7, 2, 2, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 5, 0, 2, 5, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 5, 4, 2, 5, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(0, 5, 7, 2, 5, 3))).count());

        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 0, 0, 1, 3, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 0, 4, 1, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 0, 7, 1, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 3, 0, 1, 2, 4))).count());

        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 3, 7, 1, 2, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 5, 0, 1, 5, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 5, 4, 1, 5, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(2, 5, 7, 1, 5, 3))).count());

        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 0, 0, 7, 3, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 0, 4, 7, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 0, 7, 7, 3, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 3, 0, 7, 2, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 3, 4, 7, 2, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 3, 7, 7, 2, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 5, 0, 7, 5, 4))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 5, 4, 7, 5, 3))).count());
        assertEquals(1, sub.stream().filter(v -> v.equals(new Area3d(3, 5, 7, 7, 5, 3))).count());
    }
}
