package net.eugenpaul.adventofcode.y2015.day21;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop {

    private static final String ITEM_PATTERN_STRING = "([a-zA-Z0-9 \\+]+) +([0-9]+) +([0-9]+) +([0-9]+)";
    private static final Pattern ITEM_PATTERN = Pattern.compile(ITEM_PATTERN_STRING);
    private static Logger logger = Logger.getLogger(Shop.class.getName());

    private Map<ItemType, List<Item>> allItems;

    private Shop() {
        allItems = new EnumMap<>(ItemType.class);
        for (ItemType type : ItemType.values()) {
            allItems.put(type, new ArrayList<>());
        }
    }

    private void addItem(Item item) {
        allItems.get(item.getType()).add(item);
    }

    public void printItems() {
        for (Entry<ItemType, List<Item>> element : allItems.entrySet()) {
            element.getValue().stream().forEach(v -> logger.info(v.toString()));
        }
    }

    public List<Item> getItems(ItemType type) {
        return new ArrayList<>(allItems.get(type));
    }

    public static Shop fromList(List<String> items) {
        Shop responseShop = new Shop();

        ItemType currentType = null;

        for (String data : items) {
            if (!data.isBlank()) {
                ItemType typ = readItemType(data);
                if (null != typ) {
                    currentType = typ;
                    continue;
                }

                Item item = readItem(currentType, data);
                if (item == null) {
                    return null;
                }

                responseShop.addItem(item);
            }
        }

        return responseShop;
    }

    private static ItemType readItemType(String data) {
        if (data.startsWith("Weapons:")) {
            return ItemType.WEAPON;
        }
        if (data.startsWith("Armor:")) {
            return ItemType.ARMOR;
        }
        if (data.startsWith("Rings:")) {
            return ItemType.RING;
        }

        return null;
    }

    private static Item readItem(ItemType type, String data) {
        if (null == type) {
            return null;
        }

        Matcher matcher = ITEM_PATTERN.matcher(data);
        if (matcher.find()) {
            return new Item(//
                    type, //
                    matcher.group(1).trim(), //
                    Integer.parseInt(matcher.group(2)), //
                    Integer.parseInt(matcher.group(3)), //
                    Integer.parseInt(matcher.group(4)) //
            );
        }
        return null;
    }
}
