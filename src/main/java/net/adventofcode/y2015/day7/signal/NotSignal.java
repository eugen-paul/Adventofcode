package net.adventofcode.y2015.day7.signal;

import java.util.List;

import net.adventofcode.y2015.day7.Circuit;
import net.adventofcode.y2015.day7.ISourceSignal;

public class NotSignal implements ISourceSignal {

    private String sourceWire;
    private String wireName;

    public NotSignal(String sourceWire, String wireName) {
        this.sourceWire = sourceWire;
        this.wireName = wireName;
    }

    @Override
    public Short getSignal(Circuit circuit) {
        return (short) ~circuit.getWireSignal(sourceWire);
    }

    @Override
    public List<String> getInput() {
        return List.of(sourceWire);
    }

    @Override
    public String getWirename() {
        return wireName;
    }

}
