package net.eugenpaul.adventofcode.y2022.day20;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
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

        part1 = doPuzzleAlt(eventData, 1, 1);
        part2 = doPuzzle(eventData, 10, 811589153);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle(List<String> eventData, int rounds, int mult) {

        CircleMemory<Long> mem = new CircleMemory<>();
        List<CircleMemory<Long>.CirclePosition> pos = new LinkedList<>();
        for (String data : eventData) {
            pos.add(mem.addLast(Long.parseLong(data) * mult));
        }

        for (int i = 0; i < rounds; i++) {
            for (var currentElement : pos) {
                var prev = mem.removeAndGetPrev(currentElement);
                var newPos = mem.moveNext(prev, (int) (currentElement.getData() % (pos.size() - 1)));
                mem.add(currentElement, newPos);
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

    @AllArgsConstructor
    private class Data {
        private long value;
    }

    private long doPuzzleAlt(List<String> eventData, int rounds, int mult) {

        List<Data> mem = new LinkedList<>();
        List<Data> order = new LinkedList<>();
        for (String data : eventData) {
            var dataEl = new Data(Long.parseLong(data) * mult);
            mem.add(dataEl);
            order.add(dataEl);
        }

        for (int i = 0; i < rounds; i++) {
            doRound(mem, order);
        }

        int zeroPos = getZeroPos(mem);

        int pos1000 = (zeroPos + 1000) % mem.size();
        int pos2000 = (zeroPos + 2000) % mem.size();
        int pos3000 = (zeroPos + 3000) % mem.size();

        return mem.get(pos1000).value + mem.get(pos2000).value + mem.get(pos3000).value;
    }

    private void doRound(List<Data> mem, List<Data> order) {
        for (Data data : order) {
            int pos = 0;
            Data currentEl = null;
            var it = mem.iterator();
            while (it.hasNext()) {
                var el = it.next();
                if (el == data) {
                    currentEl = el;
                    it.remove();
                    break;
                }
                pos++;
            }

            if (currentEl == null) {
                throw new IllegalArgumentException();
            }

            int newPos = (int) ((currentEl.value + pos) % mem.size());
            if (newPos < 0) {
                newPos += mem.size();
            }

            mem.add(newPos, currentEl);
        }
    }

    private int getZeroPos(List<Data> mem) {
        int zeroPos = 0;
        for (Data data : mem) {
            if (data.value == 0) {
                break;
            }
            zeroPos++;
        }
        return zeroPos;
    }
}
