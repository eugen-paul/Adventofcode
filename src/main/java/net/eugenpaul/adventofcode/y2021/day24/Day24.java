package net.eugenpaul.adventofcode.y2021.day24;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {

    @Getter
    private long largestModelNumber;
    @Getter
    private long smallestModelNumber;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2021/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        largestModelNumber = doPuzzle1();
        smallestModelNumber = doPuzzle2();
        logger.log(Level.INFO, () -> "largestModelNumber  : " + getLargestModelNumber());
        logger.log(Level.INFO, () -> "smallestModelNumber  : " + getSmallestModelNumber());

        return true;
    }

    /**
     * Reduction of the largest number
     * 
     * <code>
     * 29989297949519
     * 2998  97949519
     * 299    7949519
     * 299      49519
     * 299        519
     * 29          19
     * 2            9
     * </code>
     * 
     * @return
     */
    private long doPuzzle1() {
        int[] modelNumber = new int[14];
        Arrays.fill(modelNumber, 9);
        modelNumber[0] = 2;
        modelNumber[1] = 9;
        modelNumber[2] = 9;
        modelNumber[3] = 8;
        modelNumber[4] = 9;
        modelNumber[5] = 2;
        modelNumber[6] = 9;
        modelNumber[7] = 7;
        modelNumber[8] = 9;
        modelNumber[9] = 4;
        modelNumber[10] = 9;
        modelNumber[11] = 5;
        modelNumber[12] = 1;
        modelNumber[13] = 9;

        if (doALU(modelNumber) == 0) {
            StringBuilder resp = new StringBuilder(14);
            for (int i : modelNumber) {
                resp.append(i);
            }
            return Long.parseLong(resp.toString());
        }

        return -1;
    }

    /**
     * Reduction of the smallest number
     * 
     * <code>
     * 19518121316118
     * 1951  21316118
     * 195    1316118
     * 195      16118
     * 195        118
     * 19          18
     * 1            8
     * </code>
     * 
     * @return
     */
    private long doPuzzle2() {
        int[] modelNumber = new int[14];
        Arrays.fill(modelNumber, 9);
        modelNumber[0] = 1;
        modelNumber[1] = 9;
        modelNumber[2] = 5;
        modelNumber[3] = 1;
        modelNumber[4] = 8;
        modelNumber[5] = 1;
        modelNumber[6] = 2;
        modelNumber[7] = 1;
        modelNumber[8] = 3;
        modelNumber[9] = 1;
        modelNumber[10] = 6;
        modelNumber[11] = 1;
        modelNumber[12] = 1;
        modelNumber[13] = 8;

        if (doALU(modelNumber) == 0) {
            StringBuilder resp = new StringBuilder(14);
            for (int i : modelNumber) {
                resp.append(i);
            }
            return Long.parseLong(resp.toString());
        }

        return -1;
    }

    @SuppressWarnings("unused")
    private void nextNumber(int[] modelNumber, int pos) {
        int n = modelNumber[pos];
        if (n == 1) {
            for (int i = pos; i < 14; i++) {
                modelNumber[i] = 9;
            }
            nextNumber(modelNumber, pos - 1);
        } else {
            modelNumber[pos] = modelNumber[pos] - 1;
        }
    }

    // It is a system with base 26.
    private long doALU(int[] modelNumber) {
        long x = 0;
        long y = 0;
        long z = 0;
        long w = 0;

        // inp w
        // The code can be reduced, because a number greater than 10 is added to x (In this case 14). Thus x is always greater than input and the "eql"
        // statement always sets x equal to 1. This makes z grow (current input is increased by N and written into z). Z*26 is nothing else than "SHIFT LEFT".
        w = modelNumber[0];
        z = w + 14;

        // inp w
        w = modelNumber[1];
        z *= 26;
        z += w + 2;

        // inp w
        w = modelNumber[2];
        z *= 26;
        z += w + 1;

        // inp w
        w = modelNumber[3];
        z *= 26;
        z += w + 13;

        // inp w
        w = modelNumber[4];
        z *= 26;
        z += w + 5;

        // inp w
        w = modelNumber[5];
        // Last written number is read and 12 is subtracted from the number.
        // This is the way to reduce Z, so let's do it.
        // To prevent the Z from increasing again, the current integer must be equal to "last number + 5 - 12".
        x = (z % 26) - 12;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 5) * x;
        z += y;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[3] + 13 - 12".
        w = modelNumber[6];
        x = (z % 26) - 12;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 5) * x;
        z += y;

        // inp w
        w = modelNumber[7];
        z *= 26;
        z += w + 9;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[7] + 9 - 7".
        w = modelNumber[8];
        x = (z % 26) - 7;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 3) * x;
        z += y;

        // inp w
        w = modelNumber[9];
        z *= 26;
        z += w + 13;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[9] + 13 - 8".
        w = modelNumber[10];
        x = (z % 26) - 8;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 2) * x;
        z += y;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[2] + 1 - 5".
        w = modelNumber[11];
        x = (z % 26) - 5;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 1) * x;
        z += y;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[1] + 2 - 10".
        w = modelNumber[12];
        x = (z % 26) - 10;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 11) * x;
        z += y;

        // inp w
        // To prevent the Z from increasing again, the current integer must be equal to "input[1] + 14 - 7".
        w = modelNumber[13];
        x = (z % 26) - 7;
        z = z / 26;
        x = (x == w) ? 0 : 1;
        y = 25 * x + 1;
        z *= y;
        y = (w + 8) * x;
        z += y;

        return z;
    }

}
