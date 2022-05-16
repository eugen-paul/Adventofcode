package net.eugenpaul.adventofcode.helper;

public final class MathHelper {
    private MathHelper() {

    }

    public static long sum(Long from, long to) {
        return (to * to + to - (from - 1L) * (from - 1L) - (from - 1L)) / 2L;
    }
    
    public static long modularExponentiation(long number, long exp, long mod) {
        String expBinary = Long.toBinaryString(exp);

        long response = 1L;
        for (char element : expBinary.toCharArray()) {
            if (element == '0') {
                response = (response * response) % mod;
            } else {
                response = (response * response) % mod;
                response = (response * number) % mod;
            }
        }

        return response;
    }
}
