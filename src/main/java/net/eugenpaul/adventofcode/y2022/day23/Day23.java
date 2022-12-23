package net.eugenpaul.adventofcode.y2022.day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day23 extends SolutionTemplate {

    private class Elf {
        private LinkedList<Direction> d = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));
    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2022/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        var area = readMap(eventData);
        var elfCount = area.values().stream().filter(v -> v != null).count();

        SimplePos checkPos = new SimplePos(0, 0);
        for (int i = 0; i < 10; i++) {
            int xMin = area.keySet().stream().mapToInt(v -> v.getX()).min().orElseThrow();
            int xMax = area.keySet().stream().mapToInt(v -> v.getX()).max().orElseThrow();
            int yMin = area.keySet().stream().mapToInt(v -> v.getY()).min().orElseThrow();
            int yMax = area.keySet().stream().mapToInt(v -> v.getY()).max().orElseThrow();

            Set<SimplePos> willMove = new HashSet<>();
            for (int y = yMin; y <= yMax; y++) {
                checkPos.setY(y);

                for (int x = xMin; x <= xMax; x++) {
                    checkPos.setX(x);
                    if (area.get(checkPos) == null) {
                        continue;
                    }
                    var n = checkPos.getNeighbors(true);
                    boolean wm = false;
                    for (SimplePos nPos : n) {
                        if (area.get(nPos) != null) {
                            wm = true;
                            break;
                        }
                    }
                    if (!wm) {
                        continue;
                    }
                    willMove.add(checkPos.copy());
                }

            }

            Map<SimplePos, Integer> moveTarget = new HashMap<>();
            for (SimplePos moveElf : willMove) {
                var d = getMoving(area, moveElf);
                if (d == null) {
                    continue;
                }
                moveTarget.compute(moveElf.moveNew(d), (k, v) -> (v == null) ? 1 : (v + 1));
            }

            Map<SimplePos, Elf> areaCopy = new HashMap<>(area);

            for (SimplePos moveElf : willMove) {
                var d = getMoving(area, moveElf);
                if (d == null) {
                    continue;
                }
                if (moveTarget.get(moveElf.moveNew(d)) == 1) {
                    var elf = areaCopy.remove(moveElf);
                    areaCopy.put(moveElf.moveNew(d), elf);
                }
            }
            area = areaCopy;

            areaCopy.values().forEach(v -> {
                var b = v.d.pollFirst();
                v.d.addLast(b);
            });
        }

        int xMin = area.keySet().stream().mapToInt(v -> v.getX()).min().orElseThrow();
        int xMax = area.keySet().stream().mapToInt(v -> v.getX()).max().orElseThrow();
        int yMin = area.keySet().stream().mapToInt(v -> v.getY()).min().orElseThrow();
        int yMax = area.keySet().stream().mapToInt(v -> v.getY()).max().orElseThrow();

        return (xMax - xMin + 1) * (yMax - yMin + 1) - elfCount;
    }

    private Direction getMoving(Map<SimplePos, Elf> area, SimplePos moveElf) {
        var elf = area.get(moveElf);

        for (var d : elf.d) {
            switch (d) {
            case N, S:
                if (area.get(moveElf.moveNew(d)) == null //
                        && area.get(moveElf.moveNew(d).move(Direction.E)) == null //
                        && area.get(moveElf.moveNew(d).move(Direction.W)) == null //
                ) {
                    return d;
                }
                break;
            case W, E:
                if (area.get(moveElf.moveNew(d)) == null //
                        && area.get(moveElf.moveNew(d).move(Direction.N)) == null //
                        && area.get(moveElf.moveNew(d).move(Direction.S)) == null //
                ) {
                    return d;
                }
                break;
            default:
                throw new IllegalArgumentException();
            }
        }
        return null;
    }

    private long doPuzzle2(List<String> eventData) {
        var area = readMap(eventData);

        SimplePos checkPos = new SimplePos(0, 0);
        for (int i = 0;; i++) {
            int xMin = area.keySet().stream().mapToInt(v -> v.getX()).min().orElseThrow();
            int xMax = area.keySet().stream().mapToInt(v -> v.getX()).max().orElseThrow();
            int yMin = area.keySet().stream().mapToInt(v -> v.getY()).min().orElseThrow();
            int yMax = area.keySet().stream().mapToInt(v -> v.getY()).max().orElseThrow();

            Set<SimplePos> willMove = new HashSet<>();
            for (int y = yMin; y <= yMax; y++) {
                checkPos.setY(y);

                for (int x = xMin; x <= xMax; x++) {
                    checkPos.setX(x);
                    if (area.get(checkPos) == null) {
                        continue;
                    }
                    var n = checkPos.getNeighbors(true);
                    boolean wm = false;
                    for (SimplePos nPos : n) {
                        if (area.get(nPos) != null) {
                            wm = true;
                            break;
                        }
                    }
                    if (!wm) {
                        continue;
                    }
                    willMove.add(checkPos.copy());
                }

            }

            Map<SimplePos, Integer> moveTarget = new HashMap<>();
            for (SimplePos moveElf : willMove) {
                var d = getMoving(area, moveElf);
                if (d == null) {
                    continue;
                }
                moveTarget.compute(moveElf.moveNew(d), (k, v) -> (v == null) ? 1 : (v + 1));
            }

            Map<SimplePos, Elf> areaCopy = new HashMap<>(area);

            boolean isMove = false;
            for (SimplePos moveElf : willMove) {
                var d = getMoving(area, moveElf);
                if (d == null) {
                    continue;
                }
                if (moveTarget.get(moveElf.moveNew(d)) == 1) {
                    var elf = areaCopy.remove(moveElf);
                    areaCopy.put(moveElf.moveNew(d), elf);
                    isMove = true;
                }
            }

            if (!isMove) {
                return i + 1;
            }

            area = areaCopy;

            areaCopy.values().forEach(v -> {
                var b = v.d.pollFirst();
                v.d.addLast(b);
            });
        }
    }

    private Map<SimplePos, Elf> readMap(List<String> eventData) {
        return MapOfSimplePos.initMap(eventData, v -> {
            switch (v) {
            case '.':
                return null;
            case '#':
                return new Elf();
            default:
                throw new IllegalArgumentException();
            }
        });
    }
}
