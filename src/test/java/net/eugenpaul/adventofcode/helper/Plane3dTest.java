package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Plane3dTest {

    @Test
    void testFromPointPointSetsFields() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d b = new Pos3d(5, 4, 0);
        Pos3d c = new Pos3d(6, 4, 3);

        Plane3d result = Plane3d.fromPPP(a, b, c);

        assertEquals(4L, result.getPointX());
        assertEquals(1L, result.getPointY());
        assertEquals(2L, result.getPointZ());

        assertEquals(1L, result.getDeltaX1());
        assertEquals(3L, result.getDeltaY1());
        assertEquals(-2L, result.getDeltaZ1());

        assertEquals(2L, result.getDeltaX2());
        assertEquals(3L, result.getDeltaY2());
        assertEquals(1L, result.getDeltaZ2());
    }

    @Test
    void testFromPointVectorSetsFields() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d result = Plane3d.fromPVV(a, v1, v2);

        assertEquals(4L, result.getPointX());
        assertEquals(1L, result.getPointY());
        assertEquals(2L, result.getPointZ());

        assertEquals(1L, result.getDeltaX1());
        assertEquals(3L, result.getDeltaY1());
        assertEquals(-2L, result.getDeltaZ1());

        assertEquals(2L, result.getDeltaX2());
        assertEquals(3L, result.getDeltaY2());
        assertEquals(1L, result.getDeltaZ2());
    }

    @Test
    void testIntersection_ok() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        Line3d line1 = Line3d.fromPointVector(new Pos3d(1, 0, 2), new Pos3d(2, 1, -3));

        Pos3d intersection12 = plane.intersection(line1);

        assertEquals(3, intersection12.getX());
        assertEquals(1, intersection12.getY());
        assertEquals(-1, intersection12.getZ());
    }

    @Test
    void testNoIntersection() {
        Pos3d a = new Pos3d(3, 4, 1);
        Pos3d v1 = new Pos3d(2, 0, 1);
        Pos3d v2 = new Pos3d(3, 0, 4);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        Line3d line1 = Line3d.fromPointVector(new Pos3d(2, 3, 1), new Pos3d(1, 0, 3));

        Pos3d intersection12 = plane.intersection(line1);

        assertNull(intersection12);
    }

    @Test
    void testIsPointOnPlane_true() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        assertTrue(plane.isPointOnPlane(new Pos3d(4, 1, 2)));
        assertTrue(plane.isPointOnPlane(new Pos3d(5, 4, 0)));
        assertTrue(plane.isPointOnPlane(new Pos3d(6, 4, 3)));
        assertTrue(plane.isPointOnPlane(new Pos3d(7, 7, 1)));
    }

    @Test
    void testIsPointOnPlane_false() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        assertFalse(plane.isPointOnPlane(new Pos3d(5, 4, 1)));
        assertFalse(plane.isPointOnPlane(new Pos3d(6, 5, 3)));
        assertFalse(plane.isPointOnPlane(new Pos3d(7, 7, 2)));
    }

    @Test
    void testIsIntersecting_true() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        Line3d line1 = Line3d.fromPointVector(new Pos3d(1, 0, 2), new Pos3d(2, 1, -3));

        assertTrue(plane.isIntersecting(line1));
    }

    @Test
    void testIsIntersecting_false() {
        Pos3d a = new Pos3d(3, 4, 1);
        Pos3d v1 = new Pos3d(2, 0, 1);
        Pos3d v2 = new Pos3d(3, 0, 4);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        Line3d line1 = Line3d.fromPointVector(new Pos3d(2, 3, 1), new Pos3d(1, 0, 3));

        assertFalse(plane.isIntersecting(line1));
    }

    @Test
    void testIsLineOnPlane_true() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        assertTrue(plane.isLineOnPlane(Line3d.fromPointPoint(new Pos3d(4, 1, 2), new Pos3d(5, 4, 0))));
        assertTrue(plane.isLineOnPlane(Line3d.fromPointPoint(new Pos3d(4, 1, 2), new Pos3d(6, 4, 3))));
        assertTrue(plane.isLineOnPlane(Line3d.fromPointPoint(new Pos3d(4, 1, 2), new Pos3d(7, 7, 1))));
    }

    @Test
    void testIsLineOnPlane_false() {
        Pos3d a = new Pos3d(4, 1, 2);
        Pos3d v1 = new Pos3d(1, 3, -2);
        Pos3d v2 = new Pos3d(2, 3, 1);

        Plane3d plane = Plane3d.fromPVV(a, v1, v2);

        assertFalse(plane.isLineOnPlane(Line3d.fromPointPoint(new Pos3d(5, 4, 1), new Pos3d(6, 5, 3))));
        assertFalse(plane.isLineOnPlane(Line3d.fromPointPoint(new Pos3d(5, 4, 1), new Pos3d(7, 7, 2))));
    }
}