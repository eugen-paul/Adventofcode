package net.eugenpaul.adventofcode.y2018.day15;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.eugenpaul.adventofcode.helper.SimplePos;

@AllArgsConstructor
@Data
public class Entity {
    private SimplePos pos;
    private boolean good;
    private int hitPoints;
    private int attackPower;
}
