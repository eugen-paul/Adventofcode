package net.eugenpaul.adventofcode.helper;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Vertical Hexagonal Map <code>
 *   \ n  /
 * nw +--+ ne
 *   /    \
 * -+      +-
 *   \    /
 * sw +--+ se
 *   / s  \
 * </code>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HexagonalMapV {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum HexMapVDirection {
        N("n"), NE("ne"), SE("se"), S("s"), SW("sw"), NW("nw");

        private static final Map<String, HexMapVDirection> valueToEnum = Stream.of(HexMapVDirection.values())
                .collect(Collectors.toMap(HexMapVDirection::getValue, v -> v));

        @Getter
        private final String value;

        public static HexMapVDirection fromString(String data) {
            if (valueToEnum.containsKey(data.charAt(0) + "")) {
                return valueToEnum.get(data.charAt(0) + "");
            }
            return valueToEnum.get(data.charAt(0) + "" + data.charAt(1));
        }
    }

    public static SimplePos computeNextPosition(SimplePos position, HexMapVDirection direction) {
        return computeNextPosition(position, direction.getValue());
    }

    public static SimplePos computeNextPosition(SimplePos position, String direction) {
        SimplePos reponsePosition = position.copy();

        switch (direction) {
        case "n":
            reponsePosition.setY(reponsePosition.getY() + 1);
            break;
        case "ne":
            reponsePosition.setX(reponsePosition.getX() + 1);
            if ((reponsePosition.getX() % 2) == 1 || (reponsePosition.getX() % 2) == -1) {
                reponsePosition.setY(reponsePosition.getY() + 1);
            }
            break;
        case "nw":
            reponsePosition.setX(reponsePosition.getX() - 1);
            if ((reponsePosition.getX() % 2) == 1 || (reponsePosition.getX() % 2) == -1) {
                reponsePosition.setY(reponsePosition.getY() + 1);
            }
            break;
        case "se":
            reponsePosition.setX(reponsePosition.getX() + 1);
            if ((reponsePosition.getX() % 2) == 0) {
                reponsePosition.setY(reponsePosition.getY() - 1);
            }
            break;
        case "sw":
            reponsePosition.setX(reponsePosition.getX() - 1);
            if ((reponsePosition.getX() % 2) == 0) {
                reponsePosition.setY(reponsePosition.getY() - 1);
            }
            break;
        case "s":
            reponsePosition.setY(reponsePosition.getY() - 1);
            break;
        default:
            break;
        }

        return reponsePosition;
    }

    public static int computeDistanceToZero(SimplePos position) {
        int x = Math.abs(position.getX());
        int y = Math.abs(position.getY());
        int cSteps = 0;

        if (position.getY() < 0) {
            cSteps = x + Math.max(0, y - x + (x + 1) / 2);
        } else {
            cSteps = x + Math.max(0, y - x + x / 2);
        }
        return cSteps;
    }
}
