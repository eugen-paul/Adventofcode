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

    public static Area3d fromMinMaxXYZ(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        return new Area3d(minX, minY, minZ, maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
    }

    private boolean isOk() {
        return width > 0 && height > 0 && deep > 0;
    }

    private void addIfOk(List<Area3d> list, Area3d toAdd) {
        if (toAdd.isOk()) {
            list.add(toAdd);
        }
    }

    public List<Area3d> substract(Area3d other) {
        if (x + width - 1 < other.x || x > other.x + other.width - 1 //
                || y + height - 1 < other.y || y > other.y + other.height - 1 //
                || z + deep - 1 < other.z || z > other.z + other.deep - 1 //
        ) {
            return List.of(this.copy());
        }

        List<Area3d> response = new LinkedList<>();
        int x11 = x;
        int x12 = other.x - 1;
        int x21 = Math.max(x, other.x);
        int x22 = Math.min(x + width - 1, other.x + other.width - 1);
        int x31 = other.x + other.width;
        int x32 = x + width - 1;

        int y11 = y;
        int y12 = other.y - 1;
        int y21 = Math.max(y, other.y);
        int y22 = Math.min(y + height - 1, other.y + other.height - 1);
        int y31 = other.y + other.height;
        int y32 = y + height - 1;

        int z11 = z;
        int z12 = other.z - 1;
        int z21 = Math.max(z, other.z);
        int z22 = Math.min(z + deep - 1, other.z + other.deep - 1);
        int z31 = other.z + other.deep;
        int z32 = z + deep - 1;

        // ---------------------------------------
        addIfOk(response, fromMinMaxXYZ(x11, x12, y11, y12, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y11, y12, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y11, y12, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y21, y22, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y21, y22, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y21, y22, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y31, y32, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y31, y32, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x11, x12, y31, y32, z31, z32));

        addIfOk(response, fromMinMaxXYZ(x21, x22, y11, y12, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y11, y12, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y11, y12, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y21, y22, z11, z12));
        // addIfOk(response, fromMinMaxXYZ(x21, x22, y21, y22, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y21, y22, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y31, y32, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y31, y32, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x21, x22, y31, y32, z31, z32));

        addIfOk(response, fromMinMaxXYZ(x31, x32, y11, y12, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y11, y12, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y11, y12, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y21, y22, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y21, y22, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y21, y22, z31, z32));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y31, y32, z11, z12));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y31, y32, z21, z22));
        addIfOk(response, fromMinMaxXYZ(x31, x32, y31, y32, z31, z32));

        return response;
    }
}
