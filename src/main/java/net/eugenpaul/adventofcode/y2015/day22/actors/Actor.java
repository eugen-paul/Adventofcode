package net.eugenpaul.adventofcode.y2015.day22.actors;

import java.util.List;

public class Actor {

    private String name;
    private int hitpoints;
    private int damage;
    private int armor;
    private int mana;

    public Actor(String name, int hitpoints, int damage, int armor, int mana) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.damage = damage;
        this.armor = armor;
        this.mana = mana;
    }

    public Actor(Actor actor) {
        this.name = actor.name;
        this.hitpoints = actor.hitpoints;
        this.damage = actor.damage;
        this.armor = actor.armor;
        this.mana = actor.mana;
    }

    public static Actor fromList(List<String> bossData) {
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

    public int getHitpoints() {
        return this.hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getName() {
        return name;
    }

}
