package net.eugenpaul.adventofcode.y2016.day7;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day7 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day7.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day7.class.getName());

    @Getter
    private long ipWithTls;
    @Getter
    private long ipWithSsl;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2016/day7/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "ipWithTls: " + getIpWithTls());
        logger.log(Level.INFO, () -> "ipWithSsl: " + getIpWithSsl());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        ipWithTls = eventData.stream().filter(this::isTslSupport).count();
        ipWithSsl = eventData.stream().filter(this::isSslSupport).count();

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
            isIn = true;
        } else if (data.charAt(i) == ']') {
            isIn = false;
        }
        return isIn;
    }

    private boolean isABA(String data, int i) {
        return data.charAt(i) == data.charAt(i + 2)//
                && data.charAt(i) != data.charAt(i + 1);
    }

}
