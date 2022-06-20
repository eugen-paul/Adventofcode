package net.eugenpaul.adventofcode.y2019.day17;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day17 extends SolutionTemplate {

    @Getter
    private long sumOfAlignmentParameters;
    @Getter
    private long dust;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2019/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<SimplePos, Boolean> area = new HashMap<>();
        Map<SimplePos, Direction> robot = new HashMap<>();

        initMaps(eventData, area, robot);

        List<SimplePos> intersections = getIntersections(area);

        sumOfAlignmentParameters = intersections.stream()//
                .mapToInt(v -> v.getX() * v.getY())//
                .sum();

        printMap(area, robot);

        SimplePos startPosition = robot.entrySet().stream().findFirst().orElseThrow().getKey();
        Direction startDirection = robot.entrySet().stream().findFirst().orElseThrow().getValue();
        List<String> way = getWay(area, startPosition, startDirection);

        logger.log(Level.INFO, () -> "sumOfAlignmentParameters : " + getSumOfAlignmentParameters());

        logger.log(Level.INFO, () -> "way                      : " + compress(way).stream().reduce("", (a, b) -> a + b));

        // I have compiled the functions manually.
        // my way is "L12R8L6R8L6R8L12L12R8L12R8L6R8L6L12R8L6R8L6R8L12L12R8L6R6L12R8L12L12R8L6R6L12L6R6L12R8L12L12R8"
        // The chain "R6" occurs three times. From this I have joined adjacent nodes of R6 to functino a. So that the function A is as long as possible.
        // => A = L,6,R,6,L,12 = 76 44 54 44 82 44 54 44 76 44 49 50
        // In the rest of the sequence you can see the small sequence "R8L12L12R8". The sequence was merged to function B.
        // => B = R,8,L,12,L,12,R,8 = 82 44 56 44 76 44 49 50 44 76 44 49 50 44 82 44 56
        // Rest is the function C
        // => C = L,12,R,8,L,6,R,8,L,6 = 76 44 49 50 44 82 44 56 44 76 44 54 44 82 44 56 44 76 44 54
        // => way = C,B,C,C,B,A,B,A,A,B = 67 44 66 44 67 44 67 44 66 44 65 44 66 44 65 44 65 44 66

        List<Integer> f = List.of(67, 44, 66, 44, 67, 44, 67, 44, 66, 44, 65, 44, 66, 44, 65, 44, 65, 44, 66, 10);
        List<Integer> a = List.of(76, 44, 54, 44, 82, 44, 54, 44, 76, 44, 49, 50, 10);
        List<Integer> b = List.of(82, 44, 56, 44, 76, 44, 49, 50, 44, 76, 44, 49, 50, 44, 82, 44, 56, 10);
        List<Integer> c = List.of(76, 44, 49, 50, 44, 82, 44, 56, 44, 76, 44, 54, 44, 82, 44, 56, 44, 76, 44, 54, 10);

        dust = moveRobot(eventData, f, a, b, c);

        logger.log(Level.INFO, () -> "dust : " + getDust());

        return true;
    }

    private List<String> getWay(Map<SimplePos, Boolean> area, SimplePos startPosition, Direction startDirection) {

        List<String> way = new LinkedList<>();

        Set<SimplePos> scaffoldArea = area.entrySet().stream()//
                .filter(v -> v.getValue().booleanValue())//
                .map(Entry::getKey)//
                .collect(Collectors.toSet());

        SimplePos currentPos = startPosition;
        Direction currentDir = startDirection;

        while (true) {
            SimplePos next = currentPos.moveNew(currentDir);
            if (scaffoldArea.contains(next)) {
                way.add("1");
                currentPos = next;
            } else if (scaffoldArea.contains(currentPos.moveNew(currentDir.turnLeft()))) {
                way.add("L");
                currentDir = currentDir.turnLeft();
            } else if (scaffoldArea.contains(currentPos.moveNew(currentDir.turnRight()))) {
                way.add("R");
                currentDir = currentDir.turnRight();
            } else {
                break;
            }
        }

        return way;
    }

    private List<String> compress(List<String> way) {
        List<String> response = new LinkedList<>();

        String lastChar = "?";
        int count = 0;
        for (String step : way) {
            if (step.equals("1")) {
                count++;
            } else {
                if (count > 0) {
                    response.add(count + "");
                }
                count = 0;
                response.add(step);
            }
            lastChar = step;
        }

        if (lastChar.equals("1")) {
            response.add(count + "");
        } else {
            response.add(lastChar);
        }

        return response;
    }

    private List<SimplePos> getIntersections(Map<SimplePos, Boolean> area) {
        List<SimplePos> response = new LinkedList<>();
        for (var key : area.keySet()) {
            if (isIntersection(area, key)) {
                response.add(key);
            }
        }
        return response;
    }

    private boolean isIntersection(Map<SimplePos, Boolean> area, SimplePos pos) {
        return area.get(pos) //
                && area.getOrDefault(pos.moveNew(Direction.N), false) //
                && area.getOrDefault(pos.moveNew(Direction.S), false) //
                && area.getOrDefault(pos.moveNew(Direction.W), false) //
                && area.getOrDefault(pos.moveNew(Direction.E), false) //
        ;
    }

    private void printMap(Map<SimplePos, Boolean> area, Map<SimplePos, Direction> robot) {
        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(area, v -> {
            if (v == null) {
                return ' ';
            }
            return (v.booleanValue() ? '#' : '.');
        }, robot, Direction::getArrow));
    }

    private long moveRobot(String eventData, List<Integer> f, List<Integer> a, List<Integer> b, List<Integer> c) {
        int pos = 0;
        IntcodeMapComputer comp;
        Map<Long, Long> opcodes;

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodes = new HashMap<>();
        comp = new IntcodeMapComputer();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        opcodes.put(0L, 2L);

        List<Integer> input = new LinkedList<>();
        input.addAll(f);
        input.addAll(a);
        input.addAll(b);
        input.addAll(c);
        input.add(110);
        input.add(10);

        comp.setInput(input.stream().mapToLong(i -> i).toArray());

        long response = 0L;

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                response = comp.removeOutput().intValue();
            }
        }

        return response;
    }

    private void initMaps(String eventData, Map<SimplePos, Boolean> area, Map<SimplePos, Direction> robot) {
        int pos = 0;
        IntcodeMapComputer comp;
        Map<Long, Long> opcodes;

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodes = new HashMap<>();
        comp = new IntcodeMapComputer();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        int x = 0;
        int y = 0;

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                int output = comp.removeOutput().intValue();
                switch (output) {
                case '#':
                    area.put(new SimplePos(x, y), true);
                    x++;
                    break;
                case '.':
                    area.put(new SimplePos(x, y), false);
                    x++;
                    break;
                case '\n':
                    x = 0;
                    y++;
                    break;
                case '<', '>', 'v', '^':
                    area.put(new SimplePos(x, y), true);
                    robot.put(new SimplePos(x, y), Direction.fromArrow((char) output));
                    x++;
                    break;
                default:
                    break;
                }
            }
        }
    }

}
