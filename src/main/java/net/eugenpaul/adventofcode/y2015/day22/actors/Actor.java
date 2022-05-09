package net.eugenpaul.adventofcode.y2015.day22.actors;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Actor {

    private String name;
    private int hitpoints;
    private int damage;
    private int armor;
    private int mana;

    public Actor(Actor actor) {
        this.name = actor.name;
        this.hitpoints = actor.hitpoints;
        this.damage = actor.damage;
        this.armor = actor.armor;
        this.mana = actor.mana;
    }

    public static Actor initBoss(List<String> bossData) {
        Integer hitpoints = null;
        Integer damage = null;
        for (String data : bossData) {
            if (data.startsWith("Hit Points: ")) {
                hitpoints = Integer.parseInt(data.substring("Hit Points: ".length()));
            }
            if (data.startsWith("Damage: ")) {
                damage = Integer.parseInt(data.substring("Damage: ".length()));
            }
        }

        if (hitpoints == null || damage == null) {
            return null;
        }
        return new Actor("Boss", hitpoints.intValue(), damage.intValue(), 0, 0);
    }
}
