package net.eugenpaul.adventofcode.y2015.day22.spells;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

public class Shield extends Spell {

    private static final int MANA_COST = 113;
    private static final int DURATION = 6;
    private static final int ARMOR_BONUS = 7;
    private static final String NAME = "Shield";
    private static final String LOG_SHIELD_ON = "cast " + NAME + ", increasing armor by " + ARMOR_BONUS + " by ";
    private static final String LOG_SHIELD_OFF = NAME + " wears off, decreasing armor by " + ARMOR_BONUS + " by ";
    
    public Shield() {
        super(DURATION, NAME, MANA_COST);
    }

    @Override
    public String aktivateOnEnemy(Actor actor) {
        return null;
    }

    @Override
    public String computeEffectOnEnemy(Actor actor) {
        return null;
    }

    @Override
    public String deaktivateOnEnemy(Actor actor) {
        return null;
    }

    @Override
    public String aktivateOnFriend(Actor actor) {
        actor.setArmor(actor.getArmor() + ARMOR_BONUS);
        return LOG_SHIELD_ON + actor.getName();
    }

    @Override
    public String computeEffectOnFriend(Actor actor) {
        return null;
    }

    @Override
    public String deaktivateOnFriend(Actor actor) {
        actor.setArmor(actor.getArmor() - ARMOR_BONUS);
        return LOG_SHIELD_OFF + actor.getName();
    }

}
