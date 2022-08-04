package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Horizontal Hexagonal Map <code>
 *     \ /
 *      +
 *  nw / \ ne
 *  \ /   \ /
 * w |     | e
 *   |     |
 *  / \   / \
 *  sw \ / se
 *      +
 *     / \
 * </code>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HexagonalMapH {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum HexMapHDirection {
        E("e"), SE("se"), SW("sw"), W("w"), NW("nw"), NE("ne");

        private static final Map<String, HexMapHDirection> valueToEnum = Stream.of(HexMapHDirection.values())
                .collect(Collectors.toMap(HexMapHDirection::getValue, v -> v));

        @Getter
        private final String value;

        public static HexMapHDirection fromString(String data) {
            if (valueToEnum.containsKey(data.charAt(0) + "")) {
                return valueToEnum.get(data.charAt(0) + "");
            }
            return valueToEnum.get(data.charAt(0) + "" + data.charAt(1));
        }
    }

    public static List<SimplePos> getNeighbors(SimplePos pos) {
        List<SimplePos> neighbors = new LinkedList<>();

        for (var d : HexMapHDirection.values()) {
            neighbors.add(computeNextPosition(pos, d));
        }

        return neighbors;
    }

    public static SimplePos computeNextPosition(SimplePos position, HexMapHDirection direction) {
        SimplePos reponsePosition = position.copy();

        switch (direction) {
        case E:
            reponsePosition.setX(reponsePosition.getX() + 1);
            break;
        case W:
            reponsePosition.setX(reponsePosition.getX() - 1);
            break;
        case SE:
            reponsePosition.setY(reponsePosition.getY() + 1);
            if ((reponsePosition.getY() % 2) == 1 || (reponsePosition.getY() % 2) == -1) {
                reponsePosition.setX(reponsePosition.getX() + 1);
            }
            break;
        case NE:
            reponsePosition.setY(reponsePosition.getY() - 1);
            if ((reponsePosition.getY() % 2) == 1 || (reponsePosition.getY() % 2) == -1) {
                reponsePosition.setX(reponsePosition.getX() + 1);
            }
            break;
        case SW:
            reponsePosition.setY(reponsePosition.getY() + 1);
            if ((reponsePosition.getY() % 2) == 0) {
                reponsePosition.setX(reponsePosition.getX() - 1);
            }
            break;
        case NW:
            reponsePosition.setY(reponsePosition.getY() - 1);
            if ((reponsePosition.getY() % 2) == 0) {
                reponsePosition.setX(reponsePosition.getX() - 1);
            }
            break;

        default:
            break;
        }

        return reponsePosition;
    }

    public static SimplePos computeNextPosition(SimplePos position, String direction) {
        return computeNextPosition(position, HexMapHDirection.fromString(direction));
    }

}
