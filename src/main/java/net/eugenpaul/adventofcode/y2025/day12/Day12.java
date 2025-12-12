package net.eugenpaul.adventofcode.y2025.day12;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2025/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<List<String>> data = asListList(eventData);

        List<Integer> shapes = new ArrayList<>();
        for (var shapeData : data.subList(0, data.size() - 1)) {
            shapes.add(shapeData.subList(1, shapeData.size()).stream().mapToInt(v -> 3 - v.replace("#", "").length())
                    .sum());
        }

        for (var all : data.subList(data.size() - 1, data.size())) {
            for (var a : all) {
                String[] d = a.split(":");
                int aArea = toInt(d[0].split("x")[0]) * toInt(d[0].split("x")[1]);
                var s = Arrays.asList(d[1].trim().split(" ")).stream().map(v -> toInt(v)).toList();
                var aAreaNeed = 0;
                for (int i = 0; i < shapes.size(); i++) {
                    aAreaNeed += 9 * s.get(i);
                }

                if (aAreaNeed <= aArea) {
                    response++;
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 (simple way for real Data) " + response);
        return response;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<List<String>> data = asListList(eventData);

        List<Shape> shapes = new ArrayList<>();
        for (var shapeData : data.subList(0, data.size() - 1)) {
            shapes.add(new Shape(shapeData.subList(1, shapeData.size())));
        }

        List<Area> areas = new ArrayList<>();
        for (var all : data.subList(data.size() - 1, data.size())) {
            for (var a : all) {
                areas.add(new Area(a));
            }
        }

        response = areas.stream().filter(v -> isPossible(shapes, v)).count();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public boolean isPossible(List<Shape> shapes, Area a) {
        var aArea = a.w * a.h;
        var aAreaNeed = 0;
        for (int i = 0; i < shapes.size(); i++) {
            aAreaNeed += shapes.get(i).n11.size() * a.s.get(i);
        }
        if (aAreaNeed > aArea) {
            return false;
        }

        var aAreaOpt = (a.w - a.w % 3) * (a.h - a.h % 3);
        var aAreaNeedMax = 0;
        for (int i = 0; i < shapes.size(); i++) {
            aAreaNeedMax += 9 * a.s.get(i);
        }

        if (aAreaNeedMax <= aAreaOpt) {
            return true;
        }
        // will never call for real data. The function is always terminated in the Ifs
        // before for the real data.

        TargetArea area = new TargetArea(a);

        List<Integer> shapesLeft = new ArrayList<>(a.s);
        return isPos(shapes, area, shapesLeft, 0, 0);
    }

    public boolean isPos(List<Shape> shapes, TargetArea area, List<Integer> shapesLeft, int startX, int startY) {
        if (shapesLeft.isEmpty()) {
            return true;
        }

        if (shapesLeft.get(0) == 0) {
            return isPos(shapes.subList(1, shapes.size()), area, shapesLeft.subList(1, shapesLeft.size()), 0, 0);
        }

        boolean firstX = true;
        int curY = startY;
        while (curY <= area.h - 3) {
            int curX = 0;
            if (firstX) {
                curX = startX;
                firstX = false;
            }
            while (curX <= area.w - 3) {
                for (var shape : shapes.get(0).all) {
                    if (area.add(shape, curX, curY)) {
                        shapesLeft.set(0, shapesLeft.get(0) - 1);
                        if (isPos(shapes, area, shapesLeft, curX + 1, curY)) {
                            return true;
                        }
                        shapesLeft.set(0, shapesLeft.get(0) + 1);
                        area.remove(shape, curX, curY);
                    }
                }
                curX++;
            }
            curY++;
        }

        return false;
    }

    private class TargetArea {
        private int w;
        private int h;

        private boolean[][] area;

        public TargetArea(Area a) {
            this.w = a.w;
            this.h = a.h;

            area = new boolean[h][w];
            for (var l : area) {
                Arrays.fill(l, false);
            }
        }

        public boolean cannAdd(Set<SimplePos> shape, int x, int y) {
            for (SimplePos p : shape) {
                int targetX = p.getX() + x;
                int targetY = p.getY() + y;
                if (targetX < 0 || w <= targetX) {
                    return false;
                }
                if (targetY < 0 || h <= targetY) {
                    return false;
                }
                if (area[targetY][targetX]) {
                    return false;
                }
            }
            return true;
        }

        public boolean add(Set<SimplePos> shape, int x, int y) {
            if (cannAdd(shape, x, y)) {
                for (SimplePos p : shape) {
                    int targetX = p.getX() + x;
                    int targetY = p.getY() + y;
                    area[targetY][targetX] = true;
                }
                return true;
            }
            return false;
        }

        public void remove(Set<SimplePos> shape, int x, int y) {
            for (SimplePos p : shape) {
                int targetX = p.getX() + x;
                int targetY = p.getY() + y;
                area[targetY][targetX] = false;
            }
        }

    }

    private class Area {
        private int w;
        private int h;
        private List<Integer> s;

        public Area(String data) {
            String[] d = data.split(":");
            w = toInt(d[0].split("x")[0]);
            h = toInt(d[0].split("x")[1]);
            s = Arrays.asList(d[1].trim().split(" ")).stream().map(v -> toInt(v)).toList();
        }
    }

    @Getter
    private class Shape {
        private Set<SimplePos> n11;
        private Set<SimplePos> n12;
        private Set<SimplePos> n21;
        private Set<SimplePos> n22;
        private Set<SimplePos> n31;
        private Set<SimplePos> n32;
        private Set<SimplePos> n41;
        private Set<SimplePos> n42;

        private Set<Set<SimplePos>> all;

        public Shape(List<String> data) {
            n11 = toSet(data, '#');
            n12 = flipp(n11);
            n21 = toSet(rotateRStrings(data, 1), '#');
            n22 = flipp(n21);
            n31 = toSet(rotateRStrings(data, 2), '#');
            n32 = flipp(n31);
            n41 = toSet(rotateRStrings(data, 3), '#');
            n42 = flipp(n41);
            all = new HashSet<>();
            all.add(n11);
            all.add(n12);
            all.add(n21);
            all.add(n22);
            all.add(n31);
            all.add(n32);
            all.add(n41);
            all.add(n42);
        }

        private Set<SimplePos> flipp(Set<SimplePos> in) {
            Set<SimplePos> out = new HashSet<>();
            for (var a : in) {
                out.add(new SimplePos(2 - a.getX(), 2 - a.getY()));
            }
            return out;
        }
    }

}
