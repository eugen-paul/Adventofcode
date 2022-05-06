package net.eugenpaul.adventofcode.helper;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class HashStorage {

    Map<String, Integer> storage;

    @Getter
    Integer cycleBegin = null;

    @Getter
    Integer cycleEnd = null;

    public HashStorage() {
        storage = new HashMap<>();
    }

    public boolean add(String hash, Integer step) {
        if (storage.containsKey(hash)) {
            if (cycleBegin == null) {
                cycleBegin = storage.get(hash);
                cycleEnd = step - 1;
            }
            return true;
        }
        storage.put(hash, step);
        return false;
    }

    public String getHashOf(int step) {
        if (cycleBegin == null) {
            return storage.entrySet().stream().filter(v -> v.getValue() == step).findFirst().orElseThrow().getKey();
        }

        int delta = cycleEnd - cycleBegin + 1;
        if (delta == 0) {
            return "";
        }
        int rest = step - cycleEnd - 1;
        int restMod = rest % delta;
        int responseStep = cycleBegin + restMod;

        return storage.entrySet().stream().filter(v -> v.getValue() == responseStep).findFirst().orElseThrow().getKey();
    }
}
