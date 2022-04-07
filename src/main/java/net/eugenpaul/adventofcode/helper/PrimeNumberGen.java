package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;

public class PrimeNumberGen {

    private PrimeNumberGen() {

    }

    /**
     * Compute all prime numbers from 1 to to (inclusive)
     * 
     * @param to
     * @return list of prime numbers
     * @exception IllegalArgumentException - to must be greater then 0
     */
    public static List<Long> genPrimeNumbers(long to) throws IllegalArgumentException {
        if (to < 1) {
            throw new IllegalArgumentException("\"to\" must be greater then 0.");
        }
        var responsePrimeNumbers = new LinkedList<Long>();
        if (to >= 2) {
            responsePrimeNumbers.add(2L);
        }

        for (long i = 3L; i <= to; i++) {
            if (checkPrim(i, responsePrimeNumbers)) {
                responsePrimeNumbers.add(i);
            }
        }

        responsePrimeNumbers.addFirst(1L);

        return responsePrimeNumbers;
    }

    private static boolean checkPrim(long number, List<Long> primeNumbers) {
        for (Long prime : primeNumbers) {
            if ((number % prime) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compute largest prime number that is less than or equal to to.
     * 
     * @param to
     * @return
     * @throws IllegalArgumentException
     */
    public static long genMaxPrimeNumbers(long to) throws IllegalArgumentException {
        if (to < 1L) {
            throw new IllegalArgumentException("\"to\" must be greater then 0.");
        }
        if (to <= 3L) {
            return to;
        }

        long response = to;
        for (; response > 3; response--) {
            if (checkPrim(response)) {
                break;
            }
        }

        return response;
    }

    public static boolean checkPrim(long number) {

        long maxCheckNumber = (long) Math.sqrt(number);

        for (int i = 2; i <= maxCheckNumber; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;
    }
}