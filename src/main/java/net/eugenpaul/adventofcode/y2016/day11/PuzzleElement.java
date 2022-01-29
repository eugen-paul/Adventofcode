package net.eugenpaul.adventofcode.y2016.day11;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PuzzleElement implements Comparable<PuzzleElement> {
    private PuzzleObjectType type;
    private String name;

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();

        if (type == PuzzleObjectType.GENERATOR) {
            response.append("G_");
        } else {
            response.append("M_");
        }
        response.append(name);

        return response.toString();
    }

    @Override
    public int compareTo(PuzzleElement o) {
        return name.compareTo(o.getName());
    }
}
