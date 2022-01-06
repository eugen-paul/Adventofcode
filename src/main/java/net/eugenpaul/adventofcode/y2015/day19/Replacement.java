package net.eugenpaul.adventofcode.y2015.day19;

public class Replacement {

    private String input;
    private String output;

    private Replacement(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return this.input;
    }

    public String getOutput() {
        return this.output;
    }

    @Override
    public String toString() {
        return input + "=>" + output;
    }

    public static Replacement fromString(String data) {
        String[] elements = data.split(" ");
        return new Replacement(elements[0], elements[2]);
    }
}
