package net.eugenpaul.adventofcode.y2017.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    @Getter
    private Integer programs0;
    @Getter
    private Integer groups;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2017/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<Integer, Pipe> communicationMap = eventData.stream()//
                .map(Pipe::fromString)//
                .collect(Collectors.toMap(Pipe::getFrom, v -> v));

        List<Integer> rest = new ArrayList<>(communicationMap.keySet());

        groups = 1;
        programs0 = doPuzzle(communicationMap, rest, 0);

        while (!rest.isEmpty()) {
            doPuzzle(communicationMap, rest, rest.get(0));
            groups++;
        }

        logger.log(Level.INFO, () -> "programs0 : " + getPrograms0());
        logger.log(Level.INFO, () -> "groups : " + getGroups());

        return true;
    }

    private int doPuzzle(Map<Integer, Pipe> communicationMap, List<Integer> rest, int startPosition) {
        Map<Integer, Boolean> storage = new HashMap<>();

        step(communicationMap, storage, startPosition);

        storage.keySet().stream().forEach(rest::remove);
        return storage.size();
    }

    private void step(Map<Integer, Pipe> communicationMap, Map<Integer, Boolean> storage, int pos) {
        if (storage.containsKey(pos)) {
            return;
        }

        storage.put(pos, true);

        communicationMap.get(pos).getPipes().stream().forEach(v -> step(communicationMap, storage, v));
    }

}
