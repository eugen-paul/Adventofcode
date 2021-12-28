package net.eugenpaul.adventofcode.y2015.day7.signal;

import java.util.List;

import net.eugenpaul.adventofcode.y2015.day7.Circuit;
import net.eugenpaul.adventofcode.y2015.day7.ISourceSignal;

public class RshiftSignal implements ISourceSignal {

    private String sourceWire1;
    private int shiftValue;
    private String wireName;

    public RshiftSignal(String sourceWire1, int shiftValue, String wireName) {
        this.sourceWire1 = sourceWire1;
        this.shiftValue = shiftValue;
        this.wireName = wireName;
    }

    @Override
    public Short getSignal(Circuit circuit) {
        return (short) ((circuit.getWireSignal(sourceWire1) & 0xFFFF) >>> shiftValue);
    }

    @Override
    public List<String> getInput() {
        return List.of(sourceWire1);
    }

    @Override
    public String getWirename() {
        return wireName;
    }

}
