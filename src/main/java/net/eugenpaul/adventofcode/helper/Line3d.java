package net.eugenpaul.adventofcode.helper;

import org.apache.commons.math3.linear.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Line3d {

    private final long pointX;
    private final long pointY;
    private final long pointZ;

    private final long deltaX;
    private final long deltaY;
    private final long deltaZ;

    public static Line3d fromPointPoint(Pos3d a, Pos3d b) {
        return new Line3d(//
                a.getX(), //
                a.getY(), //
                a.getZ(), //
                b.getX() - a.getX(), //
                b.getY() - a.getY(), //
                b.getZ() - a.getZ() //
        );
    }

    public static Line3d fromPointVector(Pos3d a, Pos3d v) {
        return new Line3d(//
                a.getX(), //
                a.getY(), //
                a.getZ(), //
                v.getX(), //
                v.getY(), //
                v.getZ() //
        );
    }

    public boolean isPointOnLine(Pos3d p) {
        long crossX = (p.getY() - pointY) * deltaZ - (p.getZ() - pointZ) * deltaY;
        long crossY = (p.getZ() - pointZ) * deltaX - (p.getX() - pointX) * deltaZ;
        long crossZ = (p.getX() - pointX) * deltaY - (p.getY() - pointY) * deltaX;
        return crossX == 0 && crossY == 0 && crossZ == 0;
    }

    public boolean isIntersecting(Line3d other) {
        // Two lines in 3D intersect if they are not parallel and the shortest distance between them is zero.
        long crossX = deltaY * other.deltaZ - deltaZ * other.deltaY;
        long crossY = deltaZ * other.deltaX - deltaX * other.deltaZ;
        long crossZ = deltaX * other.deltaY - deltaY * other.deltaX;
        return !(crossX == 0 && crossY == 0 && crossZ == 0);
    }

    public Pos3d intersection(Line3d other) {
        double[][] coefficients = { //
                { deltaX, -other.deltaX }, //
                { deltaY, -other.deltaY }, //
                // { deltaZ, -other.deltaZ } //No need
        };
        double[] constants = { //
                other.pointX - pointX, //
                other.pointY - pointY, //
                // other.pointZ - pointZ //No need
        };

        RealMatrix matrixA = new Array2DRowRealMatrix(coefficients);
        RealVector vectorB = new ArrayRealVector(constants);

        // Loesen
        DecompositionSolver solver = new LUDecomposition(matrixA).getSolver();

        try {
            RealVector solution = solver.solve(vectorB);
            int ix = (int) (pointX + Math.round(solution.getEntry(0)) * deltaX);
            int iy = (int) (pointY + Math.round(solution.getEntry(0)) * deltaY);
            int iz = (int) (pointZ + Math.round(solution.getEntry(0)) * deltaZ);

            return new Pos3d(ix, iy, iz);
        } catch (SingularMatrixException e) {
            return null;
        }
    }
}
