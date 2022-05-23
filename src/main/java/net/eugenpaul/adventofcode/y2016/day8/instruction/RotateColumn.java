package net.eugenpaul.adventofcode.y2016.day8.instruction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RotateColumn implements Instruction {

    private int column;
    private int shift;

    public static RotateColumn fromString(String data) {
        String[] size = data.substring("rotate column x=".length()).split(" ");
        return new RotateColumn(Integer.parseInt(size[0]), Integer.parseInt(size[2]));
    }

    @Override
    public void doInstruction(boolean[][] display) {

        for (int i = 0; i < shift; i++) {
            boolean lastPixel = display[column][display[column].length - 1];

            for (int j = display[0].length - 1; j > 0; j--) {
                display[column][j] = display[column][j - 1];
            }

            display[column][0] = lastPixel;
        }

    }

}
