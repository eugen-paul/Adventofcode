package net.eugenpaul.adventofcode.y2020.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private int product;
    @Getter
    private int product2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2020/day1/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Integer> intData = eventData.stream()//
                .mapToInt(Integer::parseInt)//
                .sorted()//
                .boxed()//
                .collect(Collectors.toList());

        product = getProduct(intData, 2020);

        for (Integer integer : intData) {
            List<Integer> tempData = new ArrayList<>(intData);
            tempData.remove(integer);
            int tmpProduct = getProduct(tempData, 2020-integer);
            if(tmpProduct != -1){
                product2 = integer * tmpProduct;
                break;
            }
        }

        logger.log(Level.INFO, () -> "product   : " + getProduct());
        logger.log(Level.INFO, () -> "product2  : " + getProduct2());

        return true;
    }

    private Integer getProduct(List<Integer> intData, int sumToFind) {
        int min = 0;
        int max = intData.size() - 1;
        int sum = intData.get(min) + intData.get(max);

        while (sum != sumToFind && min < max) {
            if (sum < sumToFind) {
                min++;
            } else {
                max--;
            }
            sum = intData.get(min) + intData.get(max);
        }

        if (min >= max) {
            return -1;
        }

        return intData.get(min) * intData.get(max);
    }

}
