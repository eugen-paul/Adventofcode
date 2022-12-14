package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SimplePos {
    private int x;
    private int y;

    public SimplePos move(Direction direction) {
        switch (direction) {
        case N:
            y--;
            break;
        case S:
            y++;
            break;
        case E:
            x++;
            break;
        case W:
            x--;
            break;
        default:
            break;
        }

        return this;
    }

    public static SimplePos fromData(String data, String delimer) {
        var xy = data.split(delimer);
        if (xy.length != 2) {
            throw new IllegalArgumentException(data);
        }

        return new SimplePos(//
                Integer.parseInt(xy[0]), //
                Integer.parseInt(xy[1]) //
        );
    }

    /**
     * Create new position. Move position depending on direction and return new position.
     * 
     * @param direction
     * @return new Position
     */
    public SimplePos moveNew(Direction direction) {
        SimplePos pos = new SimplePos(x, y);
        pos.move(direction);
        return pos;
    }

    /**
     * Add b to current
     * 
     * @param b
     */
    public void add(SimplePos b) {
        x += b.getX();
        y += b.getY();
    }

    public SimplePos addNew(SimplePos b) {
        return new SimplePos(//
                x + b.x, //
                y + b.y //
        );
    }

    public SimplePos subNew(SimplePos b) {
        return new SimplePos(//
                x - b.x, //
                y - b.y //
        );
    }

    public SimplePos copy() {
        return new SimplePos(x, y);
    }

    public List<SimplePos> getNeighbors(boolean diagonal) {
        List<SimplePos> response = new LinkedList<>();
        response.add(new SimplePos(x + 1, y));
        response.add(new SimplePos(x, y + 1));
        response.add(new SimplePos(x - 1, y));
        response.add(new SimplePos(x, y - 1));

        if (diagonal) {
            response.add(new SimplePos(x + 1, y + 1));
            response.add(new SimplePos(x + 1, y - 1));
            response.add(new SimplePos(x - 1, y + 1));
            response.add(new SimplePos(x - 1, y - 1));
        }
        return response;
    }

    /**
     * call consumer for each point on box
     * 
     * @param to
     * @param consumer
     */
    public void forEach(SimplePos to, Consumer<SimplePos> consumer) {
        int minX = Math.min(x, to.x);
        int maxX = Math.max(x, to.x);
        int minY = Math.min(y, to.y);
        int maxY = Math.max(y, to.y);

        for (int currentX = minX; currentX <= maxX; currentX++) {
            for (int currentY = minY; currentY <= maxY; currentY++) {
                consumer.accept(new SimplePos(currentX, currentY));
            }
        }
    }

    /**
     * call consumer for each point on box
     * 
     * @param to
     * @param consumer
     */
    public void forEachDiagonaly(SimplePos to, Consumer<SimplePos> consumer) {
        int deltaX = 0;
        int deltaY = 0;
        if (x > to.x) {
            deltaX = -1;
        } else if (x < to.x) {
            deltaX = 1;
        }

        if (y > to.y) {
            deltaY = -1;
        } else if (y < to.y) {
            deltaY = 1;
        }

        var position = copy();
        consumer.accept(position);

        while (!position.equals(to)) {
            position = new SimplePos(position.x + deltaX, position.y + deltaY);
            consumer.accept(position);
        }
    }

    public boolean isHorizontalOrVertical(SimplePos other) {
        return isHorizontal(other) || isVertical(other);
    }

    private boolean isHorizontal(SimplePos other) {
        return y == other.y;
    }

    private boolean isVertical(SimplePos other) {
        return x == other.x;
    }

}
