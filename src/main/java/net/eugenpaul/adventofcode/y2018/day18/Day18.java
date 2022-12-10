package net.eugenpaul.adventofcode.y2018.day18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.HashStorage;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day18 extends SolutionTemplate {

    private enum Type {
        OPEN('.'), TREES('|'), LUMBERYARD('#');

        @Getter
        private final char charValue;
        private static final Map<Character, Type> charToType = Stream.of(Type.values()).collect(Collectors.toMap(v -> v.charValue, v -> v));

        private Type(char c) {
            this.charValue = c;
        }

        public static Type fromChar(char c) {
            return charToType.get(c);
        }
    }

    @Getter
    private int resourceValue;
    @Getter
    private int resourceValueAfter1b;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2018/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        resourceValue = doPuzzle(eventData, 10);
        resourceValueAfter1b = doPuzzle(eventData, 1_000_000_000);

        logger.log(Level.INFO, () -> "resourceValue : " + getResourceValue());
        logger.log(Level.INFO, () -> "resourceValueAfter1b : " + getResourceValueAfter1b());

        return true;
    }

    private int doPuzzle(List<String> eventData, int stepsCount) {
        Map<SimplePos, Type> area = MapOfSimplePos.initMap(eventData, Type::fromChar);
        HashStorage storage = new HashStorage();

        for (int i = 0; i < stepsCount; i++) {
            Map<SimplePos, Type> areaCopy = new HashMap<>();
            for (var entry : area.entrySet()) {
                areaCopy.put(//
                        entry.getKey(), //
                        nextState(area, entry.getKey()) //
                );
            }
            area = areaCopy;

            String hash = toHash(area);

            if (storage.add(hash, (long) i)) {
                break;
            }
        }

        String hashAtStep = storage.getHashOf(stepsCount - 1);

        int countTreeAfter1b = (int) StringConverter.toCharStream(hashAtStep).filter(v -> v == '|').count();
        int countLumberyardsAfter1b = (int) StringConverter.toCharStream(hashAtStep).filter(v -> v == '#').count();

        return countTreeAfter1b * countLumberyardsAfter1b;
    }

    private String toHash(Map<SimplePos, Type> area) {
        return MapOfSimplePos.mapToPrintList(area, Type::getCharValue).stream().reduce("", (a, b) -> a + b);
    }

    private Type nextState(Map<SimplePos, Type> area, SimplePos pos) {
        switch (area.get(pos)) {
        case OPEN:
            return netStateOfOpen(area, pos);
        case TREES:
            return netStateOfTrees(area, pos);
        case LUMBERYARD:
            return netStateOfLumberyard(area, pos);
        default:
            throw new IllegalArgumentException();
        }
    }

    private Type netStateOfOpen(Map<SimplePos, Type> area, SimplePos pos) {
        if (countAdjacent(area, pos, Type.TREES) >= 3) {
            return Type.TREES;
        }

        return Type.OPEN;
    }

    private Type netStateOfTrees(Map<SimplePos, Type> area, SimplePos pos) {
        if (countAdjacent(area, pos, Type.LUMBERYARD) >= 3) {
            return Type.LUMBERYARD;
        }

        return Type.TREES;
    }

    private Type netStateOfLumberyard(Map<SimplePos, Type> area, SimplePos pos) {
        int countLumberyard = countAdjacent(area, pos, Type.LUMBERYARD);
        int countTree = countAdjacent(area, pos, Type.TREES);

        if (countLumberyard >= 1 && countTree >= 1) {
            return Type.LUMBERYARD;
        }

        return Type.OPEN;
    }

    private int countAdjacent(Map<SimplePos, Type> area, SimplePos pos, Type countType) {
        int response = 0;

        if (area.get(pos.moveNew(Direction.N)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.N).moveNew(Direction.E)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.E)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.E).moveNew(Direction.S)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.S)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.S).moveNew(Direction.W)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.W)) == countType) {
            response++;
        }
        if (area.get(pos.moveNew(Direction.W).moveNew(Direction.N)) == countType) {
            response++;
        }

        return response;
    }

}
