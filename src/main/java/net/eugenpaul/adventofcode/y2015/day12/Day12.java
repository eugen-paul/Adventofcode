package net.eugenpaul.adventofcode.y2015.day12;

import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Getter
    private Integer sum = null;
    @Getter
    private Integer sumWithoutRed = null;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2015/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        DocumentObject data = DocumentParser.parseData(eventData);

        sum = getSum(data);
        sumWithoutRed = getSumWithoutRed(data);

        logger.log(Level.INFO, () -> "sum  " + getSum());
        logger.log(Level.INFO, () -> "sumWithoutRed  " + getSumWithoutRed());
        return true;
    }

    private int getSum(DocumentObject data) {
        int response = 0;

        if (null != data.getValueInt()) {
            response = data.getValueInt();
        }

        if (null != data.getArrays()) {
            response += data.getArrays().stream().//
                    collect(Collectors.summingInt(this::getSum));
        }

        if (null != data.getObjects()) {
            response += data.getObjects().stream().//
                    collect(Collectors.summingInt(this::getSum));
        }

        return response;
    }

    private int getSumWithoutRed(DocumentObject data) {
        int response = 0;

        if (null != data.getValueInt()) {
            response = data.getValueInt();
        }

        if (null != data.getArrays()) {
            response += data.getArrays().stream().//
                    collect(Collectors.summingInt(this::getSumWithoutRed));
        }

        if (null != data.getObjects()) {
            boolean withoutRed = data.getObjects().stream()//
                    .noneMatch(v -> v.getValueString() != null && v.getValueString().equals("red"));
            if (withoutRed) {
                response += data.getObjects().stream().//
                        collect(Collectors.summingInt(this::getSumWithoutRed));
            }
        }

        return response;
    }

}
