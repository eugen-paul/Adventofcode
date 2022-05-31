package net.eugenpaul.adventofcode.y2016.day16;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day16 extends SolutionTemplate {

    @Getter
    private String checksum;

    @Getter
    private String checksum2;

    @Setter
    private int length = 272;
    @Setter
    private int length2 = 35651584;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2016/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        checksum = getChecksum(eventData, length);
        checksum2 = getChecksum(eventData, length2);

        logger.log(Level.INFO, () -> "checksum : " + getChecksum());
        logger.log(Level.INFO, () -> "checksum2 : " + getChecksum2());

        return true;
    }

    private String getChecksum(String eventData, int length) {
        String initial = eventData;
        while (initial.length() < length) {
            initial = DragonCurve.doDragonCurve(initial);
        }

        initial = initial.substring(0, length);

        return Checksum.computeChecksum(initial);
    }

}
