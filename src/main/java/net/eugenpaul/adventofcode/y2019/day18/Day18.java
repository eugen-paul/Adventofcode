package net.eugenpaul.adventofcode.y2019.day18;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;

public class Day18 extends SolutionTemplate {

    @Getter
    private int minSteps;
    @Getter
    private int minSteps2;

    private Map<String, Integer> storage;

    @Setter
    private boolean doStep1 = true;
    @Setter
    private boolean doStep2 = true;

    @AllArgsConstructor
    private class SimpleMaze implements Maze {
        Map<SimplePos, Boolean> area;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> response = new LinkedList<>();

            for (Direction direction : Direction.values()) {
                Boolean dirStep = area.getOrDefault(from.moveNew(direction), false);
                if (dirStep.booleanValue()) {
                    response.add(from.moveNew(direction));
                }
            }

            return response;
        }

    }

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2019/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Character> area = MapOfSimplePos.initMap(eventData, v -> v);

        Map<SimplePos, Boolean> walls = area.entrySet().stream()//
                .collect(Collectors.toMap(Entry::getKey, v -> v.getValue() != '#'));

        SimplePos startPos = area.entrySet().stream()//
                .filter(v -> v.getValue() == '@')//
                .findFirst().orElseThrow().getKey();

        Map<Character, SimplePos> keys = area.entrySet().stream()//
                .filter(v -> 'a' <= v.getValue() && v.getValue() <= 'z')//
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));

        Map<Character, SimplePos> doors = area.entrySet().stream()//
                .filter(v -> 'A' <= v.getValue() && v.getValue() <= 'Z')//
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));

        if (doStep1) {
            minSteps = doPuzzle1(walls, startPos, keys, doors);
        }

        if (doStep2) {
            minSteps2 = doPuzzle2(walls, startPos, keys, doors);
        }

        logger.log(Level.INFO, () -> "minSteps  : " + getMinSteps());
        logger.log(Level.INFO, () -> "minSteps2 : " + getMinSteps2());

        return true;
    }

    private int doPuzzle1(Map<SimplePos, Boolean> walls, SimplePos startPos, Map<Character, SimplePos> keys, Map<Character, SimplePos> doors) {
        storage = new HashMap<>();
        return getShortestPath(walls, startPos, keys, doors);
    }

    private int doPuzzle2(Map<SimplePos, Boolean> walls, SimplePos startPos, Map<Character, SimplePos> keys, Map<Character, SimplePos> doors) {
        storage = new HashMap<>();

        Map<SimplePos, Boolean> walls2 = new HashMap<>(walls);
        walls2.put(startPos, false);
        walls2.put(startPos.moveNew(Direction.N), false);
        walls2.put(startPos.moveNew(Direction.S), false);
        walls2.put(startPos.moveNew(Direction.W), false);
        walls2.put(startPos.moveNew(Direction.E), false);

        SimplePos startPos1 = startPos.moveNew(Direction.N).moveNew(Direction.W);
        SimplePos startPos2 = startPos.moveNew(Direction.N).moveNew(Direction.E);
        SimplePos startPos3 = startPos.moveNew(Direction.S).moveNew(Direction.W);
        SimplePos startPos4 = startPos.moveNew(Direction.S).moveNew(Direction.E);

        Map<Integer, SimplePos> startPosMap = new HashMap<>();
        startPosMap.put(1, startPos1);
        startPosMap.put(2, startPos2);
        startPosMap.put(3, startPos3);
        startPosMap.put(4, startPos4);

        return getShortestPath(walls2, startPosMap, keys, doors);
    }

    private int getShortestPath(Map<SimplePos, Boolean> walls, Map<Integer, SimplePos> startPos, Map<Character, SimplePos> keys,
            Map<Character, SimplePos> doors) {
        if (keys.isEmpty()) {
            return 0;
        }

        String hash = getRestHash(startPos, keys);

        Integer hashSteps = storage.get(hash);
        if (hashSteps != null) {
            return hashSteps;
        }

        Map<SimplePos, Boolean> area = getArea(walls, doors);

        int minPath = Integer.MAX_VALUE;

        for (var startEntryToCheck : startPos.entrySet()) {
            Map<Character, Integer> reachableKeys = getReachableKeys(area, startEntryToCheck.getValue(), keys);

            for (var nextKey : reachableKeys.entrySet()) {
                Map<Character, SimplePos> keysToCheck = new HashMap<>(keys);
                keysToCheck.remove(nextKey.getKey());

                Map<Character, SimplePos> doorsToCheck = new HashMap<>(doors);
                doorsToCheck.remove(Character.toUpperCase(nextKey.getKey()));

                Map<Integer, SimplePos> newStartPos = new HashMap<>(startPos);
                newStartPos.put(startEntryToCheck.getKey(), keys.get(nextKey.getKey()));

                int path = getShortestPath(//
                        walls, //
                        newStartPos, //
                        keysToCheck, //
                        doorsToCheck //
                );
                minPath = Math.min(minPath, path + nextKey.getValue());
            }
        }

        storage.putIfAbsent(hash, minPath);

        return minPath;
    }

    private int getShortestPath(Map<SimplePos, Boolean> walls, SimplePos startPos, Map<Character, SimplePos> keys, Map<Character, SimplePos> doors) {
        if (keys.isEmpty()) {
            return 0;
        }

        String hash = getRestHash(startPos, keys);

        Integer hashSteps = storage.get(hash);
        if (hashSteps != null) {
            return hashSteps;
        }

        Map<SimplePos, Boolean> area = getArea(walls, doors);

        Map<Character, Integer> reachableKeys = getReachableKeys(area, startPos, keys);

        int minPath = Integer.MAX_VALUE;

        for (var nextKey : reachableKeys.entrySet()) {
            Map<Character, SimplePos> keysToCheck = new HashMap<>(keys);
            keysToCheck.remove(nextKey.getKey());

            Map<Character, SimplePos> doorsToCheck = new HashMap<>(doors);
            doorsToCheck.remove(Character.toUpperCase(nextKey.getKey()));

            int path = getShortestPath(//
                    walls, //
                    keys.get(nextKey.getKey()), //
                    keysToCheck, //
                    doorsToCheck //
            );
            minPath = Math.min(minPath, path + nextKey.getValue());
        }

        storage.putIfAbsent(hash, minPath);

        return minPath;
    }

    private String getRestHash(SimplePos startPos, Map<Character, SimplePos> keys) {
        StringBuilder responseHash = new StringBuilder();
        responseHash.append(startPos.toString());
        for (var key : keys.keySet()) {
            responseHash.append(key);
        }
        return responseHash.toString();
    }

    private String getRestHash(Map<Integer, SimplePos> startPos, Map<Character, SimplePos> keys) {
        StringBuilder responseHash = new StringBuilder();

        responseHash.append(startPos.get(1));
        responseHash.append(startPos.get(2));
        responseHash.append(startPos.get(3));
        responseHash.append(startPos.get(4));

        for (var key : keys.keySet()) {
            responseHash.append(key);
        }
        return responseHash.toString();
    }

    private Map<SimplePos, Boolean> getArea(Map<SimplePos, Boolean> walls, Map<Character, SimplePos> doors) {
        Map<SimplePos, Boolean> area = new HashMap<>(walls);

        for (var door : doors.values()) {
            area.put(door, false);
        }

        return area;
    }

    private Map<Character, Integer> getReachableKeys(Map<SimplePos, Boolean> area, SimplePos startPos, Map<Character, SimplePos> keys) {
        Map<Character, Integer> reachableKeys = new HashMap<>();

        Maze testMaze = new SimpleMaze(area);

        Dijkstra dijkstra = new Dijkstra();
        Map<SimplePos, Integer> reachableFields = dijkstra.getReachableFields(testMaze, startPos.getX(), startPos.getY());

        for (var keyData : keys.entrySet()) {
            Integer wayLength = reachableFields.get(keyData.getValue());
            if (wayLength != null) {
                reachableKeys.put(keyData.getKey(), wayLength);
            }
        }
        return reachableKeys;
    }

}