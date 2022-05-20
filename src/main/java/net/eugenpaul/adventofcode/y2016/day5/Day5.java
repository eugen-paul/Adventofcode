package net.eugenpaul.adventofcode.y2016.day5;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Crypto;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    private static final byte[] ZERO_ARRAY = new byte[] { 0, 0, 0 };

    @Getter
    private String password = null;

    @Getter
    private String secondPassword = null;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2016/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        StringBuilder passBuilder = new StringBuilder();
        TreeMap<Integer, String> secondPass = new TreeMap<>();

        byte[] prefix = eventData.getBytes();

        long number = 0;
        do {
            number++;
            byte[] hash = Crypto.doMd5(prefix, longToAsciiBytes(number));

            int sixthCharacter = (hash[2] & 0x0F);
            int sevensthCharacter = ((hash[3] & 0xF0) >> 4);

            hash[2] = (byte) (hash[2] & 0xF0);
            if (Arrays.compare(ZERO_ARRAY, 0, 3, hash, 0, 3) == 0) {
                if (passBuilder.length() < 8) {
                    passBuilder.append(String.format("%x", sixthCharacter));
                }
                if (secondPass.size() < 8 && sixthCharacter < 8) {
                    secondPass.putIfAbsent(sixthCharacter, String.format("%x", sevensthCharacter));
                }
            }
        } while (passBuilder.length() < 8 || secondPass.size() < 8);

        password = passBuilder.toString();

        StringBuilder secondPassBuilder = new StringBuilder();
        secondPass.entrySet().stream().forEach(v -> secondPassBuilder.append(v.getValue()));
        secondPassword = secondPassBuilder.toString();

        logger.log(Level.INFO, () -> "password  " + getPassword());
        logger.log(Level.INFO, () -> "secondPassword  " + getSecondPassword());

        return true;
    }

    private byte[] longToAsciiBytes(long data) {
        byte[] response = new byte[40];
        int len = 0;
        long tmp = data;
        while (tmp != 0) {
            long mod = tmp % 10;
            tmp = tmp / 10;
            len++;
            response[40 - len] = (byte) ('0' + mod);
        }

        return Arrays.copyOfRange(response, 40 - len, 40);
    }

}
