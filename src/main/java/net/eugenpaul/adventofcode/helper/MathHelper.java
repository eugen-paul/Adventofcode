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

    /**
     * greatest common factor
     * 
     * @param a
     * @param b
     * @return
     */
    public static int gcf(int a, int b) {
        if (a == b || b == 0)
            return a;
        else
            return gcf(b, a % b);
    }

    public static long gcf(long a, long b) {
        if (a == b || b == 0)
            return a;
        else
            return gcf(b, a % b);
    }

    /**
     * lowest common multiple
     * 
     * @param a
     * @param b
     * @return
     */
    public static int lcm(int a, int b) {
        return a * (b / gcf(a, b));
    }

    public static long lcm(long a, long b) {
        return a * (b / gcf(a, b));
    }
}
