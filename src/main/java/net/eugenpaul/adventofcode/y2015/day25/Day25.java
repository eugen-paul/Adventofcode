package net.eugenpaul.adventofcode.y2015.day25;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @Getter
    private long code;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2015/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        InfinitePaper paper = new InfinitePaper(20151125L, 252533L, 33554393L);
        String[] elements = eventData.split(" ");
        long posX = Long.parseLong(elements[16].substring(0, elements[16].length() - 1));
        long posY = Long.parseLong(elements[18].substring(0, elements[18].length() - 1));

        code = paper.getElement(posX, posY);
        logger.log(Level.INFO, () -> "code : " + getCode());

        return true;
    }
}
