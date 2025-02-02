package net.eugenpaul.adventofcode.y2023.day18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private boolean ext = true;

    @Data
    private class Line {
        Direction d;
        SimplePos start;
        SimplePos end;

        SimplePos left;
        SimplePos right;

        public Line(Direction d, SimplePos start, SimplePos end) {
            this.d = d;
            this.start = start;
            this.end = end;
            if (d == Direction.W) {
                left = end;
                right = start;
            } else {
                left = start;
                right = end;
            }
        }
    }

    Set<SimplePos> g = new HashSet<>();
    boolean debug = false;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2023/day18/puzzle1.txt");
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

        g.clear();

        if (ext) {
            response = computeCompl(eventData);
        } else {
            response = computeSimple(eventData);
        }

        if (debug) {
            MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(g));
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long computeSimple(List<String> eventData) {
        long response;
        Set<SimplePos> m = new HashSet<>();
        SimplePos start = new SimplePos(0, 0);
        m.add(start);
        for (String data : eventData) {
            var splits = data.split(" ");
            var d = Direction.fromUdrl(splits[0].charAt(0));
            var l = Integer.parseInt(splits[1]);
            for (int i = 0; i < l; i++) {
                start = start.moveNew(d);
                m.add(start);
            }
        }

        var toCheck = new LinkedList<SimplePos>();
        toCheck.add(new SimplePos(1, 1));
        while (!toCheck.isEmpty()) {
            for (var nb : toCheck.removeFirst().getNeighbors(false)) {
                if (!m.contains(nb)) {
                    toCheck.add(nb);
                    m.add(nb);
                }
            }
        }

        response = m.size();
        return response;
    }

    private long computeCompl(List<String> eventData) {
        long response;

        var lines = new ArrayList<Line>();
        var start = new SimplePos(0, 0);
        for (String data : eventData) {
            var splits = data.split(" ");
            var dir = Direction.fromUdrl(splits[0].charAt(0));
            var len = Integer.parseInt(splits[1]);
            var end = start.copy().move(dir, len);
            lines.add(new Line(dir, start, end));
            start = end.copy();
        }

        response = compute(lines);

        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        debug = false;

        var lines = new ArrayList<Line>();
        var start = new SimplePos(0, 0);
        for (String data : eventData) {
            var params = data.split(" ")[2];
            var dir = switch (params.charAt(7)) {
            case '0' -> Direction.E;
            case '1' -> Direction.S;
            case '2' -> Direction.W;
            case '3' -> Direction.N;
            default -> throw new IllegalArgumentException(data);
            };
            var len = Integer.parseInt(params.substring(2, 7), 16);
            var end = start.copy().move(dir, len);
            lines.add(new Line(dir, start, end));
            start = end.copy();
        }

        long response = compute(lines);

        // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(m));

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long compute(ArrayList<Line> lines) {
        long response = 0;
        for (int i = 0; i < lines.size(); i++) {
            var lst = (i == 0) ? lines.getLast() : lines.get(i - 1);
            var cur = lines.get(i);
            var nxt = lines.get((i + 1) % lines.size());

            if (cur.d == Direction.E) {
                response += getCM(cur, lines);
            } else if (cur.d == Direction.W) {
                response += cur.getEnd().manhattanDistance(cur.getStart()) - 1;
                if (debug) {
                    for (int x = cur.getLeft().getX() + 1; x < cur.getRight().getX(); x++) {
                        g.add(new SimplePos(x, cur.getLeft().getY()));
                    }
                }
            } else {
                if ((lst.d == Direction.E && cur.d == Direction.N && nxt.d == Direction.W) //
                        || (lst.d == Direction.E && cur.d == Direction.N && nxt.d == Direction.E)//
                        || (lst.d == Direction.E && cur.d == Direction.S && nxt.d == Direction.E)//
                        || (lst.d == Direction.W && cur.d == Direction.S && nxt.d == Direction.E)) {
                    response += getCMV(cur, lines);
                }

                if ((lst.d == Direction.E && cur.d == Direction.S && nxt.d == Direction.W) //
                        || (lst.d == Direction.W && cur.d == Direction.N && nxt.d == Direction.W) //
                        || (lst.d == Direction.W && cur.d == Direction.N && nxt.d == Direction.E) //
                        || (lst.d == Direction.W && cur.d == Direction.S && nxt.d == Direction.W) //
                ) {
                    response += cur.getEnd().manhattanDistance(cur.getStart()) + 1;
                    if (debug) {
                        for (int y = cur.getStart().getY(); y <= cur.getEnd().getY(); y++) {
                            g.add(new SimplePos(cur.getLeft().getX(), y));
                        }
                        for (int y = cur.getEnd().getY(); y <= cur.getStart().getY(); y++) {
                            g.add(new SimplePos(cur.getLeft().getX(), y));
                        }
                    }
                }
            }
        }
        return response;
    }

    private long getCM(Line line, ArrayList<Line> all) {
        if (line.getStart().getX() + 1 == line.getEnd().getX()) {
            return 0;
        }

        long fromX = line.getStart().getX() + 1;
        long toX = line.getEnd().getX() - 1;
        long minY = line.getStart().getY() + 1;

        List<Line> col = getCol(fromX, toX, minY, all);
        int firstY = col.get(0).getEnd().getY();

        long response = (toX - fromX + 1) * (firstY - minY + 1);

        if (debug) {
            for (int y = (int) minY - 1; y < col.get(0).getEnd().getY(); y++) {
                for (int x = (int) fromX; x <= toX; x++) {
                    g.add(new SimplePos(x, y));
                }
            }
        }

        if (fromX < col.get(0).getLeft().getX()) {
            response += getCM(//
                    new Line( //
                            line.d, //
                            new SimplePos((int) fromX - 1, col.get(0).getLeft().getY()), //
                            new SimplePos(col.get(0).getLeft().getX(), col.get(0).getLeft().getY())//
                    ), //
                    all);
        }

        for (int i = 1; i < col.size(); i++) {
            response += getCM(//
                    new Line( //
                            line.d, //
                            new SimplePos(col.get(i - 1).getRight().getX(), col.get(0).getLeft().getY()), //
                            new SimplePos(col.get(i).getLeft().getX(), col.get(0).getLeft().getY()) //
                    ), //
                    all);
        }

        if (col.get(0).getRight().getX() < toX) {
            response += getCM(//
                    new Line( //
                            line.d, //
                            new SimplePos(col.get(0).getRight().getX(), col.get(0).getLeft().getY()), //
                            new SimplePos((int) toX + 1, col.get(0).getLeft().getY())//
                    ), //
                    all);
        }

        return response;
    }

    private long getCMV(Line line, ArrayList<Line> all) {
        long fromX = line.getStart().getX();
        long toX = fromX;
        long minY = Math.max(line.end.getY(), line.start.getY()) + 1L;

        List<Line> col = getCol(fromX, toX, minY, all);

        if (debug) {
            for (int y = Math.min(line.end.getY(), line.start.getY()); y < col.get(0).getStart().getY(); y++) {
                g.add(new SimplePos((int) toX, y));
            }
        }

        return col.get(0).getStart().getY() - Math.min(line.end.getY(), line.start.getY());
    }

    private List<Line> getCol(long fromX, long toX, long minY, ArrayList<Line> all) {
        var response = new ArrayList<Line>();

        for (var line : all) {
            boolean test1 = line.d == Direction.E || line.d == Direction.W;
            boolean test2 = line.start.getY() >= minY;
            boolean test3 = !(line.right.getX() < fromX || toX < line.left.getX());
            if (test1 && test2 && test3) {
                response.add(line);
            }
        }

        response.sort((a, b) -> Integer.compare(a.start.getY(), b.start.getY()));
        int firstY = response.get(0).getEnd().getY();
        response.removeIf(v -> v.end.getY() != firstY);

        return response;
    }

}
