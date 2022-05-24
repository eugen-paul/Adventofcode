package net.eugenpaul.adventofcode.y2016.day9;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @Getter
    private long textLength;

    @Getter
    private long textLengthV2;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2016/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        textLength = computeFieldLengthV2(eventData, new AtomicInteger(0), new Marker(eventData.length(), 1, 0), 1);

        textLengthV2 = computeFieldLengthV2(eventData, new AtomicInteger(0), new Marker(eventData.length(), 1, 0), Integer.MAX_VALUE);

        logger.log(Level.INFO, () -> "textLength: " + getTextLength());
        logger.log(Level.INFO, () -> "textLengthV2: " + getTextLengthV2());
        return true;
    }

    private long computeFieldLengthV2(String text, AtomicInteger position, Marker marker, int depthRest) {
        int endOfField = position.get() + marker.charCount;

        long count = 0;
        while (position.get() < endOfField) {
            char c = text.charAt(position.get());
            position.incrementAndGet();
            if (c == '(' && depthRest > 0) {
                Marker in = Marker.fromString(text, position.get());
                position.addAndGet(in.markerLen + 1);
                count += computeFieldLengthV2(text, position, in, depthRest - 1);
            } else {
                if (c != ' ') {
                    count++;
                }
            }
        }
        return marker.repetition * count;
    }

}
