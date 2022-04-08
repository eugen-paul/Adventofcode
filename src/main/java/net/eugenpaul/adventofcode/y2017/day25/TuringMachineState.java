package net.eugenpaul.adventofcode.y2017.day25;

import java.util.List;

import lombok.Data;

@Data
public class TuringMachineState {

    private char state;

    private boolean write0;
    private int move0;
    private char continue0;

    private boolean write1;
    private int move1;
    private char continue1;

    public TuringMachineState(List<String> blueprint, int fromLine) {
        String line = blueprint.get(fromLine);
        state = line.charAt(line.length() - 2);

        line = blueprint.get(fromLine + 2);
        write0 = line.charAt(line.length() - 2) == '1';

        line = blueprint.get(fromLine + 3);
        if (line.contains("right")) {
            move0 = 1;
        } else {
            move0 = -1;
        }

        line = blueprint.get(fromLine + 4);
        continue0 = line.charAt(line.length() - 2);

        line = blueprint.get(fromLine + 6);
        write1 = line.charAt(line.length() - 2) == '1';

        line = blueprint.get(fromLine + 7);
        if (line.contains("right")) {
            move1 = 1;
        } else {
            move1 = -1;
        }

        line = blueprint.get(fromLine + 8);
        continue1 = line.charAt(line.length() - 2);
    }
}
