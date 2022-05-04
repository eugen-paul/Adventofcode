package net.eugenpaul.adventofcode.y2015.day21;

import java.util.List;

import lombok.Getter;

public class Boss extends Actor {

    @Getter
    private int damage;
    @Getter
    private int armor;

    public Boss(int hitpoints, int damage, int armor) {
        super(hitpoints);
        this.damage = damage;
        this.armor = armor;
    }

    public static Boss fromList(List<String> bossData) {
        Integer hitpoints = null;
        Integer damage = null;
        Integer armor = null;
        for (String data : bossData) {
            if (data.startsWith("Hit Points: ")) {
                hitpoints = Integer.parseInt(data.substring("Hit Points: ".length()));
            }
            if (data.startsWith("Damage: ")) {
                damage = Integer.parseInt(data.substring("Damage: ".length()));
            }
            if (data.startsWith("Armor: ")) {
                armor = Integer.parseInt(data.substring("Armor: ".length()));
            }
        }

        if (hitpoints == null || damage == null || armor == null) {
            return null;
        }
        return new Boss(hitpoints, damage, armor);
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("Boss ");
        response.append("Hitpoins: ").append(hitpoints);
        response.append(" Damage: ").append(damage);
        response.append(" Armor: ").append(armor);
        return response.toString();
    }
}
