package net.eugenpaul.adventofcode.y2015.day22;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class TurnsStorage {

    /** posible player turns sorted by manacost */
    private TreeMap<Integer, List<TurnData>> turns;

    public TurnsStorage() {
        turns = new TreeMap<>();
    }

    public TurnData getLowestTurn() {
        if (turns.isEmpty()) {
            return null;
        }
        var first = turns.firstEntry();
        List<TurnData> valueList = first.getValue();
        TurnData responseData = valueList.remove(0);
        if (valueList.isEmpty()) {
            turns.pollFirstEntry();
        }
        return responseData;
    }

    public int getSize() {
        return turns.size();
    }

    public synchronized void addTurnData(TurnData data) {
        turns.computeIfAbsent(data.getTotalManaCost(), v -> new LinkedList<>())//
                .add(data);
    }
}
