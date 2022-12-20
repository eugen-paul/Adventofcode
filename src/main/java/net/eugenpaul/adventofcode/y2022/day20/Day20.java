package net.eugenpaul.adventofcode.y2022.day20;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.CircleMemory;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day20 extends SolutionTemplate {

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2022/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        CircleMemory<Long> mem = new CircleMemory<>();
        List<CircleMemory<Long>.CirclePosition> pos = new LinkedList<>();
        for (String data : eventData) {
            pos.add(mem.addLast(Long.parseLong(data)));
        }

        part1 = doPuzzle(mem, pos, 1);

        mem = new CircleMemory<>();
        pos = new LinkedList<>();
        for (String data : eventData) {
            pos.add(mem.addLast(Long.parseLong(data) * 811589153L));
        }
        part2 = doPuzzle(mem, pos, 10);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle(CircleMemory<Long> mem, List<CircleMemory<Long>.CirclePosition> pos, int rounds) {
        for (int i = 0; i < rounds; i++) {
            for (var p : pos) {
                CircleMemory<Long>.CirclePosition mp = p;
                long moveSteps = mp.getData();
                while (moveSteps < 0) {
                    moveSteps = moveSteps % (pos.size() - 1);
                    moveSteps += pos.size() - 1;
                }
                while (moveSteps >= pos.size()) {
                    moveSteps = moveSteps % (pos.size() - 1);
                }
                if (moveSteps == 0) {
                    continue;
                }
                CircleMemory<Long>.CirclePosition n = mem.moveNext(mp, (int) moveSteps);
                mem.removeAndMoveNext(mp);
                mem.add(p, n);
            }
        }

        for (var cp : pos) {
            if (cp.getData() == 0) {
                var cp1000 = mem.moveNext(cp, 1000);
                var cp2000 = mem.moveNext(cp, 2000);
                var cp3000 = mem.moveNext(cp, 3000);

                return cp1000.getData() + cp2000.getData() + cp3000.getData();
            }
        }

        throw new IllegalArgumentException();
    }
}
