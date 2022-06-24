package net.eugenpaul.adventofcode.y2019.day22;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.HashStorage;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private long puzzle1;
    @Getter
    private long puzzle2;

    @Setter
    private int cardCount = 10_007;

    private Stack stack;

    private class Stack {
        private LinkedList<Integer> cards = new LinkedList<>();

        private Stack(int cardCount) {
            for (int i = 0; i < cardCount; i++) {
                cards.add(i);
            }
        }

        public void doNewStack() {
            LinkedList<Integer> cardsTmp = new LinkedList<>();
            Iterator<Integer> rev = cards.descendingIterator();
            while (rev.hasNext()) {
                cardsTmp.add(rev.next());
            }
            cards = cardsTmp;
        }

        public void doCut(int n) {
            LinkedList<Integer> cardsTmp = new LinkedList<>();
            if (n > 0) {
                Iterator<Integer> rev = cards.iterator();
                for (int i = 0; i < n; i++) {
                    cardsTmp.add(rev.next());
                    rev.remove();
                }
                cards.addAll(cardsTmp);
            } else {
                Iterator<Integer> rev = cards.descendingIterator();
                for (int i = 0; i < (n * -1); i++) {
                    cardsTmp.addFirst(rev.next());
                    rev.remove();
                }
                cardsTmp.addAll(cards);
                cards = cardsTmp;
            }
        }

        public void doDealWithIncrement(int n) {
            Iterator<Integer> iterator = cards.iterator();

            int[] output = new int[cards.size()];

            int counter = 0;
            while (iterator.hasNext()) {
                output[(counter * n) % cards.size()] = iterator.next();
                counter++;
            }

            cards = Arrays.stream(output).boxed().collect(Collectors.toCollection(LinkedList::new));
        }

        public int getCard(int n) {
            return cards.get(n);
        }

        public int getPositionOfCard(int n) {
            int pos = 0;
            for (Integer integer : cards) {
                if (integer == n) {
                    return pos;
                }
                pos++;
            }
            return -1;
        }
    }

    private class StackSimple {
        private final long cardCount;
        private long cardToShuffle;

        private StackSimple(long cardCount, long cardToShuffle) {
            this.cardCount = cardCount;
            this.cardToShuffle = cardToShuffle;
        }

        public void doNewStack() {
            cardToShuffle = cardCount - cardToShuffle - 1;
        }

        public void doCut(int n) {
            cardToShuffle = cardToShuffle - n;
            if (cardToShuffle < 0) {
                cardToShuffle += cardCount;
            } else if (cardToShuffle >= cardCount) {
                cardToShuffle -= cardCount;
            }
        }

        public void doDealWithIncrement(int n) {
            cardToShuffle = (cardToShuffle * n) % cardCount;
        }

        public long getCard() {
            return cardToShuffle;
        }

    }

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2019/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        puzzle1 = doPuzzle1(eventData);
        // puzzle2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "puzzle1  : " + getPuzzle1());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        StackSimple stackSimple = new StackSimple(cardCount, 2019);
        return doOneShuffle(eventData, stackSimple);
    }

    // private long doPuzzle2(List<String> eventData) {
    //     long shuffles = 101741582076661L;

    //     StackSimple stackSimple = new StackSimple(119315717514047L, 2020);

    //     HashStorage st = new HashStorage();
    //     long step = 0;
    //     while (!st.add("" + doOneShuffle(eventData, stackSimple), step)) {
    //         step++;
    //         if (step % 500 == 0) {
    //             System.out.println(step);
    //         }
    //     }

    //     System.out.println(st.getHashOf(101741582076661L));
    //     return -1;
    // }

    public boolean testSlow(List<String> eventData) {
        stack = new Stack(cardCount);
        for (String data : eventData) {
            if (data.startsWith("cut ")) {
                stack.doCut(Integer.parseInt(data.substring(4)));
            } else if (data.startsWith("deal with increment ")) {
                stack.doDealWithIncrement(Integer.parseInt(data.substring(20)));
            } else if (data.startsWith("deal into new stack")) {
                stack.doNewStack();
            }
        }

        return true;
    }

    private long doOneShuffle(List<String> eventData, StackSimple stackSimple) {
        for (String data : eventData) {
            if (data.startsWith("cut ")) {
                stackSimple.doCut(Integer.parseInt(data.substring(4)));
            } else if (data.startsWith("deal with increment ")) {
                stackSimple.doDealWithIncrement(Integer.parseInt(data.substring(20)));
            } else if (data.startsWith("deal into new stack")) {
                stackSimple.doNewStack();
            }
        }

        return stackSimple.getCard();
    }

    public int getCart(int n) {
        return stack.getCard(n);
    }

    public int getPos(int n) {
        return stack.getPositionOfCard(n);
    }

}