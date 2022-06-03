package net.eugenpaul.adventofcode.y2016.day20;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day20 extends SolutionTemplate {

    @Getter
    private long lowestIp;

    @Getter
    private long ipCount;

    @Setter
    private long min = 0L;
    @Setter
    private long max = 4294967295L;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2016/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
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

        logger.log(Level.INFO, () -> "lowestIp " + getLowestIp());
        logger.log(Level.INFO, () -> "ipCount " + getIpCount());

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
