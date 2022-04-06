package net.eugenpaul.adventofcode.helper;

public enum Direction {
    N, //
    O, //
    S, //
    W //
    ;

    public Direction turnLeft() {
        switch (this) {
        case N:
            return W;
        case W:
            return S;
        case S:
            return O;
        case O:
            return N;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction turnRight() {
        switch (this) {
        case N:
            return O;
        case O:
            return S;
        case S:
            return W;
        case W:
            return N;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    public Direction reverse() {
        switch (this) {
        case N:
            return S;
        case S:
            return N;
        case O:
            return W;
        case W:
            return O;
        default:
            break;
        }
        throw new IllegalArgumentException();
    }
}
