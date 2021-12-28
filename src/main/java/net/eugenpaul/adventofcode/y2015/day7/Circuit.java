package net.eugenpaul.adventofcode.y2015.day7;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Circuit {

    private Map<String, Wire> data;

    public Circuit() {
        data = new HashMap<>();
    }

    public Short getWireSignal(String wireName) {
        Wire wire = data.get(wireName);
        if (null != wire) {
            return data.get(wireName).getSignal();
        }
        return null;
    }

    /**
     * Add a signal to Circuit. If a Wire has already a signal, the signal will be overwritten.
     * 
     * @param signal
     */
    public void addInputSignal(ISourceSignal signal) {
        for (String sourceWire : signal.getInput()) {
            data.computeIfAbsent(sourceWire, k -> new Wire(k, this));
        }

        data.compute(signal.getWirename(), (k, v) -> {
            if (null == v) {
                Wire wire = new Wire(k, this);
                wire.setSignal(signal);
                return wire;
            }
            v.setSignal(signal);
            return v;
        });
    }

    public void resetValues() {
        for (Entry<String, Wire> element : data.entrySet()) {
            element.getValue().resetSignal();
        }
    }
}
