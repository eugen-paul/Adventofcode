package net.eugenpaul.adventofcode.y2020.day22;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private long score;
    @Getter
    private long score2;

    private enum WINNER {
        FIRST, SECOND
    }

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2020/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        LinkedList<Integer> deck1 = new LinkedList<>();
        LinkedList<Integer> deck2 = new LinkedList<>();
        LinkedList<Integer> currentDeck = deck1;

        for (var data : eventData) {
            if (data.isEmpty()) {
                currentDeck = deck2;
            } else if (data.charAt(0) != 'P') {
                currentDeck.addLast(Integer.parseInt(data));
            }
        }

        score = doPuzzle1(new LinkedList<>(deck1), new LinkedList<>(deck2));
        score2 = doPuzzle2(new LinkedList<>(deck1), new LinkedList<>(deck2));

        logger.log(Level.INFO, () -> "score  : " + getScore());
        logger.log(Level.INFO, () -> "score2 : " + getScore2());

        return true;
    }

    private Long doPuzzle1(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {

        while (!deck1.isEmpty() && !deck2.isEmpty()) {
            Integer card1 = deck1.removeFirst();
            Integer card2 = deck2.removeFirst();
            if (card1 > card2) {
                deck1.addLast(card1);
                deck1.addLast(card2);
            } else {
                deck2.addLast(card2);
                deck2.addLast(card1);
            }
        }

        LinkedList<Integer> winnerDeck = (deck1.isEmpty()) ? deck2 : deck1;

        int i = winnerDeck.size();
        long response = 0;
        for (Integer card : winnerDeck) {
            response += i * card;
            i--;
        }

        return response;
    }

    private Long doPuzzle2(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {
        doSubGame(deck1, deck2);

        LinkedList<Integer> winnerDeck = (deck1.isEmpty()) ? deck2 : deck1;

        int i = winnerDeck.size();
        long response = 0;
        for (Integer card : winnerDeck) {
            response += i * card;
            i--;
        }

        return response;
    }

    private WINNER doSubGame(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {
        Set<String> gameHash = new HashSet<>();
        while (!deck1.isEmpty() && !deck2.isEmpty()) {

            String hash = genGameHash(deck1, deck2);
            if (gameHash.contains(hash)) {
                return WINNER.FIRST;
            }

            gameHash.add(hash);

            Integer card1 = deck1.removeFirst();
            Integer card2 = deck2.removeFirst();

            WINNER winner;
            if (card1 <= deck1.size() && card2 <= deck2.size()) {
                LinkedList<Integer> subDeck1 = getSubdeck(deck1, card1);
                LinkedList<Integer> subDeck2 = getSubdeck(deck2, card2);
                winner = doSubGame(subDeck1, subDeck2);
            } else {
                winner = (card1 > card2) ? WINNER.FIRST : WINNER.SECOND;
            }

            if (winner == WINNER.FIRST) {
                deck1.addLast(card1);
                deck1.addLast(card2);
            } else {
                deck2.addLast(card2);
                deck2.addLast(card1);
            }
        }
        return (deck1.isEmpty()) ? WINNER.SECOND : WINNER.FIRST;
    }

    private LinkedList<Integer> getSubdeck(LinkedList<Integer> deck, Integer card1) {
        return new LinkedList<>(deck.subList(0, card1));
    }

    private String genGameHash(LinkedList<Integer> deck1, LinkedList<Integer> deck2) {
        StringBuilder response = new StringBuilder(deck1.size() * 3 + deck2.size() * 3 + 3);
        for (var data : deck1) {
            response.append(data);
            response.append(".");
        }
        response.append(";");
        for (var data : deck2) {
            response.append(data);
            response.append(".");
        }
        return response.toString();
    }
}
