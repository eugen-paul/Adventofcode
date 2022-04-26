package net.eugenpaul.adventofcode.y2018.day10;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day10 extends SolutionTemplate {

    private static final String POINTS_FORMAT = "^position=<([ \\-0-9]*),([ \\-0-9]*)> velocity=<([ \\-0-9]*),([ \\-0-9]*)>$";
    private static final Pattern PATTERN_POINTS = Pattern.compile(POINTS_FORMAT);

    @Getter
    private static class Point {
        private SimplePos pos;
        private SimplePos velocity;

        public Point(String data) {
            Matcher pointMatcher = PATTERN_POINTS.matcher(data);

            pointMatcher.find();

            int xPos = Integer.parseInt(pointMatcher.group(1).trim());
            int yPos = Integer.parseInt(pointMatcher.group(2).trim());
            int xVel = Integer.parseInt(pointMatcher.group(3).trim());
            int yVel = Integer.parseInt(pointMatcher.group(4).trim());

            pos = new SimplePos(xPos, yPos);
            velocity = new SimplePos(xVel, yVel);
        }
    }

    @Getter
    private long time;

    @Getter
    private List<String> text;

    @Setter
    private int size = 10;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2018/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Point> points = eventData.stream().map(Point::new).collect(Collectors.toList());
        time = 0;

        while (true) {
            time++;
            int yMin = Integer.MAX_VALUE;
            int yMax = Integer.MIN_VALUE;
            for (Point point : points) {
                point.getPos().add(point.velocity);
                yMin = Math.min(yMin, point.getPos().getY());
                yMax = Math.max(yMax, point.getPos().getY());
            }

            // Assumption:
            // Text is printed when the maximum height reaches the size X.
            if (yMax - yMin < size) {
                Map<SimplePos, Boolean> sky = points.stream()//
                        .map(Point::getPos)//
                        .collect(Collectors.toMap(k -> k, v -> true, (a, b) -> a));

                text = StringConverter.printBoolMap(sky);

                break;
            }
        }

        logger.log(Level.INFO, () -> "time : " + getTime());

        return true;
    }

}
