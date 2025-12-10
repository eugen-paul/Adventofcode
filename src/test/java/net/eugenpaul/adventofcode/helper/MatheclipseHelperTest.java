package net.eugenpaul.adventofcode.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

class MatheclipseHelperTest {

    @Test
    void testSolve() {
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
    void TT() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("x1 + x5 == 5");
        helper.addEquation("x2 + x3 + x4 == 4");
        helper.addEquation("x0 + x1 + x3 == 7");
        helper.addEquation("x4 + x5 == 3");
        helper.addEquation("x0 + x1 + x2 + x3 + x4 + x5 == 10");
        // helper.addEquation("x0 >= 0");
        // helper.addEquation("x1 >= 0");
        // helper.addEquation("x2 >= 0");
        // helper.addEquation("x3 >= 0");
        // helper.addEquation("x4 >= 0");
        // helper.addEquation("x5 >= 0");
        helper.addUnknown("x0");
        helper.addUnknown("x1");
        helper.addUnknown("x2");
        helper.addUnknown("x3");
        helper.addUnknown("x4");
        helper.addUnknown("x5");
        helper.solve();
        System.out.println(helper.getRawResult());
        System.out.println(helper.getResults("x0"));
        System.out.println(helper.getResults("x1"));
        System.out.println(helper.getResults("x2"));
        System.out.println(helper.getResults("x3"));
        System.out.println(helper.getResults("x4"));
        System.out.println(helper.getResults("x5"));
    }

    @Test
    void TT2() {
        MatheclipseHelper helper = new MatheclipseHelper();
        double[][] coefficients = { 
            {0, 1, 0, 0, 0, 1}, 
            {0, 0, 1, 1, 1, 0}, 
            {1, 1, 0, 1, 0, 0}, 
            {0, 0, 0, 0, 1, 1}, 
            {1, 1, 1, 1, 1, 1}, 
        };
        double[]   constants = { 5, 4, 5, 6, 10 };

        RealMatrix matrixA = new Array2DRowRealMatrix(coefficients);
        RealVector vectorB = new ArrayRealVector(constants);

        // Lösen
        DecompositionSolver solver = new LUDecomposition(matrixA).getSolver();
        RealVector solution = solver.solve(vectorB);
        System.out.println(solution.toString());
    }

    @Test
    void testSolve2() {
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
    void testSolve3() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("x * x - 5 * x - 33 == 0");
        helper.addUnknown("x");
        helper.solve();
        var r = helper.getResults("x");

        assertEquals(2, r.size());
        assertEquals("1/2*(5-Sqrt(157))", r.get(0));
        assertEquals("1/2*(5+Sqrt(157))", r.get(1));
    }

    @Test
    void testNSolve1() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("x * x - 5 * x - 33 == 0");
        helper.addUnknown("x");
        helper.nSolve();
        var r = helper.getResults("x");

        assertEquals(2, r.size());
        assertEquals("-3.764982043070834", r.get(0));
        assertEquals("8.764982043070834", r.get(1));
    }

    @Test
    void testNSolve2() {
        MatheclipseHelper helper = new MatheclipseHelper();
        helper.addEquation("x^2 == 2");
        helper.addUnknown("x");
        helper.nSolve();
        var r = helper.getResults("x");

        assertEquals(2, r.size());
        assertEquals("-1.4142135623730951", r.get(0));
        assertEquals("1.4142135623730951", r.get(1));
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

    @Test
    void testNumerischeAuswertung1() {
        Double r = MatheclipseHelper.numerischD("Pi");
        assertEquals(3.14159, r, 0.001);
    }

    @Test
    void testNumerischeAuswertung2() {
        Double r = MatheclipseHelper.numerischD("1/2*(5-Sqrt(157))");
        assertEquals(-3.76498, r, 0.00001);
    }

    @Test
    void testNumerischeAuswertung3() {
        Long r = MatheclipseHelper.numerischL("1/2*(5-Sqrt(157))");
        assertEquals(-3L, r);
    }
}
