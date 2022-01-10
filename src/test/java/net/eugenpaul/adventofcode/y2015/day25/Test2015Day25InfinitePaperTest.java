package net.eugenpaul.adventofcode.y2015.day25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test2015Day25
 */
class Test2015Day25InfinitePaperTest {

    @Test
    void test2015Day25InfinitePaper_getNumber() {
        // assertEquals(1L, InfinitePaper.getElementNumberPosition(1, 1));
        assertEquals(2L, InfinitePaper.getElementNumberPosition(2, 1));
        assertEquals(3L, InfinitePaper.getElementNumberPosition(1, 2));
        assertEquals(4L, InfinitePaper.getElementNumberPosition(3, 1));
        assertEquals(5L, InfinitePaper.getElementNumberPosition(2, 2));
        assertEquals(6L, InfinitePaper.getElementNumberPosition(1, 3));
        assertEquals(7L, InfinitePaper.getElementNumberPosition(4, 1));
        assertEquals(8L, InfinitePaper.getElementNumberPosition(3, 2));
        assertEquals(9L, InfinitePaper.getElementNumberPosition(2, 3));
        assertEquals(10L, InfinitePaper.getElementNumberPosition(1, 4));
        assertEquals(11L, InfinitePaper.getElementNumberPosition(5, 1));
        assertEquals(12L, InfinitePaper.getElementNumberPosition(4, 2));
        assertEquals(13L, InfinitePaper.getElementNumberPosition(3, 3));
        assertEquals(14L, InfinitePaper.getElementNumberPosition(2, 4));
        assertEquals(15L, InfinitePaper.getElementNumberPosition(1, 5));
        assertEquals(16L, InfinitePaper.getElementNumberPosition(6, 1));
        assertEquals(17L, InfinitePaper.getElementNumberPosition(5, 2));
        assertEquals(18L, InfinitePaper.getElementNumberPosition(4, 3));
        assertEquals(19L, InfinitePaper.getElementNumberPosition(3, 4));
        assertEquals(20L, InfinitePaper.getElementNumberPosition(2, 5));
        assertEquals(21L, InfinitePaper.getElementNumberPosition(1, 6));
    }

    @Test
    void test2015Day25InfinitePaper_getModularExponentiation() {
        assertEquals(64L, InfinitePaper.modularExponentiation(2, 6, 100));
        assertEquals(28L, InfinitePaper.modularExponentiation(2, 7, 100));
    }

    @Test
    void test2015Day25InfinitePaper_getElement() {
        InfinitePaper paper = new InfinitePaper(20151125L, 252533L, 33554393L);
        assertEquals(31916031L, paper.getElement(2, 1));
        assertEquals(18749137, paper.getElement(1, 2));
    }

}