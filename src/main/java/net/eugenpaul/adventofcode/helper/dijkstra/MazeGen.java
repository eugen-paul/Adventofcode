package net.eugenpaul.adventofcode.helper.dijkstra;

import java.util.List;

public interface MazeGen<T extends DijkstraStepGen> {
    List<T> getNextSteps(T from);
}
