package net.eugenpaul.adventofcode.y2016.day13;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;

@AllArgsConstructor
public class BuildingArea implements Maze {

    private int favoriteNumber;

    private boolean isOpenArea(SimplePos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int magic = x * x + 3 * x + 2 * x * y + y + y * y;
        magic += favoriteNumber;

        long bit = 1;
        int bitCount = 0;
        do {
            if ((bit & magic) != 0) {
                bitCount++;
            }
            bit = bit << 1;
        } while (bit != 0x8000_0000L);

        return bitCount % 2 == 0;
    }

    @Override
    public List<SimplePos> getNextSteps(SimplePos from) {
        List<SimplePos> response = new LinkedList<>();
        if (from.getX() > 0 && isOpenArea(from.moveNew(Direction.W))) {
            response.add(from.moveNew(Direction.W));
        }
        if (from.getY() > 0 && isOpenArea(from.moveNew(Direction.N))) {
            response.add(from.moveNew(Direction.N));
        }

        if (isOpenArea(from.moveNew(Direction.S))) {
            response.add(from.moveNew(Direction.S));
        }

        if (isOpenArea(from.moveNew(Direction.E))) {
            response.add(from.moveNew(Direction.E));
        }

        return response;
    }
}
