package net.eugenpaul.adventofcode.y2015.day21;

public abstract class Actor {

    protected int hitpoints;

    public abstract int getDamage();

    public abstract int getArmor();

    protected Actor(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    /**
     * how many attacks can the player survive.
     * 
     * @param attacker
     * @return turn to Death
     */
    public int surviveTurns(Actor attacker) {
        int hitDamage = attacker.getDamage() - getArmor();
        if (hitDamage < 1) {
            hitDamage = 1;
        }

        int turnCount = hitpoints / hitDamage;

        return (turnCount * hitDamage == hitpoints) ? turnCount - 1 : turnCount;
    }
}
