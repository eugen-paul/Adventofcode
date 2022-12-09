package net.eugenpaul.adventofcode.y2022.day9;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Movement {
        private Direction dir;
        private int count;

        private static Movement fromString(String data) {
            return new Movement(//
                    Direction.fromUdrl(data.charAt(0)), //
                    Integer.parseInt(data.substring(2)) //
            );
        }
    }

    @Getter
    private int tailPositions;
    @Getter
    private int tailPositions10;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2022/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var moves = eventData.stream().map(Movement::fromString).collect(Collectors.toList());

        tailPositions = doPuzzle1(moves);
        tailPositions10 = doPuzzle2(moves);

        logger.log(Level.INFO, () -> "tailPositions : " + getTailPositions());
        logger.log(Level.INFO, () -> "tailPositions10 : " + getTailPositions10());

        return true;
    }

    private int doPuzzle1(List<Movement> moves) {
        return move(moves, 2);
    }

    private int doPuzzle2(List<Movement> moves) {
        return move(moves, 10);
    }

    private int move(List<Movement> moves, int len) {
        Set<SimplePos> tailPos = new HashSet<>();

        Map<Integer, SimplePos> rope = new HashMap<>();
        for (int i = 0; i < len; i++) {
            rope.put(i, new SimplePos(0, 0));
        }

        for (var move : moves) {
            for (int i = 0; i < move.count; i++) {
                // move head and rewrite it in the map
                rope.get(0).move(move.dir);

                // compute positions of all nodes in the rope
                for (int n = 1; n < len; n++) {
                    var a = rope.get(n - 1);
                    var b = rope.get(n);
                    rope.put(n, doMove(a, b));
                }
                tailPos.add(rope.get(len - 1));
            }
        }

        return tailPos.size();
    }

    private SimplePos doMove(SimplePos head, SimplePos tail) {
        int deltaX = Math.abs(head.getX() - tail.getX());
        int deltaY = Math.abs(head.getY() - tail.getY());

        // tail moves one step diagonally
        if (deltaX == 2 && deltaY == 2) {
            return new SimplePos((head.getX() + tail.getX()) / 2, (head.getY() + tail.getY()) / 2);
        }

        // tail moves one step diagonally. Take over y-position from head.
        if (deltaX == 2 && deltaY == 1) {
            return new SimplePos((head.getX() + tail.getX()) / 2, head.getY());
        }

        // tail moves one step to head in the same direction
        if (deltaX == 2 && deltaY == 0) {
            return new SimplePos((head.getX() + tail.getX()) / 2, tail.getY());
        }

        // tail moves one step diagonally. Take over x-position from head.
        if (deltaX == 1 && deltaY == 2) {
            return new SimplePos(head.getX(), (head.getY() + tail.getY()) / 2);
        }

        // tail moves one step to head in the same direction
        if (deltaX == 0 && deltaY == 2) {
            return new SimplePos(tail.getX(), (head.getY() + tail.getY()) / 2);
        }

        // don't move tail
        return tail;
    }

}
