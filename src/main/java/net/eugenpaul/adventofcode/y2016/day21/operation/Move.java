package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Move implements ScrambleOperation {

    private int from;
    private int to;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");
        return new Move(//
                Integer.parseInt(elements[2]), //
                Integer.parseInt(elements[5]) //
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        return move(input, from, to);
    }

    private StringBuilder move(StringBuilder input, int x, int y) {
        if (x == y) {
            return input;
        }

        StringBuilder output = new StringBuilder(input.length());
        if (x < y) {
            output.append(input.substring(0, x));
            output.append(input.substring(x + 1, y + 1));
            output.append(input.substring(x, x + 1));
            output.append(input.substring(y + 1));
        } else {
            output.append(input.substring(0, y));
            output.append(input.substring(x, x + 1));
            output.append(input.substring(y, x));
            output.append(input.substring(x + 1));
        }
        return output;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        return move(input, to, from);
    }

}
