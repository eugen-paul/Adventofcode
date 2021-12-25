package net.adventofcode.y2015.day7.signal;

import java.util.Collections;
import java.util.List;

import net.adventofcode.y2015.day7.Circuit;
import net.adventofcode.y2015.day7.ISourceSignal;

public class ValueSignal implements ISourceSignal {

    private Short sourceValue;
    private String wireName;

    public ValueSignal(Short sourceValue, String wireName) {
        this.sourceValue = sourceValue;
        this.wireName = wireName;
    }

    @Override
    public Short getSignal(Circuit circuit) {
        return sourceValue;
    }

    @Override
    public List<String> getInput() {
        return Collections.emptyList();
    }

    @Override
    public String getWirename() {
        return wireName;
    }

}
