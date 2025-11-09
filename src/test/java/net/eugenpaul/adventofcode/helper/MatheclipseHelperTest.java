package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MatheclipseHelperTest {

    @Test
    void testLinear() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("x + y == 10");
        helper.addEquation("x - y == 4");
        helper.addUnknown("x");
        helper.addUnknown("y");
        helper.solve();
        Long x = helper.getResult("x");
        Long y = helper.getResult("y");

        assertEquals(7, x);
        assertEquals(3, y);
    }

    @Test
    void testLinear2() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("t1 * dx0 + x == t1 * 64   + 232488932265751");
        helper.addEquation("t1 * dy0 + y == t1 * 273  + 93844132799095 ");
        helper.addEquation("t1 * dz0 + z == t1 * 119  + 203172424390144");
        helper.addEquation("t2 * dx0 + x == t2 * 14   + 258285813391475");
        helper.addEquation("t2 * dy0 + y == t2 * -10  + 225317967801013");
        helper.addEquation("t2 * dz0 + z == t2 * -22  + 306162724914014");
        helper.addEquation("t3 * dx0 + x == t3 * -182 + 377519381672953");
        helper.addEquation("t3 * dy0 + y == t3 * -80  + 343737262245611");
        helper.addEquation("t3 * dz0 + z == t3 * -373 + 485395777725108");
        helper.addEquation("t4 * dx0 + x == t4 * -183 + 365097086044914");
        helper.addEquation("t4 * dy0 + y == t4 * -200 + 416361503527803");
        helper.addEquation("t4 * dz0 + z == t4 * 23   + 264542514358904");
        helper.addEquation("t5 * dx0 + x == t5 * 174  + 167137216456431");
        helper.addEquation("t5 * dy0 + y == t5 * 112  + 212273771288373");
        helper.addEquation("t5 * dz0 + z == t5 * 27   + 263210959191120");
        helper.addEquation("x + y + z    == res");

        helper.addUnknown("x,y,z,dx0,dy0,dz0,t1,t2,t3,t4,t5, res");
        helper.solve();
        Long r = helper.getResult("res");

        assertEquals(999782576459892L, r);
    }

    @Test
    void testSimplifyOne() {
        MatheclipseHelper helper = new MatheclipseHelper();

        helper.addEquation("2 * x + 4 * y + 7 * x * 2 + 10 - 9");

        String r = helper.simplify();

        assertEquals("{1+16*x+4*y}", r);
    }

    @Test
    void testSimplifyMulti() {
        MatheclipseHelper helper = new MatheclipseHelper();

        helper.addEquation("2 * x + 4 * y + 7 * x * 2 + 10 - 9");
        helper.addEquation("(x + y + 1) * 2 + x * 3 - 4");

        String r = helper.simplify();

        assertEquals("{1+16*x+4*y,-2+5*x+2*y}", r);
    }
}
