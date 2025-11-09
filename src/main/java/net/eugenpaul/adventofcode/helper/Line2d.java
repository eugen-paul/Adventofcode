package net.eugenpaul.adventofcode.helper;

import org.apache.commons.math3.linear.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Line2d {

    private final long pointX;
    private final long pointY;

    private final long deltaX;
    private final long deltaY;

    public static Line2d fromPointPoint(SimplePos a, SimplePos b) {
        return new Line2d(//
                a.getX(), //
                a.getY(), //
                b.getX() - a.getX(), //
                b.getY() - a.getY() //
        );
    }

    public static Line2d fromPointVector(SimplePos a, SimplePos v) {
        return new Line2d(//
                a.getX(), //
                a.getY(), //
                v.getX(), //
                v.getY() //
        );
    }

    public boolean isPointOnLine(SimplePos p) {
        long cross = (p.getY() - pointY) * deltaX - (p.getX() - pointX) * deltaY;
        return cross == 0;
    }

    public boolean isIntersecting(Line2d other) {
        return deltaX * other.deltaY != deltaY * other.deltaX;
    }

    public SimplePos intersection(Line2d other) {
        double[][] coefficients = { //
                { deltaX, -other.deltaX }, //
                { deltaY, -other.deltaY } //
        };
        double[] constants = { //
                other.pointX - pointX, //
                other.pointY - pointY //
        };

        RealMatrix matrixA = new Array2DRowRealMatrix(coefficients);
        RealVector vectorB = new ArrayRealVector(constants);

        // Loesen
        DecompositionSolver solver = new LUDecomposition(matrixA).getSolver();

        try {
            RealVector solution = solver.solve(vectorB);
            int ix = (int) (pointX + Math.round(solution.getEntry(0)) * deltaX);
            int iy = (int) (pointY + Math.round(solution.getEntry(0)) * deltaY);

            return new SimplePos(ix, iy);
        } catch (SingularMatrixException e) {
            return null;
        }
    }
}
