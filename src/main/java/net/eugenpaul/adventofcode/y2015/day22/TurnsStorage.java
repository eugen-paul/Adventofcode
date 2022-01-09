package net.eugenpaul.adventofcode.y2015.day22;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class TurnsStorage {

    private TreeMap<Integer, List<TurnData>> turns;

    public TurnsStorage() {
        turns = new TreeMap<>();
    }

    public TurnData getLowestTurn() {
        if (turns.isEmpty()) {
            return null;
        }
        Integer lowestKey = turns.firstKey();
        if (lowestKey == null) {
            return null;
        }

        List<TurnData> valueList = turns.get(lowestKey);
        TurnData responseData = valueList.remove(0);
        if (valueList.isEmpty()) {
            turns.remove(lowestKey);
        }
        return responseData;
    }

    public int getSize() {
        return turns.size();
    }

    public synchronized void addTurnData(TurnData data) {
        List<TurnData> values = turns.get(data.getTotalManaCost());
        if (values == null) {
            values = new LinkedList<>();
            turns.put(data.getTotalManaCost(), values);
        }
        values.add(data);
    }
}
