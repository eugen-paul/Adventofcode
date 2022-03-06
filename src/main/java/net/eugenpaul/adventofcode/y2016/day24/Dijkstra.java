package net.eugenpaul.adventofcode.y2016.day24;

import java.util.Arrays;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Dijkstra {

    @AllArgsConstructor
    @Data
    private static class Pos {
        int x;
        int y;
        int steps;
    }

    private LinkedList<Pos> steps;
    private DuctLayout area;
    private boolean[][] visited;

    public Dijkstra(DuctLayout area) {
        this.area = area;
        steps = new LinkedList<>();
        visited = new boolean[area.getW()][area.getH()];

        for (boolean[] line : visited) {
            Arrays.fill(line, false);
        }
    }

    public int getSteps(int fromX, int fromY, int toX, int toY) {
        Pos to = new Pos(toX, toY, 0);
        Pos from = new Pos(fromX, fromY, 0);

        // pathfinding
        while (to.getX() != from.getX() || to.getY() != from.getY()) {
            checkAndAddNextSteps(area, from);
            from = steps.poll();
        }

        return from.getSteps();
    }

    private void checkAndAddNextSteps(DuctLayout area, Pos src) {
        checkAndAdd(area, src, src.x - 1, src.y);
        checkAndAdd(area, src, src.x + 1, src.y);
        checkAndAdd(area, src, src.x, src.y - 1);
        checkAndAdd(area, src, src.x, src.y + 1);
    }

    private void checkAndAdd(DuctLayout area, Pos src, int targetX, int targetY) {
        if (!area.isOk(targetX, targetY) || visited[targetX][targetY]) {
            return;
        }

        steps.add(new Pos(targetX, targetY, src.steps + 1));
        visited[targetX][targetY] = true;
    }
}
