package net.eugenpaul.adventofcode.y2015.day7;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private int aPuzzle1;
    @Getter
    private int aPuzzle2;

    private Circuit circuit;

    public Day7() {
        circuit = new Circuit();
    }

    public static void main(String[] args) {
        Day7 puzzle1 = new Day7();
        puzzle1.doPuzzleFromFile("y2015/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        for (String commando : eventData) {
            ISourceSignal signal = SignalFactory.parseSignal(commando);
            circuit.addInputSignal(signal);
        }

        aPuzzle1 = getSignal("a") & 0xFFFF;
        logger.log(Level.INFO, () -> "puzzle1 - wire a: " + (aPuzzle1 & 0xFFFF));
        
        addSignal(aPuzzle1 + " -> b");
        resetCircuitValue();
        aPuzzle2 = getSignal("a") & 0xFFFF;
        logger.log(Level.INFO, () -> "puzzle1 - wire a: " + (aPuzzle2 & 0xFFFF));
        return true;
    }

    public Short getSignal(String wireName) {
        try {
            return circuit.getWireSignal(wireName);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception by getWireSignal: ", e);
            return null;
        }
    }

    public void addSignal(String commando) {
        ISourceSignal signal = SignalFactory.parseSignal(commando);
        circuit.addInputSignal(signal);
    }

    public void resetCircuitValue() {
        circuit.resetValues();
    }

}
