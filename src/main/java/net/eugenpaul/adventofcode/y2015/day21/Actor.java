package net.eugenpaul.adventofcode.y2015.day21;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Actor {

    protected int hitpoints;

    public abstract int getDamage();

    public abstract int getArmor();

    /**
     * how many attacks can the player survive.
     * 
     * @param attacker
     * @return turn to Death
     */
    public int surviveTurns(Actor attacker) {
        int hitDamage = Math.max(1, attacker.getDamage() - getArmor());

        int turnCount = hitpoints / hitDamage;

        return (turnCount * hitDamage == hitpoints) ? turnCount - 1 : turnCount;
    }
}
