package net.eugenpaul.adventofcode.y2016.day8.instruction;

public final class InstructionFactory {
    private InstructionFactory() {

    }

    public static Instruction fromString(String data) {
        if (data.startsWith("rect ")) {
            return Rect.fromString(data);
        } else if (data.startsWith("rotate row ")) {
            return RotateRow.fromString(data);
        } else if (data.startsWith("rotate column ")) {
            return RotateColumn.fromString(data);
        }
        return null;
    }
}
