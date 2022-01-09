package net.eugenpaul.adventofcode.y2015.day22.spells;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

public class MagicMissle extends Spell {

    private static final int MANA_COST = 53;
    private static final int DAMAGE = 4;
    private static final int DURATION = 1;
    private static final String NAME = "MagicMissle";
    private static final String LOG_DMG = "cast " + NAME + ", dealing " + DAMAGE + " damage to ";

    public MagicMissle() {
        super(DURATION, NAME, MANA_COST);
    }

    @Override
    public String aktivateOnEnemy(Actor actor) {
        actor.setHitpoints(actor.getHitpoints() - DAMAGE);
        return LOG_DMG + actor.getName();
    }

    @Override
    public String aktivateOnFriend(Actor actor) {
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
    public String computeEffectOnFriend(Actor actor) {
        return null;
    }

    @Override
    public String deaktivateOnFriend(Actor actor) {
        return null;
    }

}
