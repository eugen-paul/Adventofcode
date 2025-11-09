package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Line3dTest {

    @Test
    void testFromPointPointSetsFields() {
        Pos3d a = new Pos3d(1, 2, 3);
        Pos3d b = new Pos3d(4, 6, 8);

        Line3d result = Line3d.fromPointPoint(a, b);

        assertEquals(1L, result.getPointX());
        assertEquals(2L, result.getPointY());
        assertEquals(3L, result.getPointZ());
        assertEquals(3L, result.getDeltaX()); // 4 - 1
        assertEquals(4L, result.getDeltaY()); // 6 - 2
        assertEquals(5L, result.getDeltaZ()); // 8 - 3
    }

    @Test
    void testFromPointVectorSetsFields() {
        Pos3d a = new Pos3d(1, 2, 3);
        Pos3d v = new Pos3d(4, 6, 8);

        Line3d result = Line3d.fromPointVector(a, v);

        assertEquals(1L, result.getPointX());
        assertEquals(2L, result.getPointY());
        assertEquals(3L, result.getPointZ());
        assertEquals(4L, result.getDeltaX());
        assertEquals(6L, result.getDeltaY());
        assertEquals(8L, result.getDeltaZ());
    }

    @Test
    void testIntersection_ok() {
        Line3d line1 = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(-1, 2, 4));
        Line3d line2 = Line3d.fromPointVector(new Pos3d(2, 1, 0), new Pos3d(-2, 1, 4));

        Pos3d intersection12 = line1.intersection(line2);
        Pos3d intersection21 = line2.intersection(line1);

        assertEquals(4, intersection12.getX());
        assertEquals(0, intersection12.getY());
        assertEquals(-4, intersection12.getZ());

        assertEquals(4, intersection21.getX());
        assertEquals(0, intersection21.getY());
        assertEquals(-4, intersection21.getZ());
    }

    @Test
    void testNoIntersection() {
        Line3d line1 = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(1, 2, 4));
        Line3d line2 = Line3d.fromPointVector(new Pos3d(7, 1, 8), new Pos3d(2, 4, 8));

        Pos3d intersection12 = line1.intersection(line2);
        Pos3d intersection21 = line2.intersection(line1);

        assertNull(intersection12);
        assertNull(intersection21);
    }

    @Test
    void testIsPointOnLine_true() {
        Line3d line = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(1, 2, 4));
        assertTrue(line.isPointOnLine(new Pos3d(3, 2, 0)));
        assertTrue(line.isPointOnLine(new Pos3d(4, 4, 4)));
        assertTrue(line.isPointOnLine(new Pos3d(5, 6, 8)));
        assertTrue(line.isPointOnLine(new Pos3d(2, 0, -4)));
        assertTrue(line.isPointOnLine(new Pos3d(1, -2, -8)));
    }

    @Test
    void testIsPointOnLine_false() {
        Line3d line = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(1, 2, 4));
        assertFalse(line.isPointOnLine(new Pos3d(5, 6, 7)));
        assertFalse(line.isPointOnLine(new Pos3d(10, 6, 7)));
        assertFalse(line.isPointOnLine(new Pos3d(5, 7, 7)));
        assertFalse(line.isPointOnLine(new Pos3d(5, 13, 7)));
    }

    @Test
    void testIsIntersecting_true() {
        Line3d line1 = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(1, 2, 4));
        Line3d line2 = Line3d.fromPointVector(new Pos3d(2, 1, 0), new Pos3d(-2, 1, 4));

        assertTrue(line1.isIntersecting(line2));
        assertTrue(line2.isIntersecting(line1));
    }

    @Test
    void testIsIntersecting_false() {
        Line3d line1 = Line3d.fromPointVector(new Pos3d(3, 2, 0), new Pos3d(1, 2, 4));
        Line3d line2 = Line3d.fromPointVector(new Pos3d(7, 1, 8), new Pos3d(2, 4, 8));

        assertFalse(line1.isIntersecting(line2));
        assertFalse(line2.isIntersecting(line1));
    }
}