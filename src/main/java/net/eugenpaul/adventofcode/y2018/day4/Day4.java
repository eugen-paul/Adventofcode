package net.eugenpaul.adventofcode.y2018.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {
    private static final String GUARD_FORMAT = "^\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] Guard #(\\d+) begins shift$";
    private static final String ASLEEP_FORMAT = "^\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] falls asleep$";
    private static final String WAKEUP_FORMAT = "^\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] wakes up$";
    private static final Pattern PATTERN_GUARD = Pattern.compile(GUARD_FORMAT);
    private static final Pattern PATTERN_ASLEEP = Pattern.compile(ASLEEP_FORMAT);
    private static final Pattern PATTERN_WAKEUP = Pattern.compile(WAKEUP_FORMAT);

    @Getter
    private long idMultipliedMinute;
    @Getter
    private long idMultipliedMinute2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2018/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<Integer, Map<Integer, Integer>> guardSleepTime = computeRecords(eventData);

        Map<Integer, Integer> totalSleepTime1 = guardSleepTime.entrySet().stream()//
                .collect(Collectors.toMap(//
                        k -> k.getKey().intValue(), //
                        v -> v.getValue().values().stream().reduce(0, (a, b) -> a + b)//
                ));

        var guardWithMaxSleep = totalSleepTime1.entrySet().stream()//
                .sorted((a, b) -> b.getValue() - a.getValue())//
                .collect(Collectors.toList())//
                .get(0);

        var maxMinuteOfGuardWithMaxSleep = guardSleepTime.get(guardWithMaxSleep.getKey()).entrySet().stream()//
                .sorted((a, b) -> b.getValue() - a.getValue())//
                .collect(Collectors.toList()).get(0);

        Map<Integer, Integer> maxSleepProGuard = guardSleepTime.entrySet().stream()//
                .collect(Collectors.toMap(//
                        k -> k.getKey().intValue(), //
                        v -> v.getValue().values().stream().reduce(0, Math::max)//
                ));

        var guardWithMaxFrequentlySleep = maxSleepProGuard.entrySet().stream()//
                .sorted((a, b) -> b.getValue() - a.getValue())//
                .collect(Collectors.toList())//
                .get(0);

        var maxMinuteOfGuardWithMaxFrequentlySleep = guardSleepTime.get(guardWithMaxFrequentlySleep.getKey()).entrySet().stream()//
                .sorted((a, b) -> b.getValue() - a.getValue())//
                .collect(Collectors.toList()).get(0);

        idMultipliedMinute = (long) guardWithMaxSleep.getKey() * maxMinuteOfGuardWithMaxSleep.getKey();
        idMultipliedMinute2 = (long) guardWithMaxFrequentlySleep.getKey() * maxMinuteOfGuardWithMaxFrequentlySleep.getKey();

        logger.log(Level.INFO, () -> "idMultipliedMinute  : " + getIdMultipliedMinute());
        logger.log(Level.INFO, () -> "idMultipliedMinute2 : " + getIdMultipliedMinute2());

        return true;
    }

    private Map<Integer, Map<Integer, Integer>> computeRecords(List<String> eventData) {
        Map<Integer, Map<Integer, Integer>> responseRecords = new HashMap<>();

        List<String> sortedData = eventData.stream().sorted().collect(Collectors.toList());

        int currentGuard = -1;
        int sleepFrom = -1;
        for (String data : sortedData) {
            Matcher guardMatcher = PATTERN_GUARD.matcher(data);
            Matcher asleepMatcher = PATTERN_ASLEEP.matcher(data);
            Matcher wakeupMatcher = PATTERN_WAKEUP.matcher(data);
            if (guardMatcher.find()) {
                currentGuard = getCurrentGuard(guardMatcher);
            } else if (asleepMatcher.find()) {
                sleepFrom = getAsleepTime(asleepMatcher);
            } else if (wakeupMatcher.find()) {
                computeGuardSleepRecord(responseRecords, currentGuard, sleepFrom, wakeupMatcher);
            }
        }

        return responseRecords;
    }

    private void computeGuardSleepRecord(Map<Integer, Map<Integer, Integer>> responseRecords, int currentGuard, int sleepFrom, Matcher wakeupMatcher) {
        int sleepTo = Integer.parseInt(wakeupMatcher.group(5));

        if (currentGuard >= 0 && sleepFrom >= 0) {
            for (int i = sleepFrom; i < sleepTo; i++) {
                responseRecords.computeIfAbsent(currentGuard, k -> new HashMap<>()) //
                        .compute(i, (k, v) -> (v == null) ? 1 : ++v);
            }
        }
    }

    private int getAsleepTime(Matcher asleepMatcher) {
        int sleepFrom;
        sleepFrom = Integer.parseInt(asleepMatcher.group(5));
        return sleepFrom;
    }

    private int getCurrentGuard(Matcher guardMatcher) {
        int currentGuard;
        currentGuard = Integer.parseInt(guardMatcher.group(6));
        return currentGuard;
    }

}
