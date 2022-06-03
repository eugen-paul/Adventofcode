package net.eugenpaul.adventofcode.helper;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class HashStorage {

    Map<String, Long> storage;

    @Getter
    Long cycleBegin = null;

    @Getter
    Long cycleEnd = null;

    public HashStorage() {
        storage = new HashMap<>();
    }

    public boolean add(String hash, Long step) {
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

        long delta = cycleEnd - cycleBegin + 1L;
        if (delta == 0L) {
            return "";
        }
        long rest = step - cycleEnd - 1L;
        long restMod = rest % delta;
        long responseStep = cycleBegin + restMod;

        return storage.entrySet().stream().filter(v -> v.getValue() == responseStep).findFirst().orElseThrow().getKey();
    }
}
