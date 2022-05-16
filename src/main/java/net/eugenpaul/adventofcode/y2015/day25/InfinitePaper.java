package net.eugenpaul.adventofcode.y2015.day25;

import lombok.AllArgsConstructor;
import net.eugenpaul.adventofcode.helper.MathHelper;

@AllArgsConstructor
public class InfinitePaper {

    private long firstNumber;
    private long multiplier;
    private long modulo;

    /**
     * Element(n+1) = Element(n)*multiplier mod modulo
     * <p>
     * OR
     * <p>
     * Element(n+1) = Element(1) * (multiplier to the n-1) mod modulo
     * 
     * @param posX
     * @param posY
     * @return
     */
    public long getElement(long posX, long posY) {
        long mod = MathHelper.modularExponentiation(multiplier, getElementNumberPosition(posX, posY) - 1, modulo);
        return (firstNumber * mod) % modulo;
    }

    /**
     * <pre>
     * Number of Elements of InfinitePaper:
     *    |  1   2   3   4   5   6 <- Y
     * ---+---+---+---+---+---+---+
     *  1 |  1   3   6  10  15  21
     *  2 |  2   5   9  14  20
     *  3 |  4   8  13  19
     *  4 |  7  12  18
     *  5 | 11  17
     *  6 | 16
     *  ^ 
     *  X
     * </pre>
     * 
     * Let N has Coordinates (X,Y). Then Numder of N is sum of:
     * <p>
     * - sum from 1 to (X-2) OR (x-2)*(x-1) / 2
     * <p>
     * - sum from 1 to (Y-1) OR (y-1)*y / 2
     * <p>
     * - X * Y
     * <p>
     * 
     * Example: N = (4,3) Number of N = sum(1, (4-2)) + sum (1, (3-1)) + 4*3 = 3 + 3 + 12 = 18
     * <p>
     * OR
     * <p>
     * Number of N = (4-2)*(4-1)/2 + (3-1)*3 + 4 * 3 = 3 + 3 + 12 = 18
     * 
     * @param posX
     * @param posY
     * @return Number of Element (posX, posY)
     */
    public static long getElementNumberPosition(long posX, long posY) {
        long a = (posX - 2) * (posX - 1) / 2;
        long b = (posY - 1) * posY / 2;
        long c = posX * posY;
        return a + b + c;
    }

}
