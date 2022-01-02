package net.eugenpaul.adventofcode.y2015.day15;

public class Recipe {

    private int capacity;
    private int durability;
    private int flavor;
    private int texture;
    private int calories;

    public Recipe(int capacity, int durability, int flavor, int texture, int calories) {
        this.capacity = capacity;
        this.durability = durability;
        this.flavor = flavor;
        this.texture = texture;
        this.calories = calories;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getDurability() {
        return this.durability;
    }

    public int getFlavor() {
        return this.flavor;
    }

    public int getTexture() {
        return this.texture;
    }

    public int getCalories() {
        return this.calories;
    }

    public static Recipe fromString(String data) {
        // Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
        String dataWithOutComma = data.replace(",", "");
        String[] elements = dataWithOutComma.split(" ");
        return new Recipe(//
                Integer.parseInt(elements[2]), //
                Integer.parseInt(elements[4]), //
                Integer.parseInt(elements[6]), //
                Integer.parseInt(elements[8]), //
                Integer.parseInt(elements[10]) //
        );
    }
}
