package net.eugenpaul.adventofcode.y2016.day3;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    private static final String REGEX = "[ ]*(\\d+)[ ]+(\\d+)[ ]+(\\d+)$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    @Getter
    private int triangleCount;

    @Getter
    private int triangleVerticallyCount;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2016/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        triangleCount = (int) eventData.stream().filter(this::isTriangle).count();

        triangleVerticallyCount = 0;
        LinkedList<String> dataCopy = new LinkedList<>(eventData);
        while (!dataCopy.isEmpty()) {
            triangleVerticallyCount += isTriangle(dataCopy.poll(), dataCopy.poll(), dataCopy.poll());
        }

        logger.log(Level.INFO, () -> "triangleCount: " + getTriangleCount());
        logger.log(Level.INFO, () -> "triangleVerticallyCount: " + getTriangleVerticallyCount());

        return true;
    }

    private boolean isTriangle(String data) {
        Matcher matcher = pattern.matcher(data);
        matcher.find();
        return isTriangle(//
                Integer.parseInt(matcher.group(1)), //
                Integer.parseInt(matcher.group(2)), //
                Integer.parseInt(matcher.group(3)) //
        );
    }

    private int isTriangle(String data1, String data2, String data3) {
        Matcher matcher1 = pattern.matcher(data1);
        matcher1.find();
        Matcher matcher2 = pattern.matcher(data2);
        matcher2.find();
        Matcher matcher3 = pattern.matcher(data3);
        matcher3.find();

        int response = 0;
        for (int i = 1; i <= 3; i++) {
            if (isTriangle(//
                    Integer.parseInt(matcher1.group(i)), //
                    Integer.parseInt(matcher2.group(i)), //
                    Integer.parseInt(matcher3.group(i)) //
            )) {
                response++;
            }
        }

        return response;
    }

    private boolean isTriangle(int a, int b, int c) {
        return a + b > c//
                && a + c > b//
                && b + c > a;
    }
}
