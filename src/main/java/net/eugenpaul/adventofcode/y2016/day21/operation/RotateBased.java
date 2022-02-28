package net.eugenpaul.adventofcode.y2016.day21.operation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RotateBased implements ScrambleOperation {

    private char x;

    public static ScrambleOperation fromString(String input) {
        String[] elements = input.split(" ");

        return new RotateBased(//
                elements[6].charAt(0) //
        );
    }

    @Override
    public StringBuilder execute(StringBuilder input) {
        StringBuilder output = new StringBuilder(input.length());

        int pos = input.indexOf(x + "");

        int steps = pos + 1;
        if (pos >= 4) {
            steps++;
        }

        steps = (input.length() - steps) % input.length();
        if (steps < 0) {
            steps += input.length();
        }

        output.append(input.substring(steps));
        if (x > 0) {
            output.append(input.substring(0, steps));
        }

        return output;
    }

    @Override
    public StringBuilder reverse(StringBuilder input) {
        StringBuilder output = new StringBuilder(input.length());

        int pos = input.indexOf(x + "");

        int steps = 0;
        switch (pos) {
        case 0:
            steps = 9;
            break;
        case 1:
            steps = 1;
            break;
        case 2:
            steps = 6;
            break;
        case 3:
            steps = 2;
            break;
        case 4:
            steps = 7;
            break;
        case 5:
            steps = 3;
            break;
        case 6:
            steps = 8;
            break;
        case 7:
            steps = 4;
            break;
        default:
            throw new IllegalArgumentException();
        }

        steps = steps % input.length();

        output.append(input.substring(steps));
        if (x > 0) {
            output.append(input.substring(0, steps));
        }

        return output;
    }

}
