package net.eugenpaul.adventofcode.y2015.day7;

public class Wire {
    private String name;
    private Short value;
    private ISourceSignal sourceSignal;
    private Circuit circuit;

    public Wire(String name, Circuit circuit) {
        this.value = null;
        this.name = name;
        this.sourceSignal = null;
        this.circuit = circuit;
    }

    public void setSignal(ISourceSignal sourceSignal) {
        this.sourceSignal = sourceSignal;
    }

    public Short getSignal() {
        if (null != value) {
            return value;
        }
        if (null == sourceSignal) {
            throw new IllegalArgumentException("Wire " + name + " has no input.");
        }
        value = sourceSignal.getSignal(circuit);
        return value;
    }

    public String getName() {
        return name;
    }

    public void resetSignal() {
        value = null;
    }
}
