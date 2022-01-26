package net.eugenpaul.adventofcode.y2016.day8.instruction;

public class RotateRow implements Instruction {

    private int row;
    private int shift;

    public RotateRow(int row, int shift) {
        this.row = row;
        this.shift = shift;
    }

    public static RotateRow fromString(String data) {
        String[] size = data.substring("rotate row y=".length()).split(" ");
        return new RotateRow(Integer.parseInt(size[0]), Integer.parseInt(size[2]));
    }

    @Override
    public void doInstruction(boolean[][] display) {

        for (int i = 0; i < shift; i++) {
            boolean lastPixel = display[display.length - 1][row];

            for (int j = display.length - 1; j > 0; j--) {
                display[j][row] = display[j - 1][row];
            }

            display[0][row] = lastPixel;
        }

    }

}
