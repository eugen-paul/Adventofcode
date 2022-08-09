package net.eugenpaul.adventofcode.y2021.day2;

import java.util.List;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @AllArgsConstructor
    private class SubmarinePos {
        private int posX;
        private int aim;
        private int depth;
    }

    @Getter
    private long multiply;
    @Getter
    private long multiply2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2021/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        SubmarinePos pos = new SubmarinePos(0, 0, 0);
        SubmarinePos pos2 = new SubmarinePos(0, 0, 0);

        for (var data : eventData) {
            String[] elements = data.split(" ");
            int delta = Integer.parseInt(elements[1]);
            switch (elements[0]) {
            case "forward":
                pos.posX += delta;
                pos2.posX += delta;
                pos2.depth += delta * pos2.aim;
                break;
            case "down":
                pos.depth += delta;
                pos2.aim += delta;
                break;
            case "up":
                pos.depth -= delta;
                pos2.aim -= delta;
                break;
            default:
                break;
            }
        }

        multiply = (long) pos.depth * pos.posX;
        multiply2 = (long) pos2.depth * pos2.posX;

        logger.log(Level.INFO, () -> "multiply  : " + getMultiply());
        logger.log(Level.INFO, () -> "multiply2 : " + getMultiply2());

        return true;
    }

}
