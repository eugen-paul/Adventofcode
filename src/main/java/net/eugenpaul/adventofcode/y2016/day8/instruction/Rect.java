package net.eugenpaul.adventofcode.y2016.day8.instruction;

public class Rect implements Instruction {

    private int x;
    private int y;

    private Rect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Rect fromString(String data) {
        String[] size = data.substring("rect ".length()).split("x");
        return new Rect(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
    }

    @Override
    public void doInstruction(boolean[][] display) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                display[i][j] = true;
            }
        }
    }

}
