package net.eugenpaul.adventofcode.y2016.day7;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private long ipWithTls;
    @Getter
    private long ipWithSsl;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2016/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        ipWithTls = eventData.stream().filter(this::isTslSupport).count();
        ipWithSsl = eventData.stream().filter(this::isSslSupport).count();

        logger.log(Level.INFO, () -> "ipWithTls: " + getIpWithTls());
        logger.log(Level.INFO, () -> "ipWithSsl: " + getIpWithSsl());
        return true;
    }

    private boolean isTslSupport(String data) {
        boolean isIn = false;
        boolean isAbba = false;
        for (int i = 0; i < data.length() - 3; i++) {
            isIn = isIn(data, isIn, i);

            if (isABBA(data, i)) {
                if (isIn) {
                    return false;
                }
                isAbba = true;
            }
        }
        return isAbba;
    }

    private boolean isABBA(String data, int i) {
        return data.charAt(i) == data.charAt(i + 3)//
                && data.charAt(i + 1) == data.charAt(i + 2) //
                && data.charAt(i) != data.charAt(i + 1);
    }

    private boolean isSslSupport(String data) {
        boolean isIn = false;

        List<String> aba = new LinkedList<>();
        List<String> bab = new LinkedList<>();

        for (int i = 0; i < data.length() - 2; i++) {
            isIn = isIn(data, isIn, i);

            if (isABA(data, i)) {
                String seq = "" + data.charAt(i) + data.charAt(i + 1) + data.charAt(i);
                String seqRevert = "" + data.charAt(i + 1) + data.charAt(i) + data.charAt(i + 1);
                if (isIn) {
                    if (aba.contains(seqRevert)) {
                        return true;
                    }
                    bab.add(seq);
                } else {
                    if (bab.contains(seqRevert)) {
                        return true;
                    }
                    aba.add(seq);
                }
            }
        }
        return false;
    }

    private boolean isIn(String data, boolean isIn, int i) {
        if (data.charAt(i) == '[') {
            return true;
        } else if (data.charAt(i) == ']') {
            return false;
        }
        return isIn;
    }

    private boolean isABA(String data, int i) {
        return data.charAt(i) == data.charAt(i + 2)//
                && data.charAt(i) != data.charAt(i + 1);
    }

}
