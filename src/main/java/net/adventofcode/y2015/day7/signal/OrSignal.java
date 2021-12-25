package net.adventofcode.y2015.day7.signal;

import java.util.Collections;
import java.util.List;

import net.adventofcode.y2015.day7.Circuit;
import net.adventofcode.y2015.day7.ISourceSignal;

public class OrSignal implements ISourceSignal {

    private String sourceWire1;
    private String sourceWire2;
    private Short sourceValue1;
    private Short sourceValue2;
    private String wireName;
    private List<String> inputWire;

    public OrSignal(String sourceWire1, String sourceWire2, String wireName) {
        this.sourceWire1 = sourceWire1;
        this.sourceWire2 = sourceWire2;
        this.sourceValue1 = null;
        this.sourceValue2 = null;
        this.wireName = wireName;
        inputWire = List.of(sourceWire1, sourceWire2);
    }

    public OrSignal(String sourceWire1, Short sourceValue1, String wireName) {
        this.sourceWire1 = sourceWire1;
        this.sourceWire2 = null;
        this.sourceValue1 = sourceValue1;
        this.sourceValue2 = null;
        this.wireName = wireName;
        inputWire = List.of(sourceWire1);
    }

    public OrSignal(Short sourceValue1, Short sourceValue2, String wireName) {
        this.sourceWire1 = null;
        this.sourceWire2 = null;
        this.sourceValue1 = sourceValue1;
        this.sourceValue2 = sourceValue2;
        this.wireName = wireName;
        inputWire = Collections.emptyList();
    }

    @Override
    public Short getSignal(Circuit circuit) {
        if (null != sourceWire1 && null != sourceWire2) {
            return (short) (circuit.getWireSignal(sourceWire1) | circuit.getWireSignal(sourceWire2));
        }

        if (null != sourceWire1) {
            return (short) (circuit.getWireSignal(sourceWire1) | sourceValue1);
        }

        return (short) (sourceValue1 | sourceValue2);
    }

    @Override
    public List<String> getInput() {
        return inputWire;
    }

    @Override
    public String getWirename() {
        return wireName;
    }

}
