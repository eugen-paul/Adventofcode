package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reverse implements ScrambleOperation {

    private int x;
    private int y;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");
        return new Reverse(//
                Integer.parseInt(elements[2]), //
                Integer.parseInt(elements[4]) //
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        StringBuilder reverseData = new StringBuilder(input.substring(x, y + 1));
        reverseData = reverseData.reverse();

        input.replace(x, y + 1, reverseData.toString());
        return input;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        return execute(input);
    }

}
