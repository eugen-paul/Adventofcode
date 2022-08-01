package net.eugenpaul.adventofcode.y2020.day21;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day21 extends SolutionTemplate {

    @AllArgsConstructor
    @Getter
    private class Food {
        Set<String> ingredients;
        Set<String> alergens;
    }

    @Getter
    private long ingredientsAppear;
    @Getter
    private String dangerousList;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2020/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Food> foods = eventData.stream().map(this::fromString).collect(Collectors.toList());

        Set<String> ingredients = new HashSet<>();
        for (var food : foods) {
            ingredients.addAll(food.getIngredients());
        }

        List<String> ingredientWithoutAllergen = ingredients.stream()//
                .filter(v -> !containIngredientAnyAllergen(foods, v))//
                .collect(Collectors.toList());

        ingredientsAppear = doPuzzle1(foods, ingredientWithoutAllergen);
        dangerousList = doPuzzle2(foods, ingredientWithoutAllergen);

        logger.log(Level.INFO, () -> "ingredientsAppear : " + getIngredientsAppear());
        logger.log(Level.INFO, () -> "dangerousList     : " + getDangerousList());

        return true;
    }

    private int doPuzzle1(List<Food> foods, List<String> ingredientWithoutAllergen) {
        int response = 0;

        for (var food : foods) {
            for (var ingr : ingredientWithoutAllergen) {
                if (food.getIngredients().contains(ingr)) {
                    response++;
                }
            }
        }

        return response;
    }

    private String doPuzzle2(List<Food> foods, List<String> ingredientWithoutAllergen) {
        LinkedList<String> alergens = getAlergensList(foods);

        Map<String, String> alergensMap = new HashMap<>();

        List<Food> foodsFiltered = getFoodsFiltered(foods, ingredientWithoutAllergen);

        while (!alergens.isEmpty()) {
            String checkAlergen = alergens.removeFirst();

            Set<String> possibleIngredients = getPossibleIngredients(foodsFiltered, checkAlergen);

            if (possibleIngredients.size() == 1) {
                String ingr = possibleIngredients.stream().findFirst().orElseThrow();
                alergensMap.put(checkAlergen, ingr);

                for (var food : foodsFiltered) {
                    food.getAlergens().remove(checkAlergen);
                    food.getIngredients().remove(ingr);
                }

            } else {
                alergens.addLast(checkAlergen);
            }
        }

        return alergensMap.entrySet().stream()//
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))//
                .map(Entry::getValue)//
                .reduce("", (a, b) -> a + "," + b)//
                .substring(1);
    }

    private Set<String> getPossibleIngredients(List<Food> foodsFiltered, String checkAlergen) {
        Set<String> possibleIngredients = new HashSet<>();
        for (var food : foodsFiltered) {
            if (food.getAlergens().contains(checkAlergen)) {
                possibleIngredients.addAll(food.getIngredients());
            }
        }

        for (var food : foodsFiltered) {
            if (food.getAlergens().contains(checkAlergen)) {
                possibleIngredients.removeIf(v -> !food.getIngredients().contains(v));
            }
        }
        return possibleIngredients;
    }

    private LinkedList<String> getAlergensList(List<Food> foods) {
        Set<String> alergensSet = new HashSet<>();
        for (var food : foods) {
            alergensSet.addAll(food.getAlergens());
        }

        LinkedList<String> alergens = new LinkedList<>(alergensSet);
        return alergens;
    }

    private List<Food> getFoodsFiltered(List<Food> foods, List<String> ingredientWithoutAllergen) {
        List<Food> response = new LinkedList<>();
        for (var food : foods) {
            Food f = new Food(new HashSet<>(food.getIngredients()), new HashSet<>(food.getAlergens()));
            for (var ingr : ingredientWithoutAllergen) {
                f.getIngredients().remove(ingr);
            }
            response.add(f);
        }
        return response;
    }

    private boolean containIngredientAnyAllergen(List<Food> foods, String ingredient) {
        Set<String> possibleList = new HashSet<>();
        for (var food : foods) {
            if (food.getIngredients().contains(ingredient)) {
                possibleList.addAll(food.getAlergens());
            }
        }

        for (var food : foods) {
            var posListIterator = possibleList.iterator();
            while (posListIterator.hasNext()) {
                var currentAllergen = posListIterator.next();
                if (!food.getIngredients().contains(ingredient) && food.getAlergens().contains(currentAllergen)) {
                    posListIterator.remove();
                }
            }
        }

        return !possibleList.isEmpty();
    }

    private Food fromString(String input) {
        return new Food(readIngredients(input), readAlergens(input));
    }

    private Set<String> readIngredients(String input) {
        String[] elements = input.split(" ");
        Set<String> response = new HashSet<>();
        for (String element : elements) {
            if (element.startsWith("(")) {
                break;
            }
            response.add(element);
        }
        return response;
    }

    private Set<String> readAlergens(String input) {
        int start = input.indexOf("(");
        String[] elements = input.substring(start + 10, input.length() - 1).split(",");
        Set<String> response = new HashSet<>();
        for (String element : elements) {
            response.add(element.trim());
        }
        return response;
    }

}
