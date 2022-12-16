package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
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

    public SimplePos getPosUpLeft() {
        return getPos();
    }

    public SimplePos getPosUpRight() {
        return new SimplePos(x + width - 1, y);
    }

    public SimplePos getPosDownLeft() {
        return new SimplePos(x, y + height - 1);
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
        List<Area> response = new LinkedList<>();

        if (width == 1 && height == 1) {
            response.add(copy());
            return response;
        }

        if (width == 1) {
            int h1 = height / 2;
            response.add(//
                    new Area(//
                            x, //
                            y, //
                            width, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x, //
                            y + h1, //
                            width, //
                            height - h1 //
                    )//
            );
        } else if (height == 1) {
            int w1 = width / 2;
            response.add(//
                    new Area(//
                            x, //
                            y, //
                            w1, //
                            height //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y, //
                            width - w1, //
                            height //
                    )//
            );
        } else {
            int h1 = height / 2;
            int w1 = width / 2;

            response.add(//
                    new Area(//
                            x, //
                            y, //
                            w1, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y, //
                            width - w1, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x, //
                            y + h1, //
                            w1, //
                            height - h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y + h1, //
                            width - w1, //
                            height - h1 //
                    )//
            );
        }

        return response;
    }
}
