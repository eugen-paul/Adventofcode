package net.eugenpaul.adventofcode.y2021.day3;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private long powerConsumption;
    @Getter
    private long lifeSupportRating;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2021/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        powerConsumption = computePowerConsumption(eventData);

        lifeSupportRating = filterOxygenGeneratorRating(eventData) * filterCO2ScrubberRating(eventData);

        logger.log(Level.INFO, () -> "powerConsumption  : " + getPowerConsumption());
        logger.log(Level.INFO, () -> "lifeSupportRating  : " + getLifeSupportRating());

        return true;
    }

    private long computePowerConsumption(List<String> eventData) {
        StringBuilder gammaRate = new StringBuilder(eventData.get(0).length());
        StringBuilder epsilonRate = new StringBuilder(eventData.get(0).length());
        for (int i = 0; i < eventData.get(0).length(); i++) {
            if (getMostCommonValue(eventData, i) == '1') {
                gammaRate.append("1");
                epsilonRate.append("0");
            } else {
                gammaRate.append("0");
                epsilonRate.append("1");
            }
        }

        return Long.parseLong(gammaRate.toString(), 2) * Long.parseLong(epsilonRate.toString(), 2);
    }

    private long filterOxygenGeneratorRating(List<String> eventData) {
        List<String> data = new LinkedList<>(eventData);
        int currentPos = 0;

        while (data.size() > 1) {
            char mostCommonValue = getMostCommonValue(data, currentPos);
            int pos = currentPos;
            data.removeIf(v -> v.charAt(pos) != mostCommonValue);
            currentPos++;
        }

        return Long.parseLong(data.get(0), 2);
    }

    private long filterCO2ScrubberRating(List<String> eventData) {
        List<String> data = new LinkedList<>(eventData);
        int currentPos = 0;

        while (data.size() > 1) {
            char mostCommonValue = getMostCommonValue(data, currentPos);
            int pos = currentPos;
            data.removeIf(v -> v.charAt(pos) == mostCommonValue);
            currentPos++;
        }

        return Long.parseLong(data.get(0), 2);
    }

    private char getMostCommonValue(List<String> eventData, int position) {
        int counter0 = 0;
        int counter1 = 0;
        for (String data : eventData) {
            if (data.charAt(position) == '1') {
                counter1++;
            } else {
                counter0++;
            }
        }
        return (counter0 > counter1) ? '0' : '1';
    }

}
