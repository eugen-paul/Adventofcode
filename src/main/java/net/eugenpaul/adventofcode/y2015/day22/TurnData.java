package net.eugenpaul.adventofcode.y2015.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;
import net.eugenpaul.adventofcode.y2015.day22.spells.Spell;

public class TurnData {

    private static final String PLAYER_TURN = "-- Player turn --";
    private static final String BOSS_TURN = "-- BOSS turn --";
    private static final String EMPTY = "";

    private Actor player;
    private Actor boss;

    private List<Spell> allPlayerSpells;
    private List<Spell> avaiblePlayerSpells;
    private Map<Spell, Integer> aktivePlayerSpells;

    private List<String> gameHistory;

    private int totalManaCost;

    public TurnData(Actor player, Actor boss, List<Spell> avaiblePlayerSpells) {
        this.player = player;
        this.boss = boss;
        this.allPlayerSpells = new ArrayList<>(avaiblePlayerSpells);
        this.avaiblePlayerSpells = new ArrayList<>(avaiblePlayerSpells);
        this.aktivePlayerSpells = new HashMap<>();
        this.gameHistory = new LinkedList<>();
        this.totalManaCost = 0;
    }

    public TurnData(TurnData data) {
        player = new Actor(data.player);
        boss = new Actor(data.boss);
        allPlayerSpells = new ArrayList<>(data.allPlayerSpells);
        avaiblePlayerSpells = new ArrayList<>(data.avaiblePlayerSpells);
        aktivePlayerSpells = new HashMap<>(data.aktivePlayerSpells);
        gameHistory = new LinkedList<>(data.gameHistory);
        totalManaCost = data.totalManaCost;
    }

    public int getTotalManaCost() {
        return totalManaCost;
    }

    public List<String> getGameHistory() {
        return new LinkedList<>(gameHistory);
    }

    public void computeEffects(boolean isHard) {
        Iterator<Entry<Spell, Integer>> iterator = aktivePlayerSpells.entrySet().iterator();

        if (isHard) {
            player.setHitpoints(player.getHitpoints() - 1);
            gameHistory.add("Hard mode: " + player.getName() + " lose 1 HP");
        }

        while (iterator.hasNext()) {
            Entry<Spell, Integer> spell = iterator.next();

            spell.setValue(spell.getValue() - 1);

            String log = spell.getKey().computeEffectOnFriend(player);
            logSpell(log);
            log = spell.getKey().computeEffectOnEnemy(boss);
            logSpell(log);

            if (spell.getValue() <= 0) {
                log = spell.getKey().deaktivateOnEnemy(boss);
                logSpell(log);
                log = spell.getKey().deaktivateOnFriend(player);
                logSpell(log);
                iterator.remove();
                avaiblePlayerSpells.add(spell.getKey());
            }
        }
    }

    private void logSpell(String log) {
        if (null != log) {
            gameHistory.add(log);
        }
    }

    public boolean isPlayerWin() {
        return boss.getHitpoints() <= 0;
    }

    public boolean isBossWin() {
        return player.getHitpoints() <= 0;
    }

    public boolean doPlayerTurn(Spell spell, boolean isHard) {
        this.gameHistory.add(PLAYER_TURN);
        addActorStatsToGameHistory(player);
        addActorStatsToGameHistory(boss);
        computeEffects(isHard);
        if (isPlayerWin() || isBossWin()) {
            return true;
        }

        if (!playerCasts(spell)) {
            return true;
        }
        this.gameHistory.add(EMPTY);
        return isPlayerWin() || isBossWin();
    }

    public boolean doBossTurn() {
        this.gameHistory.add(BOSS_TURN);
        addActorStatsToGameHistory(player);
        addActorStatsToGameHistory(boss);
        computeEffects(false);
        if (isPlayerWin() || isBossWin()) {
            return true;
        }

        bossDoDmg();
        this.gameHistory.add(EMPTY);
        return isPlayerWin() || isBossWin();
    }

    private void addActorStatsToGameHistory(Actor actor) {
        StringBuilder actorData = new StringBuilder();
        actorData.append("- ");
        actorData.append(actor.getName());
        actorData.append(" has ");
        actorData.append(actor.getHitpoints());
        actorData.append(" hit points, ");
        actorData.append(actor.getArmor());
        actorData.append(" armor, ");
        actorData.append(actor.getMana());
        actorData.append(" mana");
        this.gameHistory.add(actorData.toString());
    }

    /**
     * @return false if der Player cann't use the spell
     */
    private boolean playerCasts(Spell spell) {
        if (!avaiblePlayerSpells.contains(spell) || player.getMana() < spell.getManaCost()) {
            return false;
        }
        player.setMana(player.getMana() - spell.getManaCost());

        totalManaCost += spell.getManaCost();

        avaiblePlayerSpells.remove(spell);
        aktivePlayerSpells.put(spell, spell.getDuration());

        String log = spell.aktivateOnFriend(player);
        if (log != null) {
            this.gameHistory.add(player.getName() + " " + log);
        }
        log = spell.aktivateOnEnemy(boss);
        if (log != null) {
            this.gameHistory.add(player.getName() + " " + log);
        }

        return true;
    }

    public void bossDoDmg() {
        int dmg = boss.getDamage() - player.getArmor();
        if (dmg < 1) {
            dmg = 1;
            this.gameHistory.add(boss.getName() + " attacks for 8-7=1 damage.");
        } else {
            this.gameHistory.add(boss.getName() + " attacks for 8 damage.");
        }
        player.setHitpoints(player.getHitpoints() - dmg);

    }

    public List<Spell> getPlayerSpells() {
        return new LinkedList<>(allPlayerSpells);
    }

}
