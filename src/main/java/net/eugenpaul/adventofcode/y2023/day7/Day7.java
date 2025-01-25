package net.eugenpaul.adventofcode.y2023.day7;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @AllArgsConstructor
    private enum Rank {
        HIGH_CARD(1), PAIR(2), TWO_PAIRS(3), THREE_OF_A_KIND(4), FULL_HOUSE(5), FOUR_OF_A_KIND(6), FIVE_OF_A_KIND(7);

        @Getter
        private final int value;

        public static Rank getRank(Hand hand) {
            Map<Character, Integer> cardCount = new HashMap<>();
            for (char card : hand.cards.toCharArray()) {
                cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
            }
            return switch (cardCount.size()) {
            case 5 -> Rank.HIGH_CARD;
            case 4 -> Rank.PAIR;
            case 3 -> cardCount.values().stream().anyMatch(v -> v == 3) ? Rank.THREE_OF_A_KIND : Rank.TWO_PAIRS;
            case 2 -> cardCount.values().stream().anyMatch(v -> v == 4) ? Rank.FOUR_OF_A_KIND : Rank.FULL_HOUSE;
            case 1 -> Rank.FIVE_OF_A_KIND;
            default -> throw new IllegalArgumentException(hand.cards);
            };
        }
    }

    @AllArgsConstructor
    private enum Rank2 {
        HIGH_CARD(1), PAIR(2), TWO_PAIRS(3), THREE_OF_A_KIND(4), FULL_HOUSE(5), FOUR_OF_A_KIND(6), FIVE_OF_A_KIND(7);

        @Getter
        private final int value;

        public static Rank2 getRank(Hand hand) {
            Map<Character, Integer> cardCount = new HashMap<>();
            for (char card : hand.cards.toCharArray()) {
                cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
            }
            if (cardCount.containsKey('J') && cardCount.get('J') != 5) {
                int count = cardCount.get('J');
                cardCount.remove('J');
                char maxChar = cardCount.entrySet().stream().max((e1, e2) -> e1.getValue() - e2.getValue()).orElseThrow().getKey();
                cardCount.put(maxChar, cardCount.getOrDefault(maxChar, 0) + count);
            }
            return switch (cardCount.size()) {
            case 5 -> Rank2.HIGH_CARD;
            case 4 -> Rank2.PAIR;
            case 3 -> cardCount.values().stream().anyMatch(v -> v == 3) ? Rank2.THREE_OF_A_KIND : Rank2.TWO_PAIRS;
            case 2 -> cardCount.values().stream().anyMatch(v -> v == 4) ? Rank2.FOUR_OF_A_KIND : Rank2.FULL_HOUSE;
            case 1 -> Rank2.FIVE_OF_A_KIND;
            default -> throw new IllegalArgumentException(hand.cards);
            };
        }
    }

    @AllArgsConstructor
    private enum Card {
        TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'), JACK('J'), QUEEN('Q'), KING('K'), ACE('A');

        @Getter
        private final char value;

        public static Card getCard(char card) {
            return Stream.of(Card.values()).filter(c -> c.getValue() == card).findFirst().orElseThrow();
        }
    }

    @AllArgsConstructor
    private enum Card2 {
        JACK('J'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'), QUEEN('Q'), KING('K'), ACE('A');

        @Getter
        private final char value;

        public static Card2 getCard(char card) {
            return Stream.of(Card2.values()).filter(c -> c.getValue() == card).findFirst().orElseThrow();
        }
    }

    private static class HandComparator implements Comparator<Hand> {
        @Override
        public int compare(Hand a, Hand b) {
            Rank rank = Rank.getRank(a);
            Rank rank2 = Rank.getRank(b);
            if (rank.getValue() != rank2.getValue()) {
                return Integer.compare(rank.getValue(), rank2.getValue());
            }

            for (int i = 0; i < a.cards.length(); i++) {
                if (a.cards.charAt(i) != b.cards.charAt(i)) {
                    return Integer.compare(Card.getCard(a.cards.charAt(i)).ordinal(), Card.getCard(b.cards.charAt(i)).ordinal());
                }
            }
            return 0;
        }
    }

    private static class HandComparator2 implements Comparator<Hand> {
        @Override
        public int compare(Hand a, Hand b) {
            Rank2 rank = Rank2.getRank(a);
            Rank2 rank2 = Rank2.getRank(b);
            if (rank.getValue() != rank2.getValue()) {
                return Integer.compare(rank.getValue(), rank2.getValue());
            }

            for (int i = 0; i < a.cards.length(); i++) {
                if (a.cards.charAt(i) != b.cards.charAt(i)) {
                    return Integer.compare(Card2.getCard(a.cards.charAt(i)).ordinal(), Card2.getCard(b.cards.charAt(i)).ordinal());
                }
            }
            return 0;
        }
    }

    private record Hand(String cards, int bid) {
        public Hand(String data) {
            this(data.split(" ")[0], Integer.parseInt(data.split(" ")[1].trim()));
        }
    }

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2023/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Hand> hands = eventData.stream().map(Hand::new).sorted(new HandComparator()).toList();
        for (int i = 0; i < hands.size(); i++) {
            response += hands.get(i).bid * (i + 1);
        }
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Hand> hands = eventData.stream().map(Hand::new).sorted(new HandComparator2()).toList();
        for (int i = 0; i < hands.size(); i++) {
            response += hands.get(i).bid * (i + 1);
        }
        return response;
    }
}
