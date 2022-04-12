package net.eugenpaul.adventofcode.helper;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

public enum Direction {
    N('^'), //
    O('>'), //
    S('v'), //
    W('<') //
    ;

    @Getter
    private char arrow;

    private static Map<Character, Direction> arrowToDirection = Stream.of(Direction.values()).collect(Collectors.toMap(Direction::getArrow, v -> v));

    private Direction(char arrow) {
        this.arrow = arrow;
    }

    public Direction turnLeft() {
        switch (this) {
        case N:
            return W;
        case W:
            return S;
        case S:
            return O;
        case O:
            return N;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction turnRight() {
        switch (this) {
        case N:
            return O;
        case O:
            return S;
        case S:
            return W;
        case W:
            return N;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction reverse() {
        switch (this) {
        case N:
            return S;
        case S:
            return N;
        case O:
            return W;
        case W:
            return O;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Convert direction-char (^v<>) to Direction
     * @param arrow
     * @return
     */
    public static Direction fromArrow(char arrow) {
        return arrowToDirection.get(arrow);
    }
}
