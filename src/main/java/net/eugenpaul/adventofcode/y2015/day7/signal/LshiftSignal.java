package net.eugenpaul.adventofcode.y2015.day7.signal;

import java.util.List;

import net.eugenpaul.adventofcode.y2015.day7.Circuit;
import net.eugenpaul.adventofcode.y2015.day7.ISourceSignal;

public class LshiftSignal implements ISourceSignal {

    private String sourceWire1;
    private short shiftValue;
    private String wireName;

    public LshiftSignal(String sourceWire1, short shiftValue, String wireName) {
        this.sourceWire1 = sourceWire1;
        this.shiftValue = shiftValue;
        this.wireName = wireName;
    }

    @Override
    public Short getSignal(Circuit circuit) {
        return (short) (circuit.getWireSignal(sourceWire1) << shiftValue);
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
