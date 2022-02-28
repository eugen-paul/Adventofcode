package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SwapLetter implements ScrambleOperation {

    private char x;
    private char y;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");
        return new SwapLetter(//
                elements[2].charAt(0), //
                elements[5].charAt(0) //
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == x) {
                input.setCharAt(i, y);
            } else if (input.charAt(i) == y) {
                input.setCharAt(i, x);
            }
        }
        return input;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == y) {
                input.setCharAt(i, x);
            } else if (input.charAt(i) == x) {
                input.setCharAt(i, y);
            }
        }
        return input;
    }

}
