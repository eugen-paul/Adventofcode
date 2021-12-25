package net.adventofcode.y2015.day7;

import java.util.List;

public interface ISourceSignal {
    public Short getSignal(Circuit circuit);

    public List<String> getInput();

    public String getWirename();
}
