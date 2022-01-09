package net.eugenpaul.adventofcode.y2015.day22.spells;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;

public abstract class Spell {

    private int duration;
    private int manaCost;
    private String spellName;

    protected Spell(int duration, String spellName, int manaCost) {
        this.duration = duration;
        this.spellName = spellName;
        this.manaCost = manaCost;
    }

    public String getSpellName() {
        return spellName;
    }

    public int getManaCost(){
        return manaCost;
    }

    public abstract String aktivateOnEnemy(Actor actor);

    public abstract String computeEffectOnEnemy(Actor actor);

    public abstract String deaktivateOnEnemy(Actor actor);

    public abstract String aktivateOnFriend(Actor actor);

    public abstract String computeEffectOnFriend(Actor actor);

    public abstract String deaktivateOnFriend(Actor actor);

    public int getDuration() {
        return duration;
    }
}
