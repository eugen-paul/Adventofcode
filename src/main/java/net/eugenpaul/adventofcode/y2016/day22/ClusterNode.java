package net.eugenpaul.adventofcode.y2016.day22;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.eugenpaul.adventofcode.helper.SimplePos;

@Getter
@AllArgsConstructor
@ToString
public class ClusterNode {
    private int x;
    private int y;

    private int size;
    private int used;

    public int getFree() {
        return size - used;
    }

    public boolean isEmpty() {
        return used == 0;
    }

    public SimplePos getPosition() {
        return new SimplePos(x, y);
    }

    public static ClusterNode fromString(String data) {
        try {
            String[] elements = Stream.of(data.split(" ")).filter(v -> !v.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
            String[] pos = elements[0].split("-");

            return new ClusterNode(//
                    Integer.parseInt(pos[1].substring(1)), //
                    Integer.parseInt(pos[2].substring(1)), //
                    Integer.parseInt(elements[1].substring(0, elements[1].length() - 1)), //
                    Integer.parseInt(elements[2].substring(0, elements[2].length() - 1)) //
            );
        } catch (Exception e) {
            return null;
        }
    }
}
