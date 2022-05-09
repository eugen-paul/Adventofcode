package net.eugenpaul.adventofcode.y2015.day22;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;
import net.eugenpaul.adventofcode.y2015.day22.spells.Drain;
import net.eugenpaul.adventofcode.y2015.day22.spells.MagicMissle;
import net.eugenpaul.adventofcode.y2015.day22.spells.Poison;
import net.eugenpaul.adventofcode.y2015.day22.spells.Recharge;
import net.eugenpaul.adventofcode.y2015.day22.spells.Shield;
import net.eugenpaul.adventofcode.y2015.day22.spells.Spell;

public class Day22 extends SolutionTemplate {

    private int lastGameManaCost;
    private List<String> lastGameData = null;

    @Getter
    private int easyGameLeastAmountOfMana;
    @Getter
    private int hardGameLeastAmountOfMana;

    @Setter
    private Actor player = new Actor("Player", 50, 0, 0, 500);

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2015/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        Actor boss = Actor.initBoss(eventData);

        List<Spell> avaiblePlayerSpells = List.of(//
                new Drain(), //
                new MagicMissle(), //
                new Poison(), //
                new Recharge(), //
                new Shield()//
        );

        doGame(player, boss, avaiblePlayerSpells, false);

        doGame(player, boss, avaiblePlayerSpells, true);

        logger.log(Level.INFO, () -> "easyGameLeastAmountOfMana: " + getEasyGameLeastAmountOfMana());
        logger.log(Level.INFO, () -> "hardGameLeastAmountOfMana: " + getHardGameLeastAmountOfMana());

        return lastGameManaCost != Integer.MAX_VALUE;
    }

    private void doGame(Actor player, Actor boss, List<Spell> avaiblePlayerSpells, boolean isHard) {
        if (!isHard) {
            logger.log(Level.INFO, () -> "Start new game. Difficulty: easy.");
        } else {
            logger.log(Level.INFO, () -> "Start new game. Difficulty: hard.");
        }

        TurnsStorage tStorage = new TurnsStorage();
        TurnData initTurn = new TurnData(player, boss, avaiblePlayerSpells);
        tStorage.addTurnData(initTurn);

        lastGameManaCost = Integer.MAX_VALUE;
        doPuzzle(tStorage, isHard);

        if (!isHard) {
            easyGameLeastAmountOfMana = lastGameManaCost;
        } else {
            hardGameLeastAmountOfMana = lastGameManaCost;
        }

        if (lastGameManaCost != Integer.MAX_VALUE) {
            printGameHistory();
            logger.log(Level.INFO, () -> "Game over. Player win!");
        } else {
            logger.log(Level.INFO, () -> "Game over. Unpossible to win.");
        }
    }

    private void printGameHistory() {
        lastGameData.stream().forEach(v -> logger.info(v));
    }

    private void doPuzzle(TurnsStorage tStorage, boolean isHard) {
        TurnData lowestTurn = tStorage.getLowestTurn();
        while (lowestTurn != null) {
            // get all player spells
            List<Spell> avaibleSpells = lowestTurn.getPlayerSpells();

            for (Spell spell : avaibleSpells) {
                // check if the player can cast the spell
                TurnData turn = new TurnData(lowestTurn);
                if (!doNextTurn(turn, spell, isHard)) {
                    // player can cast the spell. Add new turn to storage
                    tStorage.addTurnData(turn);
                }
            }

            lowestTurn = tStorage.getLowestTurn();
        }
    }

    /**
     * @return true if the game is over or mana cost are to high
     */
    private boolean doNextTurn(TurnData turn, Spell spell, boolean isHard) {
        if (turn.doPlayerTurn(spell, isHard) || turn.doBossTurn()) {
            checkGameOver(turn);
            return true;
        }

        return turn.getTotalManaCost() > lastGameManaCost;
    }

    private void checkGameOver(TurnData turn) {
        if (turn.isPlayerWin() //
                && lastGameManaCost > turn.getTotalManaCost()) {
            lastGameManaCost = turn.getTotalManaCost();
            lastGameData = turn.getGameHistory();
        }
    }

}
