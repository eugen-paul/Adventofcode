package net.eugenpaul.adventofcode.y2015.day19;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Replacement {

    private String input;
    private String output;

    public static Replacement fromString(String data) {
        String[] elements = data.split(" ");
        return new Replacement(elements[0], elements[2]);
    }
}
