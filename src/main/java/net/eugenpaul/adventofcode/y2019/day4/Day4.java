package net.eugenpaul.adventofcode.y2019.day4;

import java.util.function.Predicate;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    @Getter
    private int diffPasswords;
    @Getter
    private int diffPasswords2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2019/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        int from = Integer.parseInt(eventData.substring(0, 6));
        int to = Integer.parseInt(eventData.substring(7));

        diffPasswords = getPasswordCount(from, to, this::isPasswordOk);
        diffPasswords2 = getPasswordCount(from, to, this::isPasswordOkExtra);

        logger.log(Level.INFO, () -> "diffPasswords   : " + getDiffPasswords());
        logger.log(Level.INFO, () -> "diffPasswords2  : " + getDiffPasswords2());

        return true;
    }

    public int getPasswordCount(int from, int to, Predicate<int[]> checker) {
        int count = 0;
        int[] pass = { //
                (from / 100000) % 10, //
                (from / 10000) % 10, //
                (from / 1000) % 10, //
                (from / 100) % 10, //
                (from / 10) % 10, //
                (from) % 10 //
        };

        for (int i = from; i <= to; i++) {
            if (checker.test(pass)) {
                count++;
            }
            inc(pass);
        }

        return count;
    }

    public void inc(int[] pass) {
        for (int i = pass.length - 1; i >= 0; i--) {
            if (pass[i] == 9) {
                pass[i] = 0;
            } else {
                pass[i] = pass[i] + 1;
                break;
            }
        }
    }

    public boolean isPasswordOkExtra(int[] pass) {
        // group of two
        boolean isOk = false;
        for (int i = 0; i < pass.length - 1; i++) {
            if (i == 0) {
                if (pass[0] == pass[1] && pass[1] != pass[2]) {
                    isOk = true;
                }
            } else if (i == 4) {
                if (pass[3] != pass[4] && pass[4] == pass[5]) {
                    isOk = true;
                }
            } else if (pass[i - 1] != pass[i] //
                    && pass[i] == pass[i + 1] //
                    && pass[i + 1] != pass[i + 2] //
            ) {
                isOk = true;
            }
        }

        if (!isOk) {
            return false;
        }

        return isDecrease(pass);
    }

    private boolean isDecrease(int[] pass) {
        boolean isOk = true;
        for (int i = 0; i < pass.length - 1; i++) {
            if (pass[i] > pass[i + 1]) {
                isOk = false;
            }
        }
        return isOk;
    }

    public boolean isPasswordOk(int[] pass) {
        // check adjacent digits
        boolean isOk = false;
        for (int i = 0; i < pass.length - 1; i++) {
            if (pass[i] == pass[i + 1]) {
                isOk = true;
            }
        }

        if (!isOk) {
            return false;
        }

        return isDecrease(pass);
    }

}
