package net.eugenpaul.adventofcode.y2015.day22;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2015.day22.actors.Actor;
import net.eugenpaul.adventofcode.y2015.day22.spells.Drain;
import net.eugenpaul.adventofcode.y2015.day22.spells.MagicMissle;
import net.eugenpaul.adventofcode.y2015.day22.spells.Poison;
import net.eugenpaul.adventofcode.y2015.day22.spells.Recharge;
import net.eugenpaul.adventofcode.y2015.day22.spells.Shield;
import net.eugenpaul.adventofcode.y2015.day22.spells.Spell;

public class Day22 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day22.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day22.class.getName());

    private int lastGameManaCost;
    private List<String> lastGameData = null;

    private int easyGameLeastAmountOfMana;
    private int hardGameLeastAmountOfMana;

    public long getEasyGameLeastAmountOfMana() {
        return easyGameLeastAmountOfMana;
    }

    public long getHardGameLeastAmountOfMana() {
        return hardGameLeastAmountOfMana;
    }

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2015/day22/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(Actor.fromList(eventData), new Actor("Player", 50, 0, 0, 500))) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "easyGameLeastAmountOfMana: " + getEasyGameLeastAmountOfMana());
        logger.log(Level.INFO, () -> "hardGameLeastAmountOfMana: " + getHardGameLeastAmountOfMana());

        return true;
    }

    public boolean doEvent(Actor boss, Actor player) {
        List<Spell> avaiblePlayerSpells = List.of(//
                new Drain(), //
                new MagicMissle(), //
                new Poison(), //
                new Recharge(), //
                new Shield()//
        );

        doEasyGame(player, boss, avaiblePlayerSpells);

        doHardGame(player, boss, avaiblePlayerSpells);

        return lastGameManaCost != Integer.MAX_VALUE;
    }

    private void doEasyGame(Actor player, Actor boss, List<Spell> avaiblePlayerSpells) {
        TurnsStorage tStorage = new TurnsStorage();
        TurnData initTurn = new TurnData(player, boss, avaiblePlayerSpells);
        tStorage.addTurnData(initTurn);

        lastGameManaCost = Integer.MAX_VALUE;
        doPuzzle(tStorage, false);
        pringGameHistory();
        easyGameLeastAmountOfMana = lastGameManaCost;
    }

    private void doHardGame(Actor player, Actor boss, List<Spell> avaiblePlayerSpells) {
        TurnsStorage tStorage = new TurnsStorage();
        TurnData initTurn = new TurnData(player, boss, avaiblePlayerSpells);
        tStorage.addTurnData(initTurn);

        lastGameManaCost = Integer.MAX_VALUE;
        doPuzzle(tStorage, true);
        pringGameHistory();
        hardGameLeastAmountOfMana = lastGameManaCost;
    }

    private void pringGameHistory() {
        lastGameData.stream().forEach(v -> logger.info(v));
    }

    private void doPuzzle(TurnsStorage tStorage, boolean isHard) {
        TurnData lowestTurn = tStorage.getLowestTurn();
        while (lowestTurn != null) {
            List<Spell> avaibleSpells = lowestTurn.getPlayerSpells();

            for (Spell spell : avaibleSpells) {
                TurnData turn = new TurnData(lowestTurn);
                if (!doFullTurn(turn, spell, isHard)) {
                    tStorage.addTurnData(turn);
                }
            }

            lowestTurn = tStorage.getLowestTurn();
        }
    }

    /**
     * @return true ist the game is over or mana cost are to high
     */
    private boolean doFullTurn(TurnData turn, Spell spell, boolean isHard) {
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
