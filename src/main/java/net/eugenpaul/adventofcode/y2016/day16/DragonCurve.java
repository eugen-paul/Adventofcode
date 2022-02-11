package net.eugenpaul.adventofcode.y2016.day16;

public class DragonCurve {
    private DragonCurve() {

    }

    public static String doDragonCurve(String a) {
        return a + '0' + reverseAndInvertString(a);
    }

    private static String reverseAndInvertString(String input) {
        StringBuilder reverse = new StringBuilder(input.length());
        for (int i = input.length() - 1; i >= 0; i--) {
            reverse.append((input.charAt(i) == '0') ? '1' : '0');
        }
        return reverse.toString();
    }
}
