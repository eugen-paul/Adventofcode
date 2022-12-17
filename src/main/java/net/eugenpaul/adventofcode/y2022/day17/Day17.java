package net.eugenpaul.adventofcode.y2022.day17;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2022/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData, 10000);
        // part2 = doPuzzle2(eventData, 1000000000000L);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(String eventData) {
        List<List<SimplePos>> allShapes = initShapes();
        Map<SimplePos, Boolean> area = new HashMap<>();
        area.put(new SimplePos(0, 0), true);
        area.put(new SimplePos(1, 0), true);
        area.put(new SimplePos(2, 0), true);
        area.put(new SimplePos(3, 0), true);
        area.put(new SimplePos(4, 0), true);
        area.put(new SimplePos(5, 0), true);
        area.put(new SimplePos(6, 0), true);

        int count = 0;
        int jet = 0;
        int shapeI = 0;
        List<SimplePos> currentShape = allShapes.get(shapeI % allShapes.size());
        shapeI++;
        currentShape = setStartY(0, currentShape);

        while (count < 2022) {
            char j = eventData.charAt(jet % eventData.length());
            switch (j) {
            case '<':
                currentShape = moveLeft(currentShape, area);
                break;
            case '>':
                currentShape = moveRight(currentShape, area);
                break;
            default:
                throw new IllegalArgumentException("illegal jet: " + j);
            }
            if (checkMoveDown(area, currentShape)) {
                currentShape = moveDown(currentShape);
            } else {
                addToMap(area, currentShape);
                currentShape = allShapes.get(shapeI);
                int minAreay = getMinY(area);
                currentShape = setStartY(minAreay, currentShape);
                shapeI = (shapeI + 1) % allShapes.size();
                count++;
                if (count % 50 == 0) {
                    reduceMap(area);
                }
            }
            jet++;
        }

        return getMinY(area) * -1L;
    }

    private long doPuzzle2(String eventData, long rounds) {
        logger.log(Level.INFO, () -> "-------- new game --------");
        List<List<SimplePos>> allShapes = initShapes();
        Map<SimplePos, Boolean> area = new HashMap<>();
        Map<String, List<Long>> hashs = new HashMap<>();
        area.put(new SimplePos(0, 0), true);
        area.put(new SimplePos(1, 0), true);
        area.put(new SimplePos(2, 0), true);
        area.put(new SimplePos(3, 0), true);
        area.put(new SimplePos(4, 0), true);
        area.put(new SimplePos(5, 0), true);
        area.put(new SimplePos(6, 0), true);

        long count = 0;
        int jet = 0;
        int shapeI = 0;
        List<SimplePos> currentShape = allShapes.get(shapeI);
        shapeI++;
        currentShape = setStartY(0, currentShape);

        String currentHash = "";
        int hashCount = 0;
        int falling = 0;
        long lastH = 0;

        while (count < rounds) {
            char j = eventData.charAt(jet);
            switch (j) {
            case '<':
                currentShape = moveLeft(currentShape, area);
                break;
            case '>':
                currentShape = moveRight(currentShape, area);
                break;
            default:
                throw new IllegalArgumentException("illegal jet: " + j);
            }
            if (checkMoveDown(area, currentShape)) {
                currentShape = moveDown(currentShape);
                falling++;
            } else {
                addToMap(area, currentShape);
                currentShape = allShapes.get(shapeI);
                int minAreay = getMinY(area);
                currentShape = setStartY(minAreay, currentShape);
                count++;

                /**
                 * The output shows us that there is wine repetition of the data after 1817 steps. The new altitude is always 2738 greater than the altitude
                 * logged before 1745.
                 * 
                 * The following calculation for the determination of the solution:
                 * 
                 * limtRounds - first repetition
                 * 
                 * 1000000000000 - 1817 = 999.999.998.183
                 * 
                 * 999.999.998.183 /1745 = 573.065.901
                 * 
                 * 573,065,901 is the number of complete repetitions up to 10000000000.
                 * 
                 * 999.999.998.183 mod 1745 = 938
                 * 
                 * 938 is the additional rounds we need to skip to calculate better.
                 * 
                 * 1817 + 938 = 2.755
                 * 
                 * We calculate the final height from round 2.755
                 * 
                 * Height in round 2.755 is 4305.
                 * 
                 * Result: 573,065,901*2738 + 4305 = 1,569,054,441,243
                 */
                currentHash = jet + "";
                if (hashs.containsKey(currentHash)) {
                    long currH = (getMinY(area) * -1L);
                    long delta = currH - hashs.get(currentHash).get(1);
                    long lastCount = hashs.get(currentHash).get(0);
                    long deltaCount = count - lastCount;
                    System.out.println(jet + " " + shapeI + " " + count + " " + lastCount + " " + deltaCount + " " + currH + " " + hashs.get(currentHash).get(1)
                            + " " + delta);
                    hashs.put(currentHash, List.of(count, currH));
                } else {
                    long currH = (getMinY(area) * -1L);
                    hashs.put(currentHash, List.of(count, currH));
                }

                shapeI = (shapeI + 1) % allShapes.size();

                falling = 0;
                if (count % 50 == 0) {
                    reduceMap(area);
                }
            }
            jet = (jet + 1) % eventData.length();
        }

        return getMinY(area) * -1L;
    }

    private void reduceMap(Map<SimplePos, Boolean> area) {
        long minY = area.keySet().stream()//
                .mapToInt(SimplePos::getY)//
                .min().orElseThrow();
        long maxY = area.keySet().stream()//
                .mapToInt(SimplePos::getY)//
                .max().orElseThrow();

        long delta = maxY - minY;
        if (delta > 50) {
            area.entrySet().removeIf(v -> v.getKey().getY() > minY + 50);
        }
    }

    private List<SimplePos> setStartY(int minAreaY, List<SimplePos> shape) {
        int maxShapeY = shape.stream()//
                .mapToInt(SimplePos::getY)//
                .max().orElseThrow();

        int delta = minAreaY - 4 - maxShapeY;
        var copyShape = copyShape(shape);
        for (var pos : copyShape) {
            pos.setY(pos.getY() + delta);
        }
        return copyShape;
    }

    private int getMinY(Map<SimplePos, Boolean> area) {
        return area.keySet().stream()//
                .mapToInt(SimplePos::getY)//
                .min().orElseThrow();
    }

    private void addToMap(Map<SimplePos, Boolean> area, List<SimplePos> shape) {
        for (var pos : shape) {
            if (area.containsKey(pos)) {
                throw new IllegalArgumentException(shape.toString());
            }
            area.put(pos, true);
        }
    }

    private boolean checkMoveDown(Map<SimplePos, Boolean> area, List<SimplePos> shape) {
        var copyShape = copyShape(shape);
        for (var pos : copyShape) {
            pos.setY(pos.getY() + 1);
            if (area.containsKey(pos)) {
                return false;
            }
        }
        return true;
    }

    private List<SimplePos> moveDown(List<SimplePos> shape) {
        var copyShape = copyShape(shape);
        for (var pos : copyShape) {
            pos.setY(pos.getY() + 1);
        }
        return copyShape;
    }

    private List<SimplePos> moveLeft(List<SimplePos> shape, Map<SimplePos, Boolean> area) {
        var copyShape = copyShape(shape);
        for (var pos : copyShape) {
            pos.setX(pos.getX() - 1);
            if (pos.getX() < 0 || area.containsKey(pos)) {
                return shape;
            }
        }
        return copyShape;
    }

    private List<SimplePos> moveRight(List<SimplePos> shape, Map<SimplePos, Boolean> area) {
        var copyShape = copyShape(shape);
        for (var pos : copyShape) {
            pos.setX(pos.getX() + 1);
            if (pos.getX() > 6 || area.containsKey(pos)) {
                return shape;
            }
        }
        return copyShape;
    }

    private List<SimplePos> copyShape(List<SimplePos> rocks) {
        List<SimplePos> r = new LinkedList<>();
        for (var point : rocks) {
            r.add(point.copy());
        }
        return r;
    }

    private List<List<SimplePos>> initShapes() {
        List<List<SimplePos>> response = new LinkedList<>();

        List<SimplePos> line = List.of(//
                new SimplePos(2, 0), //
                new SimplePos(3, 0), //
                new SimplePos(4, 0), //
                new SimplePos(5, 0)//
        );
        response.add(line);

        List<SimplePos> plus = List.of(//
                new SimplePos(3, 0), //
                new SimplePos(3, 1), //
                new SimplePos(3, 2), //
                new SimplePos(2, 1), //
                new SimplePos(4, 1)//
        );
        response.add(plus);

        List<SimplePos> l = List.of(//
                new SimplePos(4, 0), //
                new SimplePos(4, 1), //
                new SimplePos(4, 2), //
                new SimplePos(3, 2), //
                new SimplePos(2, 2)//
        );
        response.add(l);

        List<SimplePos> i = List.of(//
                new SimplePos(2, 0), //
                new SimplePos(2, 1), //
                new SimplePos(2, 2), //
                new SimplePos(2, 3)//
        );
        response.add(i);

        List<SimplePos> sq = List.of(//
                new SimplePos(2, 0), //
                new SimplePos(2, 1), //
                new SimplePos(3, 0), //
                new SimplePos(3, 1)//
        );
        response.add(sq);

        return response;
    }

}
