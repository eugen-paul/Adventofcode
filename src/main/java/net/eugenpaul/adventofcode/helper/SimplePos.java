package net.eugenpaul.adventofcode.helper;

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
}
