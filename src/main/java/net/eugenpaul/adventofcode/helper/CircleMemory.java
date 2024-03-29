package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CircleMemory<T> {

    @AllArgsConstructor
    @NoArgsConstructor
    public class CirclePosition {
        @Getter
        private T data;
        private CirclePosition prev;
        private CirclePosition next;

        public CirclePosition copy() {
            return new CirclePosition(data, prev, next);
        }
    }

    private CirclePosition first = null;
    private CirclePosition last = null;

    @Getter
    private int size = 0;

    public CirclePosition addFirst(T data) {
        CirclePosition newPosition = new CirclePosition();
        newPosition.data = data;

        if (first == null) {
            newPosition.prev = newPosition;
            newPosition.next = newPosition;
            first = newPosition;
            last = newPosition;
        } else {
            newPosition.prev = last;
            newPosition.next = first;
            first.prev = newPosition;
            first = newPosition;
            last.next = newPosition;
        }

        size++;

        return newPosition;
    }

    public CirclePosition addLast(T data) {
        CirclePosition newPosition = new CirclePosition();
        newPosition.data = data;

        if (first == null) {
            newPosition.prev = newPosition;
            newPosition.next = newPosition;
            first = newPosition;
            last = newPosition;
        } else {
            newPosition.prev = last;
            newPosition.next = first;
            first.prev = newPosition;
            last.next = newPosition;
            last = newPosition;
        }

        size++;

        return newPosition;
    }

    public CirclePosition add(T data, CirclePosition after) {
        if (after == null) {
            throw new IllegalArgumentException();
        }

        CirclePosition newPosition = new CirclePosition();
        newPosition.data = data;
        newPosition.prev = after;
        newPosition.next = after.next;

        after.next = newPosition;
        newPosition.next.prev = newPosition;

        if (last == after) {
            last = newPosition;
        }

        size++;

        return newPosition;
    }

    public CirclePosition add(CirclePosition data, CirclePosition after) {
        if (after == null) {
            throw new IllegalArgumentException();
        }

        data.prev = after;
        data.next = after.next;

        after.next = data;
        data.next.prev = data;

        if (last == after) {
            last = data;
        }

        size++;

        return data;
    }

    public CirclePosition moveNext(CirclePosition pos) {
        return pos.next;
    }

    public CirclePosition moveNext(CirclePosition pos, int steps) {
        if (steps == 0) {
            return pos;
        }

        if (steps > 0) {
            CirclePosition response = pos;
            for (int i = 0; i < steps; i++) {
                response = response.next;
            }
            return response;
        }

        CirclePosition response = pos;
        for (int i = 0; i > steps; i--) {
            response = response.prev;
        }
        return response;
    }

    public CirclePosition removeAndGetPrev(CirclePosition pos) {
        if (size <= 1) {
            size = 0;
            first = null;
            last = null;
            return null;
        }

        if (pos == first) {
            first = pos.next;
        }

        if (pos == last) {
            last = pos.prev;
        }

        CirclePosition prev = pos.prev;

        prev.next = pos.next;

        CirclePosition next = pos.next;
        next.prev = pos.prev;

        size--;
        return prev;
    }

    public CirclePosition removeAndGetNext(CirclePosition pos) {
        if (size <= 1) {
            size = 0;
            first = null;
            last = null;
            return null;
        }

        if (pos == first) {
            first = pos.next;
        }

        if (pos == last) {
            last = pos.prev;
        }

        CirclePosition prev = pos.prev;

        prev.next = pos.next;

        CirclePosition next = pos.next;
        next.prev = pos.prev;

        size--;
        return next;
    }

    public T getData(CirclePosition pos) {
        return pos.data;
    }

    public void forEach(Consumer<T> consumer) {
        if (first == null) {
            return;
        }

        CirclePosition current = first;
        do {
            consumer.accept(current.data);
            current = current.next;
        } while (current != first);
    }
}
