package net.eugenpaul.adventofcode.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class AreaTest {

    @Test
    void testConstructorAndGetters() {
        Area area = new Area(1, 2, 3, 4);
        assertEquals(1, area.getX());
        assertEquals(2, area.getY());
        assertEquals(3, area.getWidth());
        assertEquals(4, area.getHeight());
    }

    @Test
    void testConstructorWithSimplePos() {
        SimplePos a = new SimplePos(1, 2);
        SimplePos b = new SimplePos(3, 5);
        Area area = new Area(a, b);
        assertEquals(1, area.getX());
        assertEquals(2, area.getY());
        assertEquals(3, area.getWidth());
        assertEquals(4, area.getHeight());
    }

    @Test
    void testFromPP() {
        SimplePos a = new SimplePos(2, 3);
        SimplePos b = new SimplePos(4, 6);
        Area area = Area.fromPP(a, b);
        assertEquals(2, area.getX());
        assertEquals(3, area.getY());
        assertEquals(3, area.getWidth());
        assertEquals(4, area.getHeight());
    }

    @Test
    void testFromPPOpt1() {
        SimplePos a = new SimplePos(5, 7);
        SimplePos b = new SimplePos(2, 3);
        Area area = Area.fromPPOpt(a, b);
        assertEquals(2, area.getX());
        assertEquals(3, area.getY());
        assertEquals(4, area.getWidth());
        assertEquals(5, area.getHeight());
    }

    @Test
    void testFromPPOpt2() {
        SimplePos a = new SimplePos(2, 3);
        SimplePos b = new SimplePos(5, 7);
        Area area = Area.fromPPOpt(a, b);
        assertEquals(2, area.getX());
        assertEquals(3, area.getY());
        assertEquals(4, area.getWidth());
        assertEquals(5, area.getHeight());
    }

    @Test
    void testFromPPOpt3() {
        SimplePos a = new SimplePos(2, 7);
        SimplePos b = new SimplePos(5, 3);
        Area area = Area.fromPPOpt(a, b);
        assertEquals(2, area.getX());
        assertEquals(3, area.getY());
        assertEquals(4, area.getWidth());
        assertEquals(5, area.getHeight());
    }

    @Test
    void testFromPPOpt4() {
        SimplePos a = new SimplePos(5, 3);
        SimplePos b = new SimplePos(2, 7);
        Area area = Area.fromPPOpt(a, b);
        assertEquals(2, area.getX());
        assertEquals(3, area.getY());
        assertEquals(4, area.getWidth());
        assertEquals(5, area.getHeight());
    }

    @Test
    void testIstEinPunkt() {
        Area area = new Area(1, 1, 1, 1);
        assertTrue(area.istEinPunkt());
        Area area2 = new Area(1, 1, 2, 1);
        assertFalse(area2.istEinPunkt());
        Area area3 = new Area(1, 1, 1, 2);
        assertFalse(area3.istEinPunkt());
    }

    @Test
    void testGetPosMethods() {
        Area area = new Area(2, 3, 4, 5);
        assertEquals(new SimplePos(2, 3), area.getPos());
        assertEquals(new SimplePos(2, 3), area.getPosUpLeft());
        assertEquals(new SimplePos(5, 3), area.getPosUpRight());
        assertEquals(new SimplePos(2, 7), area.getPosDownLeft());
        assertEquals(new SimplePos(5, 7), area.getPosDownRight());
    }

    @Test
    void testForEach() {
        Area area = new Area(1, 2, 2, 2);
        Set<String> visited = new HashSet<>();
        area.forEach((x, y) -> visited.add(x + "," + y));
        assertEquals(Set.of("1,2", "1,3", "2,2", "2,3"), visited);
    }

    @Test
    void testForEachWithBreak() {
        Area area = new Area(0, 0, 3, 3);
        AtomicInteger count = new AtomicInteger(0);
        area.forEach((x, y) -> count.incrementAndGet(), () -> count.get() >= 2);
        assertEquals(2, count.get());
    }

    @Test
    void testCopy() {
        Area area = new Area(1, 2, 3, 4);
        Area copy = area.copy();
        assertEquals(area, copy);
        assertNotSame(area, copy);
    }

    @Test
    void testDivideSinglePoint() {
        Area area = new Area(1, 1, 1, 1);
        List<Area> parts = area.divide();
        assertEquals(1, parts.size());
        assertEquals(area, parts.get(0));
    }

    @Test
    void testDivideVertical() {
        Area area = new Area(0, 0, 1, 4);
        List<Area> parts = area.divide();
        assertEquals(2, parts.size());
        assertEquals(new Area(0, 0, 1, 2), parts.get(0));
        assertEquals(new Area(0, 2, 1, 2), parts.get(1));
    }

    @Test
    void testDivideHorizontal() {
        Area area = new Area(0, 0, 4, 1);
        List<Area> parts = area.divide();
        assertEquals(2, parts.size());
        assertEquals(new Area(0, 0, 2, 1), parts.get(0));
        assertEquals(new Area(2, 0, 2, 1), parts.get(1));
    }

    @Test
    void testDivideRectangle() {
        Area area = new Area(0, 0, 4, 4);
        List<Area> parts = area.divide();
        assertEquals(4, parts.size());
        assertEquals(new Area(0, 0, 2, 2), parts.get(0));
        assertEquals(new Area(2, 0, 2, 2), parts.get(1));
        assertEquals(new Area(0, 2, 2, 2), parts.get(2));
        assertEquals(new Area(2, 2, 2, 2), parts.get(3));
    }

    @Test
    void overlap_nonOverlapping_returnsNull() {
        Area a = new Area(0, 0, 2, 2); // covers (0..1,0..1)
        Area b = new Area(2, 2, 2, 2); // covers (2..3,2..3) -> no overlap
        assertNull(Area.overlap(a, b));
        assertNull(a.overlap(b));
    }

    @Test
    void overlap_contained_returnsInner() {
        Area outer = new Area(0, 0, 5, 5);
        Area inner = new Area(1, 1, 2, 2);
        Area ov = Area.overlap(outer, inner);
        assertNotNull(ov);
        assertEquals(inner.getX(), ov.getX());
        assertEquals(inner.getY(), ov.getY());
        assertEquals(inner.getWidth(), ov.getWidth());
        assertEquals(inner.getHeight(), ov.getHeight());
    }

    @Test
    void overlap_partialOverlap_returnsExpected() {
        Area a = new Area(0, 0, 4, 4); // (0..3,0..3)
        Area b = new Area(2, 1, 4, 3); // (2..5,1..3)
        Area ov = Area.overlap(a, b); // expected (2..3,1..3) => x=2,y=1,width=2,height=3
        assertNotNull(ov);
        assertEquals(2, ov.getX());
        assertEquals(1, ov.getY());
        assertEquals(2, ov.getWidth());
        assertEquals(3, ov.getHeight());
    }

    @Test
    void overlap_touchingEdge_returnsSingleLineOrPoint() {
        // touching at x == 2 should produce overlap (column at x==2)
        Area a = new Area(0, 0, 3, 2); // x:0..2, y:0..1
        Area b = new Area(2, 1, 2, 3); // x:2..3, y:1..3
        Area ov = Area.overlap(a, b); // expected x=2, y=1, width=1, height=1
        assertNotNull(ov);
        assertEquals(2, ov.getX());
        assertEquals(1, ov.getY());
        assertEquals(1, ov.getWidth());
        assertEquals(1, ov.getHeight());
    }

    @Test
    void subtract_noOverlap_returnsOriginal() {
        Area a = new Area(0, 0, 3, 3);
        Area b = new Area(4, 4, 1, 1);
        List<Area> res = a.subtract(b);
        assertEquals(1, res.size());
        assertEquals(a, res.get(0));
    }

    @Test
    void subtract_completeOverlap_returnsEmpty1() {
        Area a = new Area(0, 0, 2, 2);
        Area b = new Area(0, 0, 2, 2);
        List<Area> res = a.subtract(b);
        assertTrue(res.isEmpty());
    }

    @Test
    void subtract_centerOverlap_returnsFourPieces1() {
        Area a = new Area(0, 0, 4, 4);
        Area b = new Area(1, 1, 2, 2);
        List<Area> res = a.subtract(b);

        assertEquals(4, res.size());
        // expected order: top, bottom, left middle, right middle (matches implementation)
        assertEquals(new Area(0, 0, 4, 1), res.get(0)); // top
        assertEquals(new Area(0, 3, 4, 1), res.get(1)); // bottom
        assertEquals(new Area(0, 1, 1, 2), res.get(2)); // left middle
        assertEquals(new Area(3, 1, 1, 2), res.get(3)); // right middle
    }

    @Test
    void contains_nullOther_returnsFalse() {
        Area a = new Area(0, 0, 3, 3);
        assertFalse(a.contains(null));
    }

    @Test
    void contains_selfAndEqual_returnsTrue() {
        Area a = new Area(1, 2, 3, 4);
        assertTrue(a.contains(a));
        Area b = new Area(1, 2, 3, 4);
        assertTrue(a.contains(b));
    }

    @Test
    void contains_strictContainment_returnsTrue() {
        Area outer = new Area(0, 0, 5, 5); // covers 0..4 x 0..4
        Area inner = new Area(1, 1, 3, 3); // 1..3 inside
        assertTrue(outer.contains(inner));
        assertTrue(Area.contains(outer, inner));
    }

    @Test
    void contains_touchingBorder_returnsTrue() {
        Area outer = new Area(0, 0, 3, 3); // covers 0..2 x 0..2
        Area inner = new Area(2, 2, 1, 1); // single point at corner inside
        assertTrue(outer.contains(inner));
    }

    @Test
    void contains_partialOverlapOrOutside_returnsFalse() {
        Area outer = new Area(0, 0, 3, 3); // 0..2
        Area overlapping = new Area(2, 2, 3, 3); // 2..4 extends outside
        Area disjoint = new Area(3, 3, 1, 1); // 3..3 outside
        assertFalse(outer.contains(overlapping));
        assertFalse(outer.contains(disjoint));
    }

    @Test
    void merge_disjoint_areas() {
        Area a = new Area(0, 0, 3, 3);
        Area b = new Area(4, 0, 3, 3);

        List<Area> merge = Area.merge(a, b);
        assertEquals(2, merge.size());
        assertEquals(a, merge.get(0));
        assertEquals(b, merge.get(1));
    }

    @Test
    void merge_contains1() {
        Area a = new Area(0, 0, 10, 10);
        Area b = new Area(4, 0, 3, 3);

        List<Area> merge = Area.merge(a, b);
        assertEquals(1, merge.size());
        assertEquals(a, merge.get(0));
    }

    @Test
    void merge_contains2() {
        Area a = new Area(4, 0, 3, 3);
        Area b = new Area(0, 0, 10, 10);

        List<Area> merge = Area.merge(a, b);
        assertEquals(1, merge.size());
        assertEquals(b, merge.get(0));
    }

    @Test
    void merge_overlap() {
        Area a = new Area(0, 0, 4, 4);
        Area b = new Area(1, 1, 4, 4);

        List<Area> merge = Area.merge(a, b);
        assertEquals(3, merge.size());
        assertEquals(a, merge.get(0));
        assertTrue(merge.contains(new Area(1, 4, 4, 1)));
        assertTrue(merge.contains(new Area(4, 1, 1, 3)));
    }
}