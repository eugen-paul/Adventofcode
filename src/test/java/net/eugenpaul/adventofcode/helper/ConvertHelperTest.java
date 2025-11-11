package net.eugenpaul.adventofcode.helper;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class ConvertHelperTest {

    @Test
    void testAsListList() {
        List<String> full = List.of(
            "123", //
            "321", //
            "", //
            "abc", //
            "", //
            "", //
            "456" //
        );

        List<List<String>> data = ConvertHelper.asListList(full);

        assertEquals(List.of("123", "321"), data.get(0));
        assertEquals(List.of("abc"), data.get(1));
        assertEquals(List.of("456"), data.get(2));
    }

    @Test
    void testToInt() {
        assertEquals(123, toInt("123"));
        assertEquals(0, toInt("0"));
        assertEquals(-34, toInt("-34"));
    }
    
    @Test
    void testToInt2() {
        assertEquals(1, toInt('1'));
        assertEquals(0, toInt('0'));
    }
    
    @Test
    void testToLong() {
        assertEquals(444444444444L, toLong("444444444444"));
        assertEquals(0, toLong("0"));
        assertEquals(-1444444444444L, toLong("-1444444444444"));
    }
    
    @Test
    void testToLong2() {
        assertEquals(1, toLong('1'));
        assertEquals(0, toLong('0'));
    }
}
