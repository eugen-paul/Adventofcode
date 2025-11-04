package net.eugenpaul.adventofcode.y2024.day15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day15 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2024/day15/puzzle1.txt");
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

        List<String> mapData = new LinkedList<>();
        String movesData = "";
        boolean map = true;
        for (var l : eventData) {
            if (l.isBlank() && !map) {
                break;
            }
            if (map && l.isBlank()) {
                map = false;
                continue;
            }
            if (map) {
                mapData.add(l);
            } else {
                movesData += l;
            }
        }

        Map<SimplePos, Character> m = StringConverter.toMap(mapData);
        SimplePos r = StringConverter.posOfChar(mapData, '@');
        m.put(r, '.');

        for (char c : movesData.toCharArray()) {
            Direction d = Direction.fromArrow(c);
            SimplePos rNew = r.moveNew(d);
            if (m.get(rNew) == 'O') {
                SimplePos testPoint = rNew.moveNew(d);
                while (m.get(testPoint) == 'O') {
                    testPoint = testPoint.moveNew(d);
                }
                if (m.get(testPoint) == '.') {
                    m.put(testPoint, 'O');
                    m.put(rNew, '.');
                    r = rNew;
                }
            } else if (m.get(rNew) == '.') {
                r = rNew;
            }
        }
        // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(m, v -> v));

        for (var e : m.entrySet()) {
            if (e.getValue() == 'O') {
                response += e.getKey().getX() + 100 * e.getKey().getY();
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<String> mapData = new LinkedList<>();
        String movesData = "";
        boolean map = true;
        for (var l : eventData) {
            if (l.isBlank() && !map) {
                break;
            }
            if (map && l.isBlank()) {
                map = false;
                continue;
            }
            if (map) {
                StringBuilder b = new StringBuilder(l.length() * 2);
                for (var c : l.toCharArray()) {
                    switch (c) {
                    case '#':
                        b.append("##");
                        break;
                    case '.':
                        b.append("..");
                        break;
                    case 'O':
                        b.append("[]");
                        break;
                    case '@':
                        b.append("@.");
                        break;
                    default:
                        break;
                    }
                }
                mapData.add(b.toString());
            } else {
                movesData += l;
            }
        }

        Map<SimplePos, Character> m = StringConverter.toMap(mapData);
        SimplePos r = StringConverter.posOfChar(mapData, '@');
        m.put(r, '.');

        // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(m, v -> v));

        for (char c : movesData.toCharArray()) {
            Direction d = Direction.fromArrow(c);
            SimplePos rNew = r.moveNew(d);
            if (m.get(rNew) == '[' || m.get(rNew) == ']') {
                if (d == Direction.E || d == Direction.W) {
                    SimplePos testPoint = rNew.moveNew(d);
                    int moves = 1;
                    while (m.get(testPoint) == '[' || m.get(testPoint) == ']') {
                        testPoint = testPoint.moveNew(d);
                        moves++;
                    }
                    if (m.get(testPoint) == '.') {
                        for (int i = 0; i < moves; i = i + 2) {
                            if (d == Direction.W) {
                                m.put(new SimplePos(rNew.getX() - moves + i, rNew.getY()), '[');
                                m.put(new SimplePos(rNew.getX() - moves + i + 1, rNew.getY()), ']');
                            } else {
                                m.put(new SimplePos(rNew.getX() + moves - i - 1, rNew.getY()), '[');
                                m.put(new SimplePos(rNew.getX() + moves - i, rNew.getY()), ']');
                            }
                        }
                        m.put(rNew, '.');
                        r = rNew;
                    }
                } else {
                    if (movePossible(m, rNew, d)) {
                        doMove(m, rNew, d);
                        m.put(rNew, '.');
                        r = rNew;
                    }
                }
            } else if (m.get(rNew) == '.') {
                r = rNew;
            }
            // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(m, v -> v));
        }
        // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(m, v -> v));

        for (var e : m.entrySet()) {
            if (e.getValue() == '[') {
                response += e.getKey().getX() + 100 * e.getKey().getY();
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private boolean movePossible(Map<SimplePos, Character> map, SimplePos pos, Direction dir) {
        Character c = map.get(pos);
        if (c == '[') {
            return movePossible(map, pos.moveNew(dir), dir) && movePossible(map, pos.moveNew(Direction.E).moveNew(dir), dir);
        } else if (c == ']') {
            return movePossible(map, pos.moveNew(dir), dir) && movePossible(map, pos.moveNew(Direction.W).moveNew(dir), dir);
        } else if (c == '#') {
            return false;
        } else if (c == '.') {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void doMove(Map<SimplePos, Character> map, SimplePos pos, Direction dir) {
        Character c = map.get(pos);
        Character cNb;
        SimplePos nb;
        if (c == '[') {
            nb = pos.moveNew(Direction.E);
            cNb = ']';
        } else if (c == ']') {
            nb = pos.moveNew(Direction.W);
            cNb = '[';
        } else {
            throw new IllegalArgumentException();
        }

        SimplePos targetCur = pos.moveNew(dir);
        SimplePos targetNb = nb.moveNew(dir);
        if (map.get(targetCur) != '.') {
            doMove(map, targetCur, dir);
        }
        if (map.get(targetNb) != '.') {
            doMove(map, targetNb, dir);
        }
        map.put(targetCur, c);
        map.put(targetNb, cNb);
        map.put(pos, '.');
        map.put(nb, '.');
    }
}
