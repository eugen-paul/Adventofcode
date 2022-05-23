package net.eugenpaul.adventofcode.y2016.day8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2016.day8.instruction.InstructionFactory;

public class Day8 extends SolutionTemplate {

    @Getter
    private long pixelsLit;

    @Getter
    private List<String> displayData = new LinkedList<>();

    @Setter
    private int sizeX = 50;
    @Setter
    private int sizeY = 6;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2016/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        boolean[][] display = new boolean[sizeX][sizeY];
        for (int i = 0; i < display.length; i++) {
            Arrays.fill(display[i], false);
        }

        eventData.stream().forEach(v -> InstructionFactory.fromString(v).doInstruction(display));

        pixelsLit = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (display[i][j]) {
                    pixelsLit++;
                }
            }
        }

        prindDisplay(display);

        logger.log(Level.INFO, () -> "pixelsLit: " + getPixelsLit());

        return true;
    }

    private void prindDisplay(boolean[][] display) {
        for (int i = 0; i < display[0].length; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < display.length; j++) {
                line.append(display[j][i] ? "#" : " ");
            }
            displayData.add(line.toString());
            logger.log(Level.INFO, line::toString);
        }
    }
}
