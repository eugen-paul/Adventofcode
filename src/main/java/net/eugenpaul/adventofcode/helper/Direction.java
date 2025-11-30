package net.eugenpaul.adventofcode.helper;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

public enum Direction {
    N('^', 'U'), //
    E('>', 'R'), //
    S('v', 'D'), //
    W('<', 'L') //
    ;

    @Getter
    private char arrow;
    @Getter
    private char oneChar;
    @Getter
    private char udrl;

    private static Map<Character, Direction> arrowToDirection = Stream.of(Direction.values()).collect(Collectors.toMap(Direction::getArrow, v -> v));
    private static Map<Character, Direction> charToDirection = Stream.of(Direction.values()).collect(Collectors.toMap(Direction::getOneChar, v -> v));
    private static Map<Character, Direction> udrlToDirection = Stream.of(Direction.values()).collect(Collectors.toMap(Direction::getUdrl, v -> v));

    private Direction(char arrow, char udrl) {
        this.arrow = arrow;
        this.udrl = udrl;
        this.oneChar = toString().charAt(0);
    }

    public Direction turnLeft() {
        switch (this) {
        case N:
            return W;
        case W:
            return S;
        case S:
            return E;
        case E:
            return N;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction turnRight() {
        switch (this) {
        case N:
            return E;
        case E:
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
        case E:
            return W;
        case W:
            return E;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction turnOnCurve(char curve) {
        switch (curve) {
        case '/':
            switch (this) {
            case N:
                return Direction.E;
            case S:
                return Direction.W;
            case W:
                return Direction.S;
            case E:
                return Direction.N;
            default:
                throw new IllegalArgumentException("Illegal Direction: " + curve);
            }
        case '\\':
            switch (this) {
            case N:
                return Direction.W;
            case S:
                return Direction.E;
            case W:
                return Direction.N;
            case E:
                return Direction.S;
            default:
                throw new IllegalArgumentException("Illegal Direction: " + curve);
            }
        default:
            throw new IllegalArgumentException("Illegal curve: " + curve);
        }
    }

    /**
     * Convert direction-char (^v<>) to Direction
     * 
     * @param arrow
     * @return
     */
    public static Direction fromArrow(char arrow) {
        return arrowToDirection.get(arrow);
    }

    /**
     * Convert direction-String (^v<>) to Direction
     * 
     * @param arrow
     * @return
     */
    public static Direction fromArrow(String arrow) {
        return fromArrow(arrow.charAt(0));
    }

    /**
     * Convert direction-char (NWES) to Direction
     * 
     * @param Direction
     * @return
     */
    public static Direction fromChar(char c) {
        return charToDirection.get(Character.toUpperCase(c));
    }

    /**
     * Convert direction-String (NWES) to Direction
     * 
     * @param Direction
     * @return
     */
    public static Direction fromChar(String c) {
        return fromChar(c.charAt(0));
    }

    /**
     * Convert direction-char (UDRL) to Direction
     * 
     * @param Direction
     * @return
     */
    public static Direction fromUdrl(char c) {
        return udrlToDirection.get(c);
    }

    /**
     * Convert direction-String (UDRL) to Direction
     * 
     * @param Direction
     * @return
     */
    public static Direction fromUdrl(String c) {
        return fromUdrl(c.charAt(0));
    }
}
