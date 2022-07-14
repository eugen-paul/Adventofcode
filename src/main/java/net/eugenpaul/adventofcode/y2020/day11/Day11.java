package net.eugenpaul.adventofcode.y2020.day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum SeatState {
        FLOOR('.'), EMPTY('L'), OCCUPIED('#');

        private static final Map<Character, SeatState> valueToState = Stream.of(SeatState.values()).collect(Collectors.toMap(SeatState::getValue, v -> v));

        @Getter
        private final char value;

        public static SeatState fromChar(char c) {
            return valueToState.get(c);
        }
    }

    @Getter
    private int occupiedSeats;
    @Getter
    private int occupiedSeats2;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2020/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, SeatState> area = MapOfSimplePos.initMap(eventData, SeatState::fromChar);

        occupiedSeats = doPuzzle1(area);
        occupiedSeats2 = doPuzzle2(area);

        logger.log(Level.INFO, () -> "occupiedSeats  : " + getOccupiedSeats());
        logger.log(Level.INFO, () -> "occupiedSeats2  : " + getOccupiedSeats2());

        return true;
    }

    private int doPuzzle1(Map<SimplePos, SeatState> area) {

        Map<SimplePos, SeatState> currentState;
        Map<SimplePos, SeatState> nextState = area;
        do {
            currentState = nextState;
            nextState = getNextState(currentState);
        } while (!currentState.equals(nextState));

        return (int) nextState.values().stream().filter(v -> v == SeatState.OCCUPIED).count();
    }

    private int doPuzzle2(Map<SimplePos, SeatState> area) {

        Map<SimplePos, SeatState> currentState;
        Map<SimplePos, SeatState> nextState = area;
        
        do {
            currentState = nextState;
            nextState = getNextState2(currentState);
        } while (!currentState.equals(nextState));

        return (int) nextState.values().stream().filter(v -> v == SeatState.OCCUPIED).count();
    }

    private Map<SimplePos, SeatState> getNextState(Map<SimplePos, SeatState> currentState) {
        Map<SimplePos, SeatState> nextState = new HashMap<>(currentState);

        for (var entry : currentState.entrySet()) {
            int os = getNumberOfOccupiedSeats(currentState, entry.getKey());
            if (entry.getValue() == SeatState.EMPTY && os == 0) {
                nextState.put(entry.getKey().copy(), SeatState.OCCUPIED);
            } else if (entry.getValue() == SeatState.OCCUPIED && os >= 4) {
                nextState.put(entry.getKey().copy(), SeatState.EMPTY);
            } else {
                nextState.put(entry.getKey().copy(), entry.getValue());
            }
        }

        return nextState;
    }

    private Map<SimplePos, SeatState> getNextState2(Map<SimplePos, SeatState> currentState) {
        Map<SimplePos, SeatState> nextState = new HashMap<>(currentState);

        for (var entry : currentState.entrySet()) {
            int os = getNumberOfOccupiedSeats2(currentState, entry.getKey());
            if (entry.getValue() == SeatState.EMPTY && os == 0) {
                nextState.put(entry.getKey().copy(), SeatState.OCCUPIED);
            } else if (entry.getValue() == SeatState.OCCUPIED && os >= 5) {
                nextState.put(entry.getKey().copy(), SeatState.EMPTY);
            } else {
                nextState.put(entry.getKey().copy(), entry.getValue());
            }
        }

        return nextState;
    }

    private int getNumberOfOccupiedSeats(Map<SimplePos, SeatState> currentState, SimplePos pos) {
        int response = 0;
        for (var d : Direction.values()) {
            SimplePos newPos = pos.moveNew(d);
            if (currentState.getOrDefault(newPos, SeatState.EMPTY) == SeatState.OCCUPIED) {
                response++;
            }
        }

        SimplePos newPos = pos.moveNew(Direction.N).move(Direction.E);
        if (currentState.getOrDefault(newPos, SeatState.EMPTY) == SeatState.OCCUPIED) {
            response++;
        }
        newPos = pos.moveNew(Direction.E).move(Direction.S);
        if (currentState.getOrDefault(newPos, SeatState.EMPTY) == SeatState.OCCUPIED) {
            response++;
        }
        newPos = pos.moveNew(Direction.S).move(Direction.W);
        if (currentState.getOrDefault(newPos, SeatState.EMPTY) == SeatState.OCCUPIED) {
            response++;
        }
        newPos = pos.moveNew(Direction.W).move(Direction.N);
        if (currentState.getOrDefault(newPos, SeatState.EMPTY) == SeatState.OCCUPIED) {
            response++;
        }
        return response;
    }

    private int getNumberOfOccupiedSeats2(Map<SimplePos, SeatState> currentState, SimplePos pos) {
        int response = 0;
        for (var d : Direction.values()) {
            if (getVisibleState(d, currentState, pos) == SeatState.OCCUPIED) {
                response++;
            }
        }

        if (getVisibleState(Direction.N, Direction.E, currentState, pos) == SeatState.OCCUPIED) {
            response++;
        }
        if (getVisibleState(Direction.E, Direction.S, currentState, pos) == SeatState.OCCUPIED) {
            response++;
        }
        if (getVisibleState(Direction.S, Direction.W, currentState, pos) == SeatState.OCCUPIED) {
            response++;
        }
        if (getVisibleState(Direction.W, Direction.N, currentState, pos) == SeatState.OCCUPIED) {
            response++;
        }
        return response;
    }

    private SeatState getVisibleState(Direction d, Map<SimplePos, SeatState> currentState, SimplePos pos) {
        SimplePos newPos = pos.moveNew(d);
        SeatState toCheckState = currentState.get(newPos);
        while (toCheckState == SeatState.FLOOR) {
            newPos = newPos.move(d);
            toCheckState = currentState.get(newPos);
        }
        return toCheckState;
    }

    private SeatState getVisibleState(Direction d1, Direction d2, Map<SimplePos, SeatState> currentState, SimplePos pos) {
        SimplePos newPos = pos.moveNew(d1).move(d2);
        SeatState toCheckState = currentState.get(newPos);
        while (toCheckState == SeatState.FLOOR) {
            newPos = newPos.moveNew(d1).move(d2);
            toCheckState = currentState.get(newPos);
        }
        return toCheckState;
    }

}
