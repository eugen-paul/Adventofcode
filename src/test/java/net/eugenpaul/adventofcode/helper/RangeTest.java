package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
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

    @Test
    void testMergeListAoc2025d5() {
        Range a1 = new Range(3, 5);
        Range a2 = new Range(10, 14);
        Range a3 = new Range(16, 20);
        Range a4 = new Range(12, 18);

        List<Range> all = new ArrayList<>();
        for (var r : List.of(a1, a2, a3, a4)) {
            all = Range.merge(all, r);
        }

        assertEquals(2, all.size());
        assertEquals(1, all.stream().filter(v -> v.equals(new Range(3, 5))).count());
        assertEquals(1, all.stream().filter(v -> v.equals(new Range(10, 20))).count());
    }

    @Test
    void testMergeListAdjacentRanges() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(11, 20);
        Range b = new Range(5, 15);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 20))).count());
    }

    @Test
    void testMergeListMultipleOverlaps() {
        Range a1 = new Range(0, 5);
        Range a2 = new Range(10, 15);
        Range a3 = new Range(20, 25);
        Range b = new Range(3, 22);

        var merge = Range.merge(List.of(a1, a2, a3), b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 25))).count());
    }

    @Test
    void testMergeListEmptyList() {
        Range b = new Range(5, 15);

        var merge = Range.merge(List.of(), b);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(5, 15))).count());
    }

    @Test
    void testMergeListBelowAll() {
        Range a1 = new Range(20, 30);
        Range a2 = new Range(40, 50);
        Range b = new Range(0, 10);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(3, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(20, 30))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(40, 50))).count());
    }

    @Test
    void testMergeListAboveAll() {
        Range a1 = new Range(0, 10);
        Range a2 = new Range(20, 30);
        Range b = new Range(50, 60);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(3, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(20, 30))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(50, 60))).count());
    }

    @Test
    void testMergeListPartialOverlapFirst() {
        Range a1 = new Range(0, 15);
        Range a2 = new Range(25, 30);
        Range b = new Range(10, 20);

        var merge = Range.merge(List.of(a1, a2), b);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 20))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(25, 30))).count());
    }
    
    @Test
    void testContainPointInside() {
        Range a = new Range(0, 10);
        
        assertEquals(true, a.contain(0));
        assertEquals(true, a.contain(5));
        assertEquals(true, a.contain(10));
    }

    @Test
    void testContainPointOutside() {
        Range a = new Range(0, 10);
        
        assertEquals(false, a.contain(-1));
        assertEquals(false, a.contain(11));
        assertEquals(false, a.contain(100));
    }

    @Test
    void testContainPointBoundary() {
        Range a = new Range(5, 15);
        
        assertEquals(true, a.contain(5));
        assertEquals(true, a.contain(15));
        assertEquals(false, a.contain(4));
        assertEquals(false, a.contain(16));
    }

    @Test
    void testContainPointNegativeRange() {
        Range a = new Range(-10, -5);
        
        assertEquals(true, a.contain(-10));
        assertEquals(true, a.contain(-7));
        assertEquals(true, a.contain(-5));
        assertEquals(false, a.contain(-11));
        assertEquals(false, a.contain(-4));
    }

    @Test
    void testContainPointSingleElementRange() {
        Range a = new Range(5, 5);
        
        assertEquals(true, a.contain(5));
        assertEquals(false, a.contain(4));
        assertEquals(false, a.contain(6));
    }

    @Test
    void testNoOverlaps(){
        List<Range> all = List.of(
            new Range(0, 10), 
            new Range(20, 30),
            new Range(50, 60)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(3, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(20, 30))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(50, 60))).count());
    }

    @Test
    void testAllSame(){
        List<Range> all = List.of(
            new Range(0, 10), 
            new Range(0, 10),
            new Range(0, 10)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 10))).count());
    }

    @Test
    void testAllOverlaps(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(0, 12),
            new Range(18, 30)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 30))).count());
    }

    @Test
    void testAllOverlaps2(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(0, 10),
            new Range(20, 30)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 30))).count());
    }

    @Test
    void testTwoOverlapsAndThird(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(0, 12),
            new Range(25, 30)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 20))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(25, 30))).count());
    }

    @Test
    void testTwoOverlaps(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(0, 12),
            new Range(30, 50),
            new Range(25, 30)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(2, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 20))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(25, 50))).count());
    }

    @Test
    void testOneContainsOther(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(5, 12),
            new Range(0, 30)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(0, 30))).count());
    }

    @Test
    void testTwoTouch(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(21, 30),
            new Range(31, 40)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(1, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(10, 40))).count());
    }

    @Test
    void testDontTouch(){
        List<Range> all = List.of(
            new Range(10, 20), 
            new Range(22, 30),
            new Range(32, 40)
        );

        List<Range> merge = Range.merge(all);
        assertNotNull(merge);
        assertEquals(3, merge.size());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(10, 20))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(22, 30))).count());
        assertEquals(1, merge.stream().filter(v -> v.equals(new Range(32, 40))).count());
    }
}