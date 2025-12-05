package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
     * Check if this range contain a point
     * 
     * @param a
     * @return
     */
    public boolean contain(long a) {
        return from <= a && a <= to;
    }

    /**
     * Calculates the size of the range.
     * 
     * @return the number of elements in this range, inclusive of both endpoints
     */
    public long size(){
        return to - from + 1;
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
     * Merge all input ranges
     * 
     * @param b
     * @return list of merged ranges
     */
    public static List<Range> merge(List<Range> a) {
        if(a.isEmpty()){
            return Collections.emptyList();
        }

        List<Range> response = new ArrayList<>();
        var aSorted = a.stream()//
                .sorted((v1, v2) -> Long.compare(v1.from, v2.from))//
                .toList();

        Range current = aSorted.get(0);
        for(var r: aSorted){
            if(current.getTo() < r.getFrom() - 1){
                response.add(current);
                current = r;
            } else {
                current = new Range(current.from, Math.max(current.to, r.to));
            }
        }
        response.add(current);
        return response;
    }

    /**
     * Merge all input ranges. Adjacent regions are not joined together.
     * 
     * @param b
     * @return list of merged ranges
     */
    public static List<Range> merge2(List<Range> a) {
        if(a.isEmpty()){
            return Collections.emptyList();
        }

        TreeSet<Long> all = new TreeSet<>();

        for(var r: a){
            all.add(r.from);
            all.add(r.to + 1);
        }

        List<Range> response = new ArrayList<>();
        long start = all.pollFirst();
        while(!all.isEmpty()){
            long end = all.pollFirst();
            if(a.stream().anyMatch(v->v.contain(end-1))){ 
                response.add(new Range(start, end - 1));
            }
            start = end;
        }
        return response;
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
                .sorted((v1, v2) -> Long.compare(v1.from, v2.from))//
                .toList();

        List<Range> response = new LinkedList<>();
        Range currentMerge = b.copy();

        var aIt = aSorted.iterator();
        while (aIt.hasNext()) {
            var aNext = aIt.next();
            if (aNext.isOverlap(currentMerge)) {
                long f = Math.min(aNext.from, b.from);
                f = Math.min(currentMerge.from, f);
                
                long t = Math.max(aNext.to, b.to);
                t = Math.max(currentMerge.to, t);

                currentMerge = new Range(f, t);
            } else {
                response.add(aNext);
            }
        }

        response.add(currentMerge);

        return response;
    }
}
