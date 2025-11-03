package net.eugenpaul.adventofcode.y2024.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day9 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2024/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Long> dm = StringConverter.digitsToLongList(eventData.get(0));
        long filesSum = 0;
        boolean f = true;
        for (var i : dm) {
            if (f) {
                filesSum += i;
            }
            f = !f;
        }

        int dmPosA = 0;
        int nPosA = 0;

        int dmPosB = dm.size() - 1;
        int nPosB = dm.get(dmPosB).intValue();

        int dmPosGl = 0;
        int nPosGl = 0;

        boolean onFile = true;
        for (long i = 0; i < filesSum; i++) {
            nPosGl++;
            if (nPosGl > dm.get(dmPosGl)) {
                nPosGl = 1;
                dmPosGl++;
                onFile = !onFile;
            }

            if (!onFile) {
                if (dm.get(dmPosGl) == 0) {
                    dmPosGl++;
                    onFile = true;
                }
            }

            if (onFile) {
                var d = dmPosA / 2;
                response += i * (d);
                nPosA++;
                if (nPosA >= dm.get(dmPosA)) {
                    nPosA = 0;
                    dmPosA += 2;
                }
            } else {
                response += i * (dmPosB / 2);
                nPosB--;
                if (nPosB == 0) {
                    dmPosB -= 2;
                    nPosB = dm.get(dmPosB).intValue();
                }

            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        ArrayList<Integer> disc = new ArrayList<>(eventData.get(0).length() * 9);
        for (int i = 0; i < eventData.get(0).length() * 9; i++) {
            disc.add(0);
        }

        HashMap<Integer, Integer> filePos = new HashMap<>();
        HashMap<Integer, Integer> fileLen = new HashMap<>();
        TreeMap<Integer, List<Integer>> freePos = new TreeMap<>();

        int pos = 0;
        int i = 0;
        boolean file = true;
        for (char c : eventData.get(0).toCharArray()) {
            int len = c - '0';
            if (file) {
                fill(pos, i, len, disc);
                filePos.put(i, pos);
                fileLen.put(i, len);
                i++;
                pos += len;
            } else {
                if (len > 0) {
                    fill(pos, 0, len, disc);
                    var pp = pos;
                    freePos.compute(len, (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        v.add(pp);
                        return v;
                    });
                }
                pos += len;
            }
            file = !file;
        }

        for (int blockNm = filePos.size() - 1; blockNm >= 0; blockNm--) {
            int blockPos = filePos.get(blockNm);
            int blockLen = fileLen.get(blockNm);
            int bestKey = 1000;
            int bestPos = Integer.MAX_VALUE;
            Entry<Integer, List<Integer>> e;
            for (int key = blockLen; key < 10; key++) {
                List<Integer> v = freePos.getOrDefault(key, Collections.emptyList());
                if (!v.isEmpty() && v.get(0) < blockPos && bestPos > v.get(0)) {
                    bestPos = v.get(0);
                    bestKey = key;
                }
            }
            e = freePos.ceilingEntry(bestKey);
            if (e == null || e.getValue().isEmpty() || e.getValue().get(0) > blockPos) {
                continue;
            }
            fill(blockPos, 0, blockLen, disc);
            fill(e.getValue().get(0), blockNm, blockLen, disc);
            int oldLen = e.getKey();
            int newLen = oldLen - blockLen;
            var pp = e.getValue().remove(0) + blockLen;
            if (newLen > 0) {
                freePos.compute(newLen, (k, v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(pp);
                    v.sort((a, b) -> a - b);
                    return v;
                });
            }
        }

        for (int r = 0; r < disc.size(); r++) {
            response += r * disc.get(r);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private void fill(int pos, int data, int len, ArrayList<Integer> disc) {
        for (int i = 0; i < len; i++) {
            disc.set(pos + i, data);
        }
    }

}
