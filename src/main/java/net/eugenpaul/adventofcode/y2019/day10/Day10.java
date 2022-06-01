package net.eugenpaul.adventofcode.y2019.day10;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day10 extends SolutionTemplate {

    @Getter
    private int detected;
    @Getter
    private String a200;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2019/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        Set<SimplePos> asteroids = StringConverter.toSet(eventData, '#');

        int maxDetected = 0;
        SimplePos bestPosition = new SimplePos(-1, -1);

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                int currentDetected = getDetect(asteroids, new SimplePos(x, y), maxX, maxY);
                if (currentDetected > maxDetected) {
                    bestPosition = new SimplePos(x, y);
                    maxDetected = currentDetected;
                }
            }
        }

        detected = maxDetected;

        a200 = doVaporization(asteroids, bestPosition, maxX, maxY);

        logger.log(Level.INFO, () -> "detected    : " + getDetected());
        logger.log(Level.INFO, () -> "a200    : " + getA200());

        return true;
    }

    private int getDetect(Set<SimplePos> asteroids, SimplePos pos, int maxX, int maxY) {
        return getVisibleAsteroids(asteroids, pos, maxX, maxY).size();
    }

    private Set<SimplePos> getVisibleAsteroids(Set<SimplePos> asteroids, SimplePos pos, int maxX, int maxY) {

        if (!asteroids.contains(pos)) {
            return Collections.emptySet();
        }

        Set<SimplePos> detectable = new HashSet<>(asteroids);
        detectable.remove(pos);

        for (var checkPos : asteroids) {
            if (pos.equals(checkPos)) {
                continue;
            }

            SimplePos s = checkPos.subNew(pos);
            s = min(s);
            SimplePos notVisible = checkPos.addNew(s);
            while (isOnMap(notVisible, maxX, maxY)) {
                detectable.remove(notVisible);
                notVisible.add(s);
            }
        }

        return detectable;
    }

    private SimplePos min(SimplePos s) {
        int gcf = MathHelper.gcf(s.getX(), s.getY());
        if (gcf < 0) {
            return new SimplePos(s.getX() / -gcf, s.getY() / -gcf);
        }
        return new SimplePos(s.getX() / gcf, s.getY() / gcf);
    }

    private boolean isOnMap(SimplePos pos, int maxX, int maxY) {
        return pos.getX() >= 0 //
                && pos.getY() >= 0 //
                && pos.getX() <= maxX //
                && pos.getY() <= maxY //
        ;
    }

    private String doVaporization(Set<SimplePos> asteroids, SimplePos pos, int maxX, int maxY) {

        Set<SimplePos> visible = getVisibleAsteroids(asteroids, pos, maxX, maxY);
        int removedByRotation = visible.size();

        // In my example, more than 200 asteroids are disrupted in the first rotation, so the 200-er is also disrupted in the first rotation and is in view.
        if (removedByRotation < 200) {
            return "One rotation is not enough. Make next steps yourself.";
        }

        List<SimplePos> sortedByAngle = visible.stream()//
                .sorted((a, b) -> (getAngle(pos, a) - getAngle(pos, b) > 0) ? 1 : -1)//
                .collect(Collectors.toList())//
        ;

        return "" + (sortedByAngle.get(199).getX() * 100 + sortedByAngle.get(199).getY());
    }

    private double getAngle(SimplePos center, SimplePos pos) {
        SimplePos dif = pos.subNew(center);
        double rad = Math.atan((double) dif.getY() / dif.getX());
        double grad = Math.toDegrees(rad);

        if (dif.getX() >= 0 && dif.getY() < 0) {
            return grad + 90;
        }
        if (dif.getX() >= 0 && dif.getY() >= 0) {
            return grad + 90;
        }
        if (dif.getX() < 0 && dif.getY() >= 0) {
            return grad + 270;
        }
        return grad + 270;
    }

}
