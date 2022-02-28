package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SwapPosition implements ScrambleOperation {

    private int x;
    private int y;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");
        return new SwapPosition(//
                Integer.parseInt(elements[2]), //
                Integer.parseInt(elements[5]) //
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        char from = input.charAt(x);
        char to = input.charAt(y);

        input.setCharAt(x, to);
        input.setCharAt(y, from);
        return input;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        char from = input.charAt(y);
        char to = input.charAt(x);

        input.setCharAt(y, to);
        input.setCharAt(x, from);
        return input;
    }

}
