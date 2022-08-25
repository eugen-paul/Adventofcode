package net.eugenpaul.adventofcode.y2021.day17;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {

    private static final String FORMAT = "^target area: x=([\\-0-9]*)\\.\\.([\\-0-9]*), y=([\\-0-9]*)\\.\\.([\\-0-9]*)$";
    private static final Pattern PATTERN = Pattern.compile(FORMAT);

    private class Area {
        private int xMin;
        private int xMax;
        private int yMin;
        private int yMax;

        public Area(String data) {
            Matcher m = PATTERN.matcher(data);
            if (!m.find()) {
                throw new IllegalArgumentException();
            }

            xMin = Integer.parseInt(m.group(1));
            xMax = Integer.parseInt(m.group(2));
            yMin = Integer.parseInt(m.group(3));
            yMax = Integer.parseInt(m.group(4));
        }

        public boolean isInArea(SimplePos pos) {
            return xMin <= pos.getX() && pos.getX() <= xMax//
                    && yMin <= pos.getY() && pos.getY() <= yMax;
        }

        public boolean isOver(SimplePos pos) {
            return xMax < pos.getX() //
                    || pos.getY() < yMin;
        }
    }

    @Getter
    private long highestY;
    @Getter
    private long count;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2021/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Area area = new Area(eventData);

        int xMin = getMinXVelocity(area);
        int xMax = getMaxXVelocity(area);

        int yMin = getMinYVelocity(area);
        int yMax = getMaxYVelocity(area);

        highestY = Integer.MIN_VALUE;
        count = 0;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                int currentMaxY = getMaxY(area, x, y);
                highestY = Math.max(highestY, currentMaxY);
                if (currentMaxY != Integer.MIN_VALUE) {
                    count++;
                }
            }
        }

        logger.log(Level.INFO, () -> "highestY   : " + getHighestY());
        logger.log(Level.INFO, () -> "count      : " + getCount());

        return true;
    }

    private int getMaxY(Area area, int x, int y) {
        SimplePos pos = new SimplePos(0, 0);
        int responseHighestY = Integer.MIN_VALUE;

        int deltaX = x;
        int deltaY = y;

        while (!area.isOver(pos)) {
            if (deltaX > 0) {
                pos.setX(pos.getX() + deltaX);
                deltaX--;
            }
            pos.setY(pos.getY() + deltaY);
            deltaY--;

            responseHighestY = Math.max(responseHighestY, pos.getY());

            if (area.isInArea(pos)) {
                return responseHighestY;
            }
        }

        return Integer.MIN_VALUE;
    }

    private int getMaxXVelocity(Area area) {
        return area.xMax;
    }

    private int getMinXVelocity(Area area) {
        int from = 1;
        int to = area.xMin;

        while (from != to) {
            int current = from + (to - from) / 2;
            int maxX = (int) MathHelper.sum(1L, current);
            if (maxX >= area.xMin) {
                to = current;
            } else {
                from = current + 1;
            }
        }

        return from;
    }

    private int getMinYVelocity(Area area) {
        return area.yMin;
    }

    private int getMaxYVelocity(Area area) {
        return -1 * area.yMin + 1;
    }
}
