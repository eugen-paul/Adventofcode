package net.eugenpaul.adventofcode.y2025.day9;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Line2d;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2025/day9/puzzle1.txt");
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

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        Set<SimplePos> m = new HashSet<>();
        for (var d : eventData) {
            m.add(SimplePos.fromData(d, ","));
        }

        for (var a : m) {
            for (var b : m) {
                response = max(//
                        response, //
                        (long) (Math.abs(a.getX() - b.getX()) + 1) * (long) (Math.abs(a.getY() - b.getY()) + 1));
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        List<SimplePos> m = new ArrayList<>();
        for (var d : eventData) {
            var p = SimplePos.fromData(d, ",");
            m.add(p);

            minX = min(minX, p.getX());
            minY = min(minY, p.getY());

            maxX = max(maxX, p.getY());
            maxY = max(maxY, p.getY());
        }

        List<Line2d> lines = new ArrayList<>();
        for (int i = 1; i < m.size(); i++) {
            Line2d l = Line2d.fromPointPoint(m.get(i - 1), m.get(i));
            lines.add(l.norm());
        }
        Line2d l = Line2d.fromPointPoint(m.getLast(), m.getFirst());
        lines.add(l.norm());

        lines.sort((a, b) -> Long.compare(a.getPointX(), b.getPointX()));
        List<Line2d> hLines = lines.stream().filter(v -> v.getDeltaY() == 0).sorted((a, b) -> Long.compare(a.getPointY(), b.getPointY())).toList();
        List<Line2d> vLines = lines.stream().filter(v -> v.getDeltaX() == 0).toList();

        for (int i = 0; i < m.size(); i++) {
            for (int k = i + 1; k < m.size(); k++) {
                var a = m.get(i);
                var b = m.get(k);

                int fromX = min(a.getX(), b.getX());
                int toX   = max(a.getX(), b.getX());
                int fromY = min(a.getY(), b.getY());
                int toY   = max(a.getY(), b.getY());

                var lTop = Line2d.fromPointPoint(new SimplePos(fromX+1, fromY), new SimplePos(toX-1, fromY));
                var lBot = Line2d.fromPointPoint(new SimplePos(fromX+1, toY), new SimplePos(toX-1, toY));
                var lLft = Line2d.fromPointPoint(new SimplePos(fromX, fromY+1), new SimplePos(fromX, toY-1));
                var lRht = Line2d.fromPointPoint(new SimplePos(toX, fromY+1), new SimplePos(toX, toY-1));

                boolean ok = isIn(hLines, vLines, fromX, fromY)
                          && isIn(hLines, vLines, toX, fromY)
                          && isIn(hLines, vLines, toX, toY)
                          && isIn(hLines, vLines, fromX, toY)
                          ;

                ok = ok && vLines.stream().noneMatch(v -> lineInterception(v,lTop));
                ok = ok && vLines.stream().noneMatch(v -> lineInterception(v,lBot));
                ok = ok && hLines.stream().noneMatch(h -> lineInterception(h,lLft));
                ok = ok && hLines.stream().noneMatch(h -> lineInterception(h,lRht));

                if (ok) {
                    response = max(//
                            response, //
                            (long) (Math.abs(a.getX() - b.getX()) + 1) * (long) (Math.abs(a.getY() - b.getY()) + 1));
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public boolean lineInterception(Line2d a, Line2d b){
        if(a.getDeltaY() == 0){
            //a is h
            return a.getPointX() < b.getPointX() && b.getPointX() < a.getPointX() + a.getDeltaX()
                && b.getPointY() < a.getPointY() && a.getPointY() < b.getPointY() + b.getDeltaY();
        }
        //a is v
        return a.getPointY() < b.getPointY() && b.getPointY() < a.getPointY() + a.getDeltaY()
            && b.getPointX() < a.getPointX() && a.getPointX() < b.getPointX() + b.getDeltaX();
    }
    
    private boolean isIn(List<Line2d> hLines, List<Line2d> curVLines, long x, long y) {
        if (curVLines.stream().anyMatch(v -> v.isPointOnSegment(new SimplePos((int) x, (int) y)))) {
            return true;
        }
        if (hLines.stream().anyMatch(v -> v.isPointOnSegment(new SimplePos((int) x, (int) y)))) {
            return true;
        }

        var lines = hLines.stream() //
                .filter(v -> v.getPointY() < y) //
                .sorted((a, b) -> Long.compare(a.getPointX(), b.getPointX())) //
                .toList();
        var cnt = 0;
        for (var ll : lines) {
            if (ll.getPointX() <= x && ll.getPointX() + ll.getDeltaX() > x) {
                cnt++;
            }
        }
        return cnt % 2 == 1;
    }
}
