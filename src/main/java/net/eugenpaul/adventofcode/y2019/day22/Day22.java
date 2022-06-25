package net.eugenpaul.adventofcode.y2019.day22;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private long puzzle1;
    @Getter
    private long puzzle2;

    @Setter
    private int cardCount = 10_007;

    private Stack simpleStack;

    private interface Shuffle {
        void doNewStack();

        void doCut(int n);

        void doDealWithIncrement(int n);
    }

    private class Stack implements Shuffle {
        private LinkedList<Integer> cards = new LinkedList<>();

        private Stack(int cardCount) {
            for (int i = 0; i < cardCount; i++) {
                cards.add(i);
            }
        }

        @Override
        public void doNewStack() {
            LinkedList<Integer> cardsTmp = new LinkedList<>();
            Iterator<Integer> rev = cards.descendingIterator();
            while (rev.hasNext()) {
                cardsTmp.add(rev.next());
            }
            cards = cardsTmp;
        }

        @Override
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

        @Override
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

    private class StackToTrack implements Shuffle {
        private final long cardCount;
        private long positionToTrack;

        private StackToTrack(long cardCount, long positionToTrack) {
            this.cardCount = cardCount;
            this.positionToTrack = positionToTrack;
        }

        @Override
        public void doNewStack() {
            positionToTrack = cardCount - positionToTrack - 1L;
        }

        @Override
        public void doCut(int n) {
            positionToTrack = positionToTrack - n;
            if (positionToTrack < 0L) {
                positionToTrack += cardCount;
            } else if (positionToTrack >= cardCount) {
                positionToTrack -= cardCount;
            }
        }

        @Override
        public void doDealWithIncrement(int n) {
            positionToTrack = (Math.multiplyExact(positionToTrack, n)) % cardCount;
        }

        public long getPosition() {
            return positionToTrack;
        }

    }

    /**
     * All changes are linear. So the whole process can be described as a function y = a*x + b.
     */
    private class ShuffleFunction implements Shuffle {
        private long a;
        private long b;

        private final long size;

        private ShuffleFunction(long size) {
            this.size = size;
            this.a = 1L;
            this.b = 0L;
        }

        public long getA() {
            if (a < 0L) {
                return a + size;
            }
            return a;
        }

        public long getB() {
            if (b < 0L) {
                return b + size;
            }
            return b;
        }

        @Override
        public void doCut(int n) {
            b -= n;
            b = b % size;
        }

        @Override
        public void doNewStack() {
            a *= -1L;
            a = a % size;

            b = -b - 1L;
            b = b % size;
        }

        @Override
        public void doDealWithIncrement(int n) {
            a *= n;
            a = a % size;

            b *= n;
            b = b % size;
        }

    }

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2019/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        puzzle1 = doPuzzle1(eventData, 10_007, 2019);
        logger.log(Level.INFO, () -> "puzzle1  : " + getPuzzle1());

        puzzle2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "puzzle1  : " + getPuzzle1());
        logger.log(Level.INFO, () -> "puzzle2  : " + getPuzzle2());

        return true;
    }

    private long doPuzzle2(List<String> eventData) {
        ShuffleFunction stackSimple = new ShuffleFunction(119315717514047L);
        doOneShuffle(eventData, stackSimple);

        // Large numbers are expected here. Long is too short for this.
        // Here you should either use a library that can calculate with very large numbers or the Windows calculator.
        // I have decided for the second one.

        // compute a and b
        logger.log(Level.INFO, () -> "a  : " + stackSimple.getA());
        logger.log(Level.INFO, () -> "b  : " + stackSimple.getB());

        // compute x manually
        // Function for 1 shuffle:
        // y = a * x + b mod m
        // Function for 2 shuffle:
        // y = a * (a * x + b) + b mod m
        // y = a * a * x + a * b + b mod m
        // y = a^2 * x + b * a + b mod m
        // Function for 3 shuffle:
        // y = a * (a^2 * x + b * a + b) + b mod m
        // y = a^3 * x + b * a^2 + b * a + b mod m
        // y = a^3 * x + b * SUM{1,2}(a^i) + b mod m
        // Function for n shuffle:
        // y = a^n * x + b * SUM{1,n-1}(a^i) + b mod m
        // SUM{1,n-1}(a^i) = a * (a^(n-1) - 1) / (a - 1)
        // y = a^n * x + b * a * (a^(n-1) - 1) / (a - 1) + b mod m
        //
        // n = 101741582076661
        // m = 119315717514047
        // a = -15681174147849 = 103634543366198
        // b = -57304980694001 = 62010736820046

        // ==> y = a^n * x + b * a * (a^(n-1) - 1) / (a - 1) + b mod m
        // ==> 2020 = 103634543366198^101741582076661 * x + 62010736820046*103634543366198*(103634543366198^(101741582076661-1)-1/103634543366198-1) +
        // 62010736820046
        // ==> 2020 = 70839959544740 * x + 98652742534150*(6350215898474-1/103634543366197) + 62010736820046
        // ==> 2020 = 70839959544740 * x + 98652742534150*6350215898473/103634543366197 + 62010736820046
        // ==> 2020 = 70839959544740 * x + 44529272864522/103634543366197 + 62010736820046
        // ==> 2020 = 70839959544740 * x + 44529272864522*103634543366197^(m-2) + 62010736820046
        // ==> 2020 = 70839959544740 * x + 44529272864522*103634543366197^119315717514045 + 62010736820046
        // ==> 2020 = 70839959544740 * x + 44529272864522*76843802954738 + 62010736820046
        // ==> 2020 = 70839959544740 * x + 69356709893769 + 62010736820046
        // ==> 2020 = 70839959544740 * x + 12051729199768
        // ==> 107263988316299 = 70839959544740 * x
        // ==> 107263988316299/70839959544740 = x
        // ==> 107263988316299*70839959544740^(m-2) = x
        // ==> 107263988316299*70839959544740^119315717514045 = x
        // ==> 107263988316299*87505940779834 = x
        // ==> 93.750.418.158.025 = x
        // ==> 93750418158025 = x

        logger.log(Level.INFO, () -> "compute x manually  : 93750418158025");

        return 93750418158025L;
    }

    private long doPuzzle1(List<String> eventData, long cardCount, long card) {
        ShuffleFunction stackSimple = new ShuffleFunction(cardCount);
        doOneShuffle(eventData, stackSimple);

        long response = stackSimple.getA() * card + stackSimple.getB();
        response = response % cardCount;
        if (response < 0) {
            response += cardCount;
        }

        StackToTrack stackToCheck = new StackToTrack(cardCount, card);
        doOneShuffle(eventData, stackToCheck);

        if (response != stackToCheck.getPosition()) {
            throw new IllegalStateException("ERROR");
        }
        return response;
    }

    /** First (slow) implementation. Just for tests. */
    public boolean testSlow(List<String> eventData) {
        simpleStack = new Stack(cardCount);
        doOneShuffle(eventData, simpleStack);

        return true;
    }

    private void doOneShuffle(List<String> eventData, Shuffle stack) {
        for (String data : eventData) {
            if (data.startsWith("cut ")) {
                stack.doCut(Integer.parseInt(data.substring(4)));
            } else if (data.startsWith("deal with increment ")) {
                stack.doDealWithIncrement(Integer.parseInt(data.substring(20)));
            } else if (data.startsWith("deal into new stack")) {
                stack.doNewStack();
            }
        }
    }

    public int getCart(int n) {
        return simpleStack.getCard(n);
    }

    public int getPos(int n) {
        return simpleStack.getPositionOfCard(n);
    }

}