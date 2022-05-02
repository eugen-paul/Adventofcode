package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

import lombok.Getter;

public class CircleMemory<T> {

    public class CirclePosition {
        private T data;
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
            newPosition.next = newPosition;
            first = newPosition;
            last = newPosition;
        } else {
            newPosition.next = first;
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
            newPosition.next = newPosition;
            first = newPosition;
            last = newPosition;
        } else {
            newPosition.next = first;
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
        newPosition.next = after;

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
