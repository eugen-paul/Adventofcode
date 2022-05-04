package net.eugenpaul.adventofcode.y2015.day21;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item {
    private ItemType type;
    private String name;
    private int cost;
    private int damage;
    private int armor;
}
