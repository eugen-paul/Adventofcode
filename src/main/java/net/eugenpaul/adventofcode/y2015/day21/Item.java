package net.eugenpaul.adventofcode.y2015.day21;

public class Item {

    private ItemType type;
    private String name;
    private int cost;
    private int damage;
    private int armor;

    public Item(ItemType type, String name, int cost, int damage, int armor) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.armor = armor;
    }

    public ItemType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getArmor() {
        return this.armor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        Item b = (Item) obj;
        return this.type == b.type //
                && this.name.equals(b.name) //
                && this.cost == b.cost //
                && this.damage == b.damage //
                && this.armor == b.armor //
        ;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("Name: ").append(name);
        response.append(" Cost: ").append(cost);
        response.append(" Dmg: ").append(damage);
        response.append(" Armor: ").append(armor);
        return response.toString();
    }
}
