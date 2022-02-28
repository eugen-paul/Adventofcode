package net.eugenpaul.adventofcode.y2016.day21.operation;

public class OperationFactory {
    private OperationFactory() {

    }

    public static ScrambleOperation fromString(String operation) {

        String prefix = operation.substring(0, 8);
        switch (prefix) {
        case "swap pos":
            return SwapPosition.fromString(operation);
        case "swap let":
            return SwapLetter.fromString(operation);
        case "rotate l", "rotate r":
            return Rotate.fromString(operation);
        case "rotate b":
            return RotateBased.fromString(operation);
        case "reverse ":
            return Reverse.fromString(operation);
        case "move pos":
            return Move.fromString(operation);
        default:
            break;
        }
        return null;
    }
}
