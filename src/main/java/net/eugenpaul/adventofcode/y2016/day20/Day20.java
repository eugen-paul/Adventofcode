package net.eugenpaul.adventofcode.y2016.day20;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day20 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day20.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day20.class.getName());

    @Getter
    private long lowestIp;

    @Getter
    private long ipCount;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2016/day20/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, 0, 4294967295L);
    }

    public boolean doPuzzleFromData(List<String> eventData, long min, long max) {
        if (!doEvent(eventData, min, max)) {
            return false;
        }

        logger.log(Level.INFO, () -> "lowestIp " + getLowestIp());
        logger.log(Level.INFO, () -> "ipCount " + getIpCount());

        return true;
    }

    private boolean doEvent(List<String> eventData, long min, long max) {
        if (null == eventData) {
            return false;
        }

        List<Firewall> firewalls = eventData.stream()//
                .map(Firewall::fromString)//
                .collect(Collectors.toList());

        List<Long> from = firewalls.stream()//
                .map(Firewall::getFrom)//
                .sorted()//
                .collect(Collectors.toList());

        List<Long> to = firewalls.stream()//
                .map(Firewall::getTo)//
                .sorted()//
                .collect(Collectors.toList());

        List<Firewall> allowedIps = getAllowedIpList(from, to, min, max);

        lowestIp = allowedIps.get(0).getFrom();
        ipCount = allowedIps.stream()//
                .map(v -> v.getTo() - v.getFrom() + 1)//
                .reduce(0L, (a, b) -> a + b);

        return true;
    }

    private List<Firewall> getAllowedIpList(List<Long> from, List<Long> to, long min, long max) {
        Iterator<Long> fromIterator = from.iterator();
        Iterator<Long> toIterator = to.iterator();

        long currentFrom = fromIterator.next();
        long currentTo = toIterator.next();

        long ipToCheck = min;
        int depth = 0;

        List<Firewall> allowedIps = new LinkedList<>();

        while (ipToCheck < max) {
            if ((ipToCheck < currentFrom //
                    && currentFrom <= currentTo //
                    && depth == 0//
            )) {
                long f = ipToCheck;
                long t = currentFrom - 1;
                allowedIps.add(new Firewall(f, t));
                ipToCheck = t + 1;
            }

            if (currentFrom <= currentTo) {
                if (fromIterator.hasNext()) {
                    currentFrom = fromIterator.next();
                    depth++;
                } else {
                    break;
                }
            } else {
                ipToCheck = currentTo + 1;
                if (toIterator.hasNext()) {
                    depth--;
                    currentTo = toIterator.next();
                }
            }
        }

        ipToCheck = to.get(to.size() - 1) + 1;
        if (ipToCheck < max) {
            allowedIps.add(new Firewall(ipToCheck, max));
        }

        return allowedIps;
    }

}
