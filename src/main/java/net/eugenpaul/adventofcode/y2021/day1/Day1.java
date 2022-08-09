package net.eugenpaul.adventofcode.y2021.day1;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private long increases;
    @Getter
    private long increasesOf3;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2021/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Integer> report = eventData.stream().map(Integer::parseInt).collect(Collectors.toList());

        increases = doPuzzle1(report);

        increasesOf3 = duPuzzle2(report);

        logger.log(Level.INFO, () -> "increases    : " + getIncreases());
        logger.log(Level.INFO, () -> "increasesOf3 : " + getIncreasesOf3());

        return true;
    }

    private int doPuzzle1(List<Integer> report) {
        Integer lastValue = null;
        int response = 0;
        for (Integer data : report) {
            if (lastValue != null && data > lastValue) {
                response++;
            }
            lastValue = data;
        }
        return response;
    }

    private int duPuzzle2(List<Integer> report) {
        Integer window1 = 0;
        Integer window2 = 0;
        Integer window3 = 0;
        int step = 0;
        int response = 0;
        for (Integer data : report) {
            switch (step) {
            case 0:
                window1 += data;
                break;
            case 1:
                window1 += data;
                window2 += data;
                break;
            case 2:
                window1 += data;
                window2 += data;
                window3 += data;
                break;
            default:
                window2 += data;
                window3 += data;
                if (window2 > window1) {
                    response++;
                }
                window1 = window2;
                window2 = window3;
                window3 = data;
                break;
            }

            step++;
        }
        return response;
    }

}
