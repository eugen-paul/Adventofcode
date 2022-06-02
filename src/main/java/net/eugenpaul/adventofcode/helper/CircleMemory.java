package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

import lombok.Getter;

public class CircleMemory<T> {

    public class CirclePosition {
        @Getter
        private T data;
        private CirclePosition prev;
        private CirclePosition next;
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

        after.next.prev = newPosition;

        after.next = newPosition;

        if (last == after) {
            last = newPosition;
        }

        size++;

        return newPosition;
    }

    public CirclePosition moveNext(CirclePosition pos) {
        return pos.next;
    }

    public CirclePosition moveNext(CirclePosition pos, int steps) {
        CirclePosition response = pos;
        for (int i = 0; i < steps; i++) {
            response = response.next;
        }
        return response;
    }

    public CirclePosition removeAndMoveNext(CirclePosition pos) {
        if (size <= 1) {
            size = 0;
            first = null;
            last = null;
            return null;
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
