package net.eugenpaul.adventofcode.y2016.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DragonCurveTest {
    @Test
    void testDoDragonCurve() {
        assertEquals("100", DragonCurve.doDragonCurve("1"));
        assertEquals("001", DragonCurve.doDragonCurve("0"));
        assertEquals("11111000000", DragonCurve.doDragonCurve("11111"));
        assertEquals("1111000010100101011110000", DragonCurve.doDragonCurve("111100001010"));
    }
}
