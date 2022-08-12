package net.eugenpaul.adventofcode.y2021.day5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    private static final String FORMAT = "^(\\d*),(\\d*) -> (\\d*),(\\d*)$";
    private static final Pattern PATTERN = Pattern.compile(FORMAT);

    @AllArgsConstructor
    private class Line {
        SimplePos a;
        SimplePos b;

        public boolean isHorizontalOrVertical() {
            return isHorizontal() || isVertical();
        }

        private boolean isHorizontal() {
            return a.getY() == b.getY();
        }

        private boolean isVertical() {
            return a.getX() == b.getX();
        }

        private void forEach(Consumer<SimplePos> c) {
            if (isHorizontal()) {
                consumeHorizontal(c);
            } else if (isVertical()) {
                consumeVertical(c);
            } else {
                consumeDiagonal(c);
            }
        }

        private void consumeDiagonal(Consumer<SimplePos> c) {
            int beginX;
            int beginY;
            int endX;
            int delta;
            if (a.getX() > b.getX()) {
                beginX = b.getX();
                endX = a.getX();
                beginY = b.getY();
                if (a.getY() > b.getY()) {
                    delta = 1;
                } else {
                    delta = -1;
                }
            } else {
                beginX = a.getX();
                endX = b.getX();
                beginY = a.getY();
                if (a.getY() > b.getY()) {
                    delta = -1;
                } else {
                    delta = 1;
                }
            }

            int y = beginY;
            for (int x = beginX; x <= endX; x++) {
                c.accept(new SimplePos(x, y));
                y += delta;
            }
        }

        private void consumeVertical(Consumer<SimplePos> c) {
            int x = a.getX();
            int begin = Math.min(a.getY(), b.getY());
            int end = Math.max(a.getY(), b.getY());
            for (int y = begin; y <= end; y++) {
                c.accept(new SimplePos(x, y));
            }
        }

        private void consumeHorizontal(Consumer<SimplePos> c) {
            int y = a.getY();
            int begin = Math.min(a.getX(), b.getX());
            int end = Math.max(a.getX(), b.getX());
            for (int x = begin; x <= end; x++) {
                c.accept(new SimplePos(x, y));
            }
        }
    }

    @Getter
    private long overlaps;
    @Getter
    private long overlaps2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2021/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Line> lines = eventData.stream()//
                .map(this::fromString)//
                .collect(Collectors.toList());

        List<Line> hvLines = lines.stream()//
                .filter(Line::isHorizontalOrVertical)//
                .collect(Collectors.toList());

        Map<SimplePos, Integer> area = new HashMap<>();
        for (Line line : hvLines) {
            line.forEach(point -> area.compute(point, (k, v) -> (v == null) ? 1 : v + 1));
        }

        Map<SimplePos, Integer> areaFull = new HashMap<>();
        for (Line line : lines) {
            line.forEach(point -> areaFull.compute(point, (k, v) -> (v == null) ? 1 : v + 1));
        }

        overlaps = area.values().stream().filter(v -> v > 1).count();
        overlaps2 = areaFull.values().stream().filter(v -> v > 1).count();

        logger.log(Level.INFO, () -> "overlaps  : " + getOverlaps());
        logger.log(Level.INFO, () -> "overlaps2 : " + getOverlaps2());

        return true;
    }

    private Line fromString(String data) {
        Matcher m = PATTERN.matcher(data);
        if (!m.find()) {
            throw new IllegalArgumentException(data);
        }
        return new Line(//
                new SimplePos(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))), //
                new SimplePos(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))) //
        );
    }
}
