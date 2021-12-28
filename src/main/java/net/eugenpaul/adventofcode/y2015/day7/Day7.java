package net.eugenpaul.adventofcode.y2015.day7;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day7 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day7.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day7.class.getName());

    private Circuit circuit;

    public Short getSignal(String wireName) {
        try {
            return circuit.getWireSignal(wireName);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception by getWireSignal: ", e);
            return null;
        }
    }

    public Day7() {
        circuit = new Circuit();
    }

    public static void main(String[] args) {
        Day7 puzzle1 = new Day7();
        puzzle1.doPuzzleFromFile("y2015/day7/puzzle1.txt");
        int aSignal = puzzle1.getSignal("a") & 0xFFFF;
        logger.log(Level.INFO, () -> "puzzle1 - wire a: " + (aSignal & 0xFFFF));

        puzzle1.addSignal(aSignal + " -> b");
        puzzle1.resetCircuitValue();
        logger.log(Level.INFO, () -> "puzzle1 - wire a: " + (puzzle1.getSignal("a") & 0xFFFF));
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        return doEvent(eventData);
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        for (String commando : eventData) {
            ISourceSignal signal = SignalFactory.parseSignal(commando);
            circuit.addInputSignal(signal);
        }

        return true;
    }

    public void addSignal(String commando) {
        ISourceSignal signal = SignalFactory.parseSignal(commando);
        circuit.addInputSignal(signal);
    }

    public void resetCircuitValue() {
        circuit.resetValues();
    }

}
