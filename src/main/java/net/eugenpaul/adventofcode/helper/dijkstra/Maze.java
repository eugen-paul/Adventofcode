package net.eugenpaul.adventofcode.helper.dijkstra;

import java.util.List;

import net.eugenpaul.adventofcode.helper.SimplePos;

public interface Maze {
    List<SimplePos> getNextSteps(SimplePos from);
}
