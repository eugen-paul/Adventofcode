package net.eugenpaul.adventofcode.helper;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Area {
    private int x;
    private int y;

    private int width;
    private int height;

    public SimplePos getPos() {
        return new SimplePos(x, y);
    }

    public SimplePos getPosDownRight() {
        return new SimplePos(x + width - 1, y + height - 1);
    }

    public void forEach(BiConsumer<Integer, Integer> consumer) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                consumer.accept(xPos, yPos);
            }
        }
    }

    public void forEach(BiConsumer<Integer, Integer> consumer, BooleanSupplier breakSupplier) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                consumer.accept(xPos, yPos);
                if (breakSupplier.getAsBoolean()) {
                    return;
                }
            }
        }
    }

    public Area copy() {
        return new Area(x, y, width, height);
    }

    public List<Area> divide() {
        // TODO y2022 d15
        throw new IllegalArgumentException();
    }
}
