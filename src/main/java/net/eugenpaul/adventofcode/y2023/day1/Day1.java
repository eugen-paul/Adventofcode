package net.eugenpaul.adventofcode.y2023.day1;

import java.util.List;
import java.util.TreeMap;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day1 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2023/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        int response = 0;
        for (String line : eventData) {
            int first = StringConverter.toCharStream(line).filter(Character::isDigit).map(c->Integer.valueOf(c+"")).findFirst().orElseThrow();
            int last = StringConverter.toCharStream(line).filter(Character::isDigit).map(c->Integer.valueOf(c+"")).reduce(0, (a, b) -> b);
            response += first * 10 + last;
        }
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        int response = 0;
        for (String line : eventData) {
            var m = getDigits(line);
            response += m.get(m.firstKey()) * 10 + m.get(m.lastKey());
        }
        return response;
    }

    private TreeMap<Integer,Integer> getDigits(String line){
        var p = List.of("0","1","2","3","4","5","6","7","8","9","one","two","three","four","five","six","seven","eight","nine");
        TreeMap<Integer,Integer> response = new TreeMap<>();
        for (String param: p){
            int first = line.indexOf(param);
            if(first >= 0){
                response.put(first, toInt(param));
            }
            int last = line.lastIndexOf(param);
            if(last >= 0){
                response.put(last, toInt(param));
            }
        }
        return response;
    }

    private int toInt(String param) {
        return switch (param) {
        case "0" -> 0;
        case "1", "one" -> 1;
        case "2", "two" -> 2;
        case "3", "three" -> 3;
        case "4", "four" -> 4;
        case "5", "five" -> 5;
        case "6", "six" -> 6;
        case "7", "seven" -> 7;
        case "8", "eight" -> 8;
        case "9", "nine" -> 9;
        default -> throw new IllegalArgumentException("Unexpected value: " + param);
        };
    }

}
