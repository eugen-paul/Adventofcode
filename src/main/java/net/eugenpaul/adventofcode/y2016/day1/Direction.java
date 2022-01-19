package net.eugenpaul.adventofcode.y2016.day1;

public enum Direction {
    N, //
    O, //
    S, //
    W //
    ;

    public Direction nextDirection(Turn t) {
        switch (this) {
        case N:
            return (t == Turn.L) ? W : O;
        case O:
            return (t == Turn.L) ? N : S;
        case S:
            return (t == Turn.L) ? O : W;
        case W:
            return (t == Turn.L) ? S : N;
        }
        return null;
    }
}
