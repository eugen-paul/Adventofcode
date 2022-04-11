package net.eugenpaul.adventofcode.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SimplePos {
    private int x;
    private int y;

    public void move(Direction direction) {
        switch (direction) {
        case N:
            y--;
            break;
        case S:
            y++;
            break;
        case O:
            x++;
            break;
        case W:
            x--;
            break;
        default:
            break;
        }
    }

    public SimplePos moveNew(Direction direction) {
        SimplePos pos = new SimplePos(x, y);
        pos.move(direction);
        return pos;
    }
}
