package net.eugenpaul.adventofcode.y2015.day21;

public class Player extends Actor {

    private Item weapon = null;
    private Item armor = null;
    private Item ring1 = null;
    private Item ring2 = null;

    public Player(int hitpoints) {
        super(hitpoints);
    }

    @Override
    public int getDamage() {
        int weaponDamage = (weapon != null) ? weapon.getDamage() : 0;
        int armorDamage = (armor != null) ? armor.getDamage() : 0;
        int ring1Damage = (ring1 != null) ? ring1.getDamage() : 0;
        int ring2Damage = (ring2 != null) ? ring2.getDamage() : 0;
        return weaponDamage + armorDamage + ring1Damage + ring2Damage;
    }

    @Override
    public int getArmor() {
        int weaponArmor = (weapon != null) ? weapon.getArmor() : 0;
        int armorArmor = (armor != null) ? armor.getArmor() : 0;
        int ring1Armor = (ring1 != null) ? ring1.getArmor() : 0;
        int ring2Armor = (ring2 != null) ? ring2.getArmor() : 0;
        return weaponArmor + armorArmor + ring1Armor + ring2Armor;
    }

    public void addItem(Item item) {
        if (null == item) {
            return;
        }
        switch (item.getType()) {
        case WEAPON:
            weapon = item;
            break;
        case ARMOR:
            armor = item;
            break;
        case RING:
            if (ring1 == null) {
                ring1 = item;
            } else {
                ring2 = item;
            }
            break;
        }
    }

    public void removeItem(Item item) {
        switch (item.getType()) {
        case WEAPON:
            weapon = null;
            break;
        case ARMOR:
            armor = null;
            break;
        case RING:
            if (ring1.equals(item)) {
                ring1 = null;
            } else if (ring2.equals(item)) {
                ring2 = null;
            }
            break;
        }
    }

    public int getCost() {
        int weaponCost = (weapon != null) ? weapon.getCost() : 0;
        int armorCost = (armor != null) ? armor.getCost() : 0;
        int ring1Cost = (ring1 != null) ? ring1.getCost() : 0;
        int ring2Cost = (ring2 != null) ? ring2.getCost() : 0;
        return weaponCost + armorCost + ring1Cost + ring2Cost;
    }

}
