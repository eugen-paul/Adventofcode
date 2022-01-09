package net.eugenpaul.adventofcode.y2015.day22.spells;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

public class Poison extends Spell {

    private static final int MANA_COST = 173;
    private static final int DURATION = 6;
    private static final int DAMAGE = 3;
    private static final String NAME = "Poison";
    private static final String LOG_DMG = NAME + " deals " + DAMAGE + " to ";
    private static final String LOG_ACTIVATE = "cast " + NAME + " to ";
    private static final String LOG_DEACTIVATE = NAME + " is off";

    public Poison() {
        super(DURATION, NAME, MANA_COST);
    }

    @Override
    public String aktivateOnEnemy(Actor actor) {
        return LOG_ACTIVATE + actor.getName();
    }

    @Override
    public String computeEffectOnEnemy(Actor actor) {
        actor.setHitpoints(actor.getHitpoints() - DAMAGE);
        return LOG_DMG + actor.getName();
    }

    @Override
    public String deaktivateOnEnemy(Actor actor) {
        return LOG_DEACTIVATE;
    }

    @Override
    public String aktivateOnFriend(Actor actor) {
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
