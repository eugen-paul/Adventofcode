package net.eugenpaul.adventofcode.y2020.day2;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {
    private static final String LINE_FORMAT = "^(\\d+)-(\\d+) ([a-z]): ([a-z]*)$";
    private static final Pattern LINE_PATTERN = Pattern.compile(LINE_FORMAT);

    @Getter
    private long okPassCount;
    @Getter
    private long okPass2Count;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2020/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        okPassCount = eventData.stream().filter(this::isPasswordOk).count();
        okPass2Count = eventData.stream().filter(this::isPasswordOk2).count();

        logger.log(Level.INFO, () -> "okPassCount   : " + getOkPassCount());
        logger.log(Level.INFO, () -> "okPass2Count  : " + getOkPass2Count());

        return true;
    }

    private boolean isPasswordOk(String data) {
        Matcher m = LINE_PATTERN.matcher(data);
        if (!m.find()) {
            throw new IllegalArgumentException(data);
        }

        int min = Integer.parseInt(m.group(1));
        int max = Integer.parseInt(m.group(2));
        char c = m.group(3).charAt(0);
        char[] password = m.group(4).toCharArray();

        int count = 0;
        for (char p : password) {
            if (p == c) {
                count++;
            }
        }

        return min <= count && count <= max;
    }

    private boolean isPasswordOk2(String data) {
        Matcher m = LINE_PATTERN.matcher(data);
        if (!m.find()) {
            throw new IllegalArgumentException(data);
        }

        int min = Integer.parseInt(m.group(1)) - 1;
        int max = Integer.parseInt(m.group(2)) - 1;
        char c = m.group(3).charAt(0);
        char[] password = m.group(4).toCharArray();

        return (password[min] == c && password[max] != c) //
                || (password[min] != c && password[max] == c);
    }

}
