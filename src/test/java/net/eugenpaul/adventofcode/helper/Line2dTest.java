package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Line2dTest {

    @Test
    void testFromPointPointSetsFields() {
        SimplePos a = new SimplePos(1, 2);
        SimplePos b = new SimplePos(4, 6);

        Line2d result = Line2d.fromPointPoint(a, b);

        assertEquals(1L, result.getPointX());
        assertEquals(2L, result.getPointY());
        assertEquals(3L, result.getDeltaX()); // 4 - 1
        assertEquals(4L, result.getDeltaY()); // 6 - 2
    }

    @Test
    void testFromPointVectorSetsFields() {
        SimplePos a = new SimplePos(5, 7);
        SimplePos v = new SimplePos(2, -3);

        Line2d result = Line2d.fromPointVector(a, v);

        assertEquals(5L, result.getPointX());
        assertEquals(7L, result.getPointY());
        assertEquals(2L, result.getDeltaX());
        assertEquals(-3L, result.getDeltaY());
    }

    @Test
    void testIntersection_ok() {
        Line2d line1 = Line2d.fromPointPoint(new SimplePos(3, 2), new SimplePos(5, 3));
        Line2d line2 = Line2d.fromPointPoint(new SimplePos(4, 2), new SimplePos(7, 3));

        SimplePos intersection12 = line1.intersection(line2);
        SimplePos intersection21 = line2.intersection(line1);

        assertEquals(1, intersection12.getX());
        assertEquals(1, intersection12.getY());

        assertEquals(1, intersection21.getX());
        assertEquals(1, intersection21.getY());
    }

    @Test
    void testNoIntersection() {
        Line2d line1 = Line2d.fromPointVector(new SimplePos(3, 0), new SimplePos(1, 3));
        Line2d line2 = Line2d.fromPointVector(new SimplePos(3, 7), new SimplePos(1, 3));

        SimplePos intersection12 = line1.intersection(line2);
        SimplePos intersection21 = line2.intersection(line1);

        assertNull(intersection12);
        assertNull(intersection21);
    }

    @Test
    void testIsPointOnLine_true() {
        Line2d line = Line2d.fromPointVector(new SimplePos(3, 2), new SimplePos(1, 2));
        assertTrue(line.isPointOnLine(new SimplePos(3, 2)));
        assertTrue(line.isPointOnLine(new SimplePos(4, 4)));
        assertTrue(line.isPointOnLine(new SimplePos(5, 6)));
    }

    @Test
    void testIsPointOnLine_false() {
        Line2d line = Line2d.fromPointVector(new SimplePos(3, 2), new SimplePos(1, 2));
        assertFalse(line.isPointOnLine(new SimplePos(5, 0)));
        assertFalse(line.isPointOnLine(new SimplePos(10, 6)));
        assertFalse(line.isPointOnLine(new SimplePos(5, 7)));
        assertFalse(line.isPointOnLine(new SimplePos(5, 13)));
    }

    @Test
    void testIsIntersecting_true() {
        Line2d line1 = Line2d.fromPointVector(new SimplePos(3, 0), new SimplePos(1, 3));
        Line2d line2 = Line2d.fromPointVector(new SimplePos(0, 3), new SimplePos(3, -1));

        assertTrue(line1.isIntersecting(line2));
        assertTrue(line2.isIntersecting(line1));
    }

    @Test
    void testIsIntersecting_false() {
        Line2d line1 = Line2d.fromPointVector(new SimplePos(3, 0), new SimplePos(1, 3));
        Line2d line2 = Line2d.fromPointVector(new SimplePos(3, 7), new SimplePos(2, 6));

        assertFalse(line1.isIntersecting(line2));
        assertFalse(line2.isIntersecting(line1));
    }
}