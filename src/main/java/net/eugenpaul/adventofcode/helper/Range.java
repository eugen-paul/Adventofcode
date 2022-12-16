package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Range {

    @Getter
    @Setter
    private long from;

    @Getter
    @Setter
    private long to;

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

    public void sort() {
        if (to < from) {
            long temp = from;
            from = to;
            to = temp;
        }
    }

    public boolean isContain(Range other) {
        return from <= other.from//
                && to >= other.to;
    }

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
}
