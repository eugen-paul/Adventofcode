package net.eugenpaul.adventofcode.y2015.day21;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day21 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day21.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day21.class.getName());

    private int leastAmountOfGold;
    private int mostAmountOfGold;

    public long getLeastAmountOfGold() {
        return leastAmountOfGold;
    }

    public long getMostAmountOfGold() {
        return mostAmountOfGold;
    }

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2015/day21/puzzle1.txt", "y2015/day21/puzzle1_shop.txt");
    }

    public boolean doPuzzleFromFile(String filename, String shopFilename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }
        List<String> shopData = FileReaderHelper.readListStringFromFile(shopFilename);
        if (null == shopData) {
            return false;
        }

        return doPuzzleFromData(eventData, shopData);
    }

    public boolean doPuzzleFromData(List<String> eventData, List<String> shopData) {
        if (!doEvent(eventData, shopData)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "leastAmountOfGold: " + getLeastAmountOfGold());
        logger.log(Level.INFO, () -> "mostAmountOfGold: " + getMostAmountOfGold());

        return true;
    }

    private boolean doEvent(List<String> eventData, List<String> shopData) {
        if (null == eventData) {
            return false;
        }

        Shop shop = Shop.fromList(shopData);
        Boss boss = Boss.fromList(eventData);

        leastAmountOfGold = Integer.MAX_VALUE;
        mostAmountOfGold = Integer.MIN_VALUE;

        doPuzzle1(shop, boss);

        return true;
    }

    private int doPuzzle1(Shop shop, Boss boss) {
        List<Item> weapons = shop.getItems(ItemType.WEAPON);
        List<Item> armors = shop.getItems(ItemType.ARMOR);
        List<Item> rings = shop.getItems(ItemType.RING);

        int currentMinCost = Integer.MAX_VALUE;

        for (Item weapon : weapons) {
            tryArmors(boss, armors, rings, weapon);
        }

        return currentMinCost;
    }

    private void tryArmors(Boss boss, List<Item> armors, List<Item> rings, Item weapon) {
        tryRings(boss, rings, weapon, null);
        for (Item armor : armors) {
            tryRings(boss, rings, weapon, armor);
        }
    }

    private void tryRings(Boss boss, List<Item> rings, Item weapon, Item armor) {
        doTurn(boss, weapon, armor, null, null);
        for (Item ring : rings) {
            doTurn(boss, weapon, armor, ring, null);
        }
        for (int i = 0; i < rings.size(); i++) {
            Item ring1 = rings.get(i);
            for (int j = i + 1; j < rings.size(); j++) {
                doTurn(boss, weapon, armor, ring1, rings.get(j));
            }
        }
    }

    private void doTurn(Boss boss, Item weapon, Item armor, Item ring1, Item ring2) {
        Player player = generatePlayer(weapon, armor, ring1, ring2);
        int playerCost = player.getCost();
        if (isPlayerWin(player, boss)) {
            if (leastAmountOfGold > playerCost) {
                leastAmountOfGold = playerCost;
            }
        } else {
            if (mostAmountOfGold < playerCost) {
                mostAmountOfGold = playerCost;
            }
        }
    }

    private Player generatePlayer(Item weapon, Item armor, Item ring1, Item ring2) {
        Player player = new Player(100);
        player.addItem(weapon);
        player.addItem(armor);
        player.addItem(ring1);
        player.addItem(ring2);
        return player;
    }

    private boolean isPlayerWin(Player player, Boss boss) {
        return player.surviveTurns(boss) >= boss.surviveTurns(player);
    }

}
