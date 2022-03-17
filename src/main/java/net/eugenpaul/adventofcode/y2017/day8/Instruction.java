package net.eugenpaul.adventofcode.y2017.day8;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Instruction {

    private String targetRegister;
    private Integer modificator;

    private String checkRegister;
    private Integer checkValue;

    private String eq;

    private boolean inc;

    public static Instruction fromString(String data) {
        String[] elements = data.split(" ");

        return new Instruction(//
                elements[0], //
                Integer.parseInt(elements[2]), //
                elements[4], //
                Integer.parseInt(elements[6]), //
                elements[5], //
                elements[1].equals("inc")//
        );
    }
}
