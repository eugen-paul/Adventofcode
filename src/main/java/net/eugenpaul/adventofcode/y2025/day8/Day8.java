package net.eugenpaul.adventofcode.y2025.day8;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.UnionFind;
import net.eugenpaul.adventofcode.helper.UnionSetsByRank;

public class Day8 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2025/day8/puzzle1.txt");
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

    private record InnerDay8(double dist, int a, int b) {
    }

    public int s1 = 1000;

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Pos3d> all = eventData.stream().map(v -> Pos3d.fromPattern(v, ",")).toList();
        List<InnerDay8> dd = new ArrayList<>();

        for (int a = 0; a < all.size(); a++) {
            for (int b = a + 1; b < all.size(); b++) {
                var ak = all.get(a);
                var bk = all.get(b);
                dd.add(new InnerDay8(ak.euclideanDistance(bk), a, b));
            }
        }

        dd.sort((a, b) -> Double.compare(a.dist, b.dist));

        UnionSetsByRank uf = new UnionSetsByRank(all.size());

        for (int i = 0; i < s1; i++) {
            InnerDay8 o = dd.get(i);
            uf.union(o.a, o.b);
        }

        HashMap<Integer, Long> mm = new HashMap<>();
        for (int i = 0; i < all.size(); i++) {
            mm.compute(uf.find(i), (k, v) -> v == null ? 1 : v + 1);
        }
        response = mm.values().stream()//
                .sorted((a, b) -> Long.compare(b, a))//
                .limit(3)//
                .reduce(1L, (a, b) -> a * b);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        List<Pos3d> all = eventData.stream().map(v -> Pos3d.fromPattern(v, ",")).toList();
        List<InnerDay8> dd = new ArrayList<>();

        for (int a = 0; a < all.size(); a++) {
            for (int b = a + 1; b < all.size(); b++) {
                var ak = all.get(a);
                var bk = all.get(b);
                dd.add(new InnerDay8(ak.euclideanDistance(bk), a, b));
            }
        }

        dd.sort((a, b) -> Double.compare(a.dist, b.dist));

        UnionFind uf = new UnionFind(all.size());

        for (int i = 0; i < s1; i++) {
            InnerDay8 o = dd.get(i);
            uf.union(o.a, o.b);
        }

        HashMap<Integer, Long> mm = new HashMap<>();
        for (int i = 0; i < all.size(); i++) {
            mm.compute(uf.find(i), (k, v) -> v == null ? 1 : v + 1);
        }
        var sh = mm.values().stream().sorted((a, b) -> Long.compare(b, a)).toList();
        response = 1;
        for (int i = 0; i < 3; i++) {
            response *= sh.get(i);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Pos3d> all = eventData.stream().map(v -> Pos3d.fromPattern(v, ",")).toList();
        List<InnerDay8> dd = new ArrayList<>();

        for (int a = 0; a < all.size(); a++) {
            for (int b = a + 1; b < all.size(); b++) {
                var ak = all.get(a);
                var bk = all.get(b);
                dd.add(new InnerDay8(ak.euclideanDistance(bk), a, b));
            }
        }

        dd.sort((a, b) -> Double.compare(a.dist, b.dist));

        UnionSetsByRank uf = new UnionSetsByRank(all.size());

        for (int i = 0; i < dd.size(); i++) {
            InnerDay8 o = dd.get(i);
            uf.union(o.a, o.b);

            if (uf.getComponents() == 1) {
                response = all.get(o.a).getX() * all.get(o.b).getX();
                break;
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
