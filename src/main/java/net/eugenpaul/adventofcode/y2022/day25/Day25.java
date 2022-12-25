package net.eugenpaul.adventofcode.y2022.day25;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @Getter
    private String part1;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2022/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());

        return true;
    }

    private String doPuzzle1(List<String> eventData) {
        long sum = eventData.stream()//
                .mapToLong(this::snafToDigimal)//
                .sum();

        return digitToSnaf(sum);
    }

    private long snafToDigimal(String data) {
        long current = 0;
        int pos = 5;

        for (int i = 0; i < data.length(); i++) {
            switch (data.charAt(i)) {
            case '=':
                current = current * pos - 2;
                break;
            case '-':
                current = current * pos - 1;
                break;
            case '0':
                current = current * pos;
                break;
            case '1':
                current = current * pos + 1;
                break;
            case '2':
                current = current * pos + 2;
                break;

            default:
                break;
            }
        }
        return current;
    }

    private String digitToSnaf(long data) {
        StringBuilder response = new StringBuilder();
        long dig = data;
        while (dig > 0) {
            int mod = (int) (dig % 5);
            switch (mod) {
            case 0:
                response.append('0');
                break;
            case 1:
                response.append('1');
                break;
            case 2:
                response.append('2');
                break;
            case 3:
                response.append('=');
                break;
            case 4:
                response.append('-');
                break;
            default:
                break;
            }

            dig = (dig + 2) / 5;
        }
        return response.reverse().toString();
    }

}
