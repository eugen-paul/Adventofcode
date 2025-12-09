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

    /**
     * Aendert Startpunkt so, dass die Deltas positiv sind.
     * 
     * @return response
     */
    public Line2d norm() {
        var x = pointX;
        var dx = deltaX;
        var y = pointY;
        var dy = deltaY;

        if (dx < 0) {
            x += dx;
            dx = -dx;
        }

        if (dy < 0) {
            y += dy;
            dy = -dy;
        }
        return new Line2d(x, y, dx, dy);
    }

    public boolean isPointOnLine(SimplePos p) {
        long cross = (p.getY() - pointY) * deltaX - (p.getX() - pointX) * deltaY;
        return cross == 0;
    }

    public boolean isPointOnSegment(SimplePos p) {
        if (!isPointOnLine(p)) {
            return false;
        }

        long minX = Math.min(pointX, pointX + deltaX);
        long maxX = Math.max(pointX, pointX + deltaX);
        long minY = Math.min(pointY, pointY + deltaY);
        long maxY = Math.max(pointY, pointY + deltaY);

        return p.getX() >= minX && p.getX() <= maxX && //
                p.getY() >= minY && p.getY() <= maxY;
    }

    /**
     * Prueft, ob die zwei (endlosen) Geraden sich schneiden.
     * 
     * @param other
     * @return
     */
    public boolean isIntersecting(Line2d other) {
        return deltaX * other.deltaY != deltaY * other.deltaX;
    }

    /**
     * Pruft, ob die zwei Abschnitte / Endliche Strecken sich schneiden
     * @param other
     * @return
     */
    public boolean isSegmentIntersecting(Line2d other) {
        if (!isIntersecting(other)) {
            return false;
        }

        SimplePos intersection = intersection(other);
        if (intersection == null) {
            return false;
        }

        // Check if intersection point is within both segments
        long minX1 = Math.min(pointX, pointX + deltaX);
        long maxX1 = Math.max(pointX, pointX + deltaX);
        long minY1 = Math.min(pointY, pointY + deltaY);
        long maxY1 = Math.max(pointY, pointY + deltaY);

        long minX2 = Math.min(other.pointX, other.pointX + other.deltaX);
        long maxX2 = Math.max(other.pointX, other.pointX + other.deltaX);
        long minY2 = Math.min(other.pointY, other.pointY + other.deltaY);
        long maxY2 = Math.max(other.pointY, other.pointY + other.deltaY);

        return intersection.getX() >= minX1 && intersection.getX() <= maxX1 && //
                intersection.getY() >= minY1 && intersection.getY() <= maxY1 && //
                intersection.getX() >= minX2 && intersection.getX() <= maxX2 && //
                intersection.getY() >= minY2 && intersection.getY() <= maxY2;
    }

    public SimplePos intersection(Line2d other) {
        // this is vertical and other is horizontal
        if(deltaX == 0 && other.deltaY == 0){
            return new SimplePos((int) pointX, (int) other.pointY);
        }
        
        // this is horizontal and other is vertical
        if(deltaY == 0 && other.deltaX == 0){
            return new SimplePos((int) other.pointX, (int) pointY);
        }

        // both are vertical (parallel, no intersection unless collinear)
        if(deltaX == 0 && other.deltaX == 0){
            return null;
        }
        
        // both are horizontal (parallel, no intersection unless collinear)
        if(deltaY == 0 && other.deltaY == 0){
            return null;
        }
        
        if (deltaX == 0) {
            // This line is vertical
            double t = (pointX - other.pointX) / (double) other.deltaX;
            int iy = (int) (other.pointY + Math.round(t) * other.deltaY);
            return new SimplePos((int) pointX, iy);
        }
        
        if (deltaY == 0) {
            // This line is horizontal
            double t = (pointY - other.pointY) / (double) other.deltaY;
            int ix = (int) (other.pointX + Math.round(t) * other.deltaX);
            return new SimplePos(ix, (int) pointY);
        }
        
        if (other.deltaX == 0) {
            // Other line is vertical
            double t = (other.pointX - pointX) / (double) deltaX;
            int iy = (int) (pointY + Math.round(t) * deltaY);
            return new SimplePos((int) other.pointX, iy);
        }
        
        if (other.deltaY == 0) {
            // Other line is horizontal
            double t = (other.pointY - pointY) / (double) deltaY;
            int ix = (int) (pointX + Math.round(t) * deltaX);
            return new SimplePos(ix, (int) other.pointY);
        }

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
