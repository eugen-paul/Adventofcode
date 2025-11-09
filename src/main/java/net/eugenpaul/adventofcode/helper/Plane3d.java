package net.eugenpaul.adventofcode.helper;

import org.apache.commons.math3.linear.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Plane3d {

    private final long pointX;
    private final long pointY;
    private final long pointZ;

    private final long deltaX1;
    private final long deltaY1;
    private final long deltaZ1;

    private final long deltaX2;
    private final long deltaY2;
    private final long deltaZ2;

    public static Plane3d fromPPP(Pos3d a, Pos3d b, Pos3d c) {
        return new Plane3d(//
                a.getX(), //
                a.getY(), //
                a.getZ(), //
                b.getX() - a.getX(), //
                b.getY() - a.getY(), //
                b.getZ() - a.getZ(), //
                c.getX() - a.getX(), //
                c.getY() - a.getY(), //
                c.getZ() - a.getZ() //
        );
    }

    public static Plane3d fromPVV(Pos3d a, Pos3d v1, Pos3d v2) {
        return new Plane3d(//
                a.getX(), //
                a.getY(), //
                a.getZ(), //
                v1.getX(), //
                v1.getY(), //
                v1.getZ(), //
                v2.getX(), //
                v2.getY(), //
                v2.getZ() //
        );
    }

    public static Plane3d fromPointLine(Pos3d a, Line3d l) {
        return new Plane3d(//
                a.getX(), //
                a.getY(), //
                a.getZ(), //
                l.getPointX() - a.getX(), //
                l.getPointY() - a.getY(), //
                l.getPointZ() - a.getZ(), //
                l.getDeltaX(), //
                l.getDeltaY(), //
                l.getDeltaZ() //
        );
    }

    public boolean isPointOnPlane(Pos3d p) {
        long crossX = (p.getY() - pointY) * deltaZ1 - (p.getZ() - pointZ) * deltaY1;
        long crossY = (p.getZ() - pointZ) * deltaX1 - (p.getX() - pointX) * deltaZ1;
        long crossZ = (p.getX() - pointX) * deltaY1 - (p.getY() - pointY) * deltaX1;

        long dot = crossX * deltaX2 + crossY * deltaY2 + crossZ * deltaZ2;
        return dot == 0;
    }

    public boolean isIntersecting(Line3d other) {
        // A plane and a line intersect if the line is not parallel to the plane.
        long crossX = deltaY1 * other.getDeltaZ() - deltaZ1 * other.getDeltaY();
        long crossY = deltaZ1 * other.getDeltaX() - deltaX1 * other.getDeltaZ();
        long crossZ = deltaX1 * other.getDeltaY() - deltaY1 * other.getDeltaX();

        long dot = crossX * deltaX2 + crossY * deltaY2 + crossZ * deltaZ2;
        return dot != 0;
    }

    public boolean isLineOnPlane(Line3d other) {
        return isPointOnPlane(new Pos3d(other.getPointX(), other.getPointY(), other.getPointZ())) //
                && isPointOnPlane(new Pos3d(//
                        other.getPointX() + other.getDeltaX(), //
                        other.getPointY() + other.getDeltaY(), //
                        other.getPointZ() + other.getDeltaZ()) //
                );
    }

    public Pos3d intersection(Line3d other) {
        double[][] coefficients = { //
                { deltaX1, deltaX2, -other.getDeltaX() }, //
                { deltaY1, deltaY2, -other.getDeltaY() }, //
                { deltaZ1, deltaZ2, -other.getDeltaZ() } //
        };
        double[] constants = { //
                other.getPointX() - pointX, //
                other.getPointY() - pointY, //
                other.getPointZ() - pointZ //
        };

        RealMatrix matrixA = new Array2DRowRealMatrix(coefficients);
        RealVector vectorB = new ArrayRealVector(constants);

        // Loesen
        DecompositionSolver solver = new LUDecomposition(matrixA).getSolver();

        try {
            RealVector solution = solver.solve(vectorB);
            int ix = (int) (pointX + Math.round(solution.getEntry(0)) * deltaX1 + Math.round(solution.getEntry(1)) * deltaX2);
            int iy = (int) (pointY + Math.round(solution.getEntry(0)) * deltaY1 + Math.round(solution.getEntry(1)) * deltaY2);
            int iz = (int) (pointZ + Math.round(solution.getEntry(0)) * deltaZ1 + Math.round(solution.getEntry(1)) * deltaZ2);

            return new Pos3d(ix, iy, iz);
        } catch (SingularMatrixException e) {
            return null;
        }
    }
}
