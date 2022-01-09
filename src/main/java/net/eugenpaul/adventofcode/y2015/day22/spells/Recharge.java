package net.eugenpaul.adventofcode.y2015.day22.spells;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

public class Recharge extends Spell {

    private static final int MANA_COST = 229;
    private static final int DURATION = 5;
    private static final int MANA_BONUS = 101;
    private static final String NAME = "Recharge";
    private static final String LOG_MANA = NAME + " provides " + MANA_BONUS + " mana to ";
    private static final String LOG_ACTIVATE = "cast " + NAME + " to ";
    private static final String LOG_DEACTIVATE = NAME + " is off";

    public Recharge() {
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
        return LOG_ACTIVATE + actor.getName();
    }

    @Override
    public String computeEffectOnFriend(Actor actor) {
        actor.setMana(actor.getMana() + MANA_BONUS);
        return LOG_MANA + actor.getName();
    }

    @Override
    public String deaktivateOnFriend(Actor actor) {
        return LOG_DEACTIVATE;
    }

}
