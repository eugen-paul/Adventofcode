package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Range {

    private long from;
    private long to;

    /**
     * Create Range from string
     * 
     * @param data
     * @param delimer
     * @return
     */
    public static Range fromString(String data, String delimer) {
        var xy = data.split(delimer);
        if (xy.length != 2) {
            throw new IllegalArgumentException(data);
        }

        return new Range(//
                Long.parseLong(xy[0]), //
                Long.parseLong(xy[1]) //
        );
    }

    /**
     * Ensure from <= to
     */
    public Range sort() {
        if (to < from) {
            long temp = from;
            from = to;
            to = temp;
        }
        return this;
    }

    /**
     * Returns a sequential ordered LongStream from "from" (inclusive) to "to" (inclusive) by an incremental step of 1.
     * 
     * @return
     */
    public static IntStream stream(int from, int to) {
        int f = Math.min(from, to);
        int t = Math.max(from, to);
        return IntStream.rangeClosed(f, t);
    }

    /**
     * Returns a sequential ordered LongStream from "from" (inclusive) to "to" (inclusive) by an incremental step of 1.
     * 
     * @return
     */
    public LongStream stream() {
        long f = Math.min(from, to);
        long t = Math.max(from, to);
        return LongStream.rangeClosed(f, t);
    }

    /**
     * Check if this range contain other range
     * 
     * @param other
     * @return
     */
    public boolean isContain(Range other) {
        return from <= other.from//
                && to >= other.to;
    }

    /**
     * Check if this range overlap other range
     * 
     * @param other
     * @return
     */
    public boolean isOverlap(Range other) {
        if (to < other.from) {
            return false;
        }

        return from <= other.to;
    }

    public void forEach(LongConsumer consumer) {
        for (long i = from; i <= to; i++) {
            consumer.accept(i);
        }
    }

    /**
     * Subtract other range from this range
     * 
     * @param other
     * @return list of ranges after subtraction
     */
    public List<Range> subtract(Range other) {
        List<Range> response = new LinkedList<>();
        if (!isOverlap(other)) {
            response.add(this);
            return response;
        }

        if (other.isContain(this)) {
            return response;
        }

        if (from < other.from) {
            response.add(new Range(from, other.from - 1));
        }

        if (to > other.to) {
            response.add(new Range(other.to + 1, to));
        }

        return response;
    }

    public Range copy() {
        return new Range(from, to);
    }

    /**
     * Merge two ranges
     * 
     * @param a
     * @param b
     * @return list of merged ranges
     */
    public static List<Range> merge(Range a, Range b) {
        if (a.isOverlap(b)) {
            return List.of(new Range(Math.min(a.from, b.from), Math.max(a.to, b.to)));
        }

        return List.of(a.copy(), b.copy());
    }

    /**
     * Merge range b into list of ranges a
     * 
     * @param a
     * @param b
     * @return list of merged ranges
     */
    public static List<Range> merge(List<Range> a, Range b) {
        var aSorted = a.stream()//
                .sorted((v1, v2) -> (int) (v1.from - v2.from))//
                .toList();

        List<Range> response = new LinkedList<>();
        Range currentMerge = b.copy();

        var aIt = aSorted.iterator();
        while (aIt.hasNext()) {
            var aNext = aIt.next();
            if (aNext.isOverlap(currentMerge)) {
                currentMerge = new Range(Math.min(aNext.from, b.from), Math.max(aNext.to, b.to));
            } else {
                response.add(aNext);
            }
        }

        response.add(currentMerge);

        return response;
    }
}
