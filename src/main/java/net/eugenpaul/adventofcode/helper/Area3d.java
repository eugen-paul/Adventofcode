package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.eugenpaul.adventofcode.helper.consumer.TriConsumer;

@AllArgsConstructor
@Data
public class Area3d {
    private int x;
    private int y;
    private int z;

    private int width;
    private int height;
    private int deep;

    public SimplePos getPos() {
        return new SimplePos(x, y);
    }

    public List<Pos3d> getVertexes() {
        List<Pos3d> response = new LinkedList<>();

        response.add(new Pos3d(x, y, z));
        response.add(new Pos3d(x, y + height - 1L, z));
        response.add(new Pos3d(x + width - 1L, y, z));
        response.add(new Pos3d(x + width - 1L, y + height - 1L, z));
        response.add(new Pos3d(x, y, z - deep - 1L));
        response.add(new Pos3d(x, y + height - 1L, z - deep - 1L));
        response.add(new Pos3d(x + width - 1L, y, z - deep - 1L));
        response.add(new Pos3d(x + width - 1L, y + height - 1L, z - deep - 1L));

        return response;
    }

    public void forEach(TriConsumer<Integer, Integer, Integer> consumer) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                for (int zPos = 0; zPos < deep + z; zPos++) {
                    consumer.accept(xPos, yPos, zPos);
                }
            }
        }
    }

    public void forEach(TriConsumer<Integer, Integer, Integer> consumer, BooleanSupplier breakSupplier) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                for (int zPos = 0; zPos < deep + z; zPos++) {
                    consumer.accept(xPos, yPos, zPos);
                    if (breakSupplier.getAsBoolean()) {
                        return;
                    }
                }
            }
        }
    }

    public Area3d copy() {
        return new Area3d(x, y, z, width, height, deep);
    }

    public List<Area3d> divide() {
        List<Area3d> response = new LinkedList<>();

        if (width == 1 && height == 1 && deep == 1) {
            response.add(copy());
            return response;
        }

        if (width == 1 && height == 1) {
            int d1 = deep / 2;
            response.add(new Area3d(x, y, z, width, height, d1));
            response.add(new Area3d(x, y, z + d1, width, height, deep - d1));
        } else if (width == 1 && deep == 1) {
            int h1 = height / 2;
            response.add(new Area3d(x, y, z, width, h1, deep));
            response.add(new Area3d(x, y + h1, z, width, height - h1, deep));
        } else if (height == 1 && deep == 1) {
            int w1 = width / 2;
            response.add(new Area3d(x, y, z, w1, height, deep));
            response.add(new Area3d(x + w1, y, z, width - w1, height, deep));
        } else if (width == 1) {
            int h1 = height / 2;
            int d1 = deep / 2;
            response.add(new Area3d(x, y, z, width, h1, d1));
            response.add(new Area3d(x, y + h1, z, width, height - h1, d1));
            response.add(new Area3d(x, y, z + d1, width, h1, deep - d1));
            response.add(new Area3d(x, y + h1, z + d1, width, height - h1, deep - d1));
        } else if (height == 1) {
            int w1 = width / 2;
            int d1 = deep / 2;
            response.add(new Area3d(x, y, z, w1, height, d1));
            response.add(new Area3d(x + w1, y, z, width - w1, height, d1));
            response.add(new Area3d(x, y, z + d1, w1, height, deep - d1));
            response.add(new Area3d(x + w1, y, z + d1, width - w1, height, deep - d1));
        } else if (deep == 1) {
            int w1 = width / 2;
            int h1 = height / 2;
            response.add(new Area3d(x, y, z, w1, h1, deep));
            response.add(new Area3d(x + w1, y, z, width - w1, h1, deep));
            response.add(new Area3d(x, y + h1, z, w1, height - h1, deep));
            response.add(new Area3d(x + w1, y + h1, z, width - w1, height - h1, deep));
        } else {
            int w1 = width / 2;
            int h1 = height / 2;
            int d1 = deep / 2;
            response.add(new Area3d(x, y, z, w1, h1, d1));
            response.add(new Area3d(x + w1, y, z, width - w1, h1, d1));
            response.add(new Area3d(x, y + h1, z, w1, height - h1, d1));
            response.add(new Area3d(x + w1, y + h1, z, width - w1, height - h1, d1));
            response.add(new Area3d(x, y, z + d1, w1, h1, deep - d1));
            response.add(new Area3d(x + w1, y, z + d1, width - w1, h1, deep - d1));
            response.add(new Area3d(x, y + h1, z + d1, w1, height - h1, deep - d1));
            response.add(new Area3d(x + w1, y + h1, z + d1, width - w1, height - h1, deep - d1));
        }

        return response;
    }
}
