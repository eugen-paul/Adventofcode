package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Rotate implements ScrambleOperation {

    private int stepsToRotate;
    private boolean direction;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");

        return new Rotate(//
                Integer.parseInt(elements[2]), //
                elements[1].equalsIgnoreCase("right")//
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        return rotate(input, stepsToRotate, direction);
    }

    private StringBuilder rotate(StringBuilder input, int x, boolean right) {
        StringBuilder output = new StringBuilder(input.length());

        int steps = x;
        if (right) {
            steps = input.length() - x;
            if (steps < 0) {
                steps += input.length();
            }
        }

        output.append(input.substring(steps));
        if (x > 0) {
            output.append(input.substring(0, steps));
        }

        return output;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        return rotate(input, stepsToRotate, !direction);
    }

}
