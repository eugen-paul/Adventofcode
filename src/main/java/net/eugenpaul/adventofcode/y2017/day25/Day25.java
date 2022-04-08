package net.eugenpaul.adventofcode.y2017.day25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    @Getter
    private long diagnosticChecksum;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2017/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        char currentState = eventData.get(0).charAt(eventData.get(0).length() - 2);
        int currentPosition = 0;
        int steps = Integer.parseInt(eventData.get(1).split(" ")[5]);

        Map<Character, TuringMachineState> states = new HashMap<>();
        for (int i = 3; i < eventData.size(); i += 10) {
            TuringMachineState state = new TuringMachineState(eventData, i);
            states.put(state.getState(), state);
        }

        Map<Integer, Boolean> tape = new HashMap<>();

        for (int i = 0; i < steps; i++) {
            TuringMachineState state = states.get(currentState);
            Boolean currentValue = tape.getOrDefault(currentPosition, Boolean.FALSE);
            if (currentValue.booleanValue()) {
                tape.put(currentPosition, state.isWrite1());
                currentPosition += state.getMove1();
                currentState = state.getContinue1();
            } else {
                tape.put(currentPosition, state.isWrite0());
                currentPosition += state.getMove0();
                currentState = state.getContinue0();
            }
        }

        diagnosticChecksum = tape.values().stream().filter(Boolean::booleanValue).count();

        logger.log(Level.INFO, () -> "diagnosticChecksum: " + getDiagnosticChecksum());

        return true;
    }

}
