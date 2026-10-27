import java.util.Scanner;
import java.util.Iterator;

public class Pile extends QueueArray<Card> {

    public Pile(String cards) {

        // Allows for the construction of a pile of cards from a string
        //
        // Example:
        //   Pile pile = new Pile("As 2s 3s Jh Qh Kh 4d 5d 6d 7c 8c 9c");
        Scanner larry = new Scanner(cards);
        while (larry.hasNext()) {
            String card = larry.next();
            this.enqueue(new Card(card.charAt(0), card.charAt(1)));
        }
        larry.close();
    }

    public String toString() {

        // Creates a string containing 10 cards per line, for as many lines
        // as necessary to show all cards in the pile
        //
        // Example:
        //   6h  3h  Jc  Ts  3c  7h  2h  9c  3s  7s
        //   8c  5d  Js  Jh  6d  Ah  Qh  9d  Ks  6c
        //   Kc  Td  Jc  Th  9s  Kd
        // TODO: implement toString. Use a  'for (Card card : this) {}' loop to print them
        String output = "";
        int count = 0;
        Iterator<Card> lori = this.iterator();
        while (lori.hasNext()) {
            ++count;
            output += lori.next().toString() + "  ";
            if (count == 10) {
                output += System.lineSeparator();
                count = 0;
            }
        }
        return output;

    }

    /*
     * A riffle shuffle cuts the pile into two halves and then interleaves
     * them by alternating cards from each half.
     *
     * Hint: Use two temporary Pile objects (or QueueArray<Card> objects) to
     * hold the two halves while you rebuild this pile.
     */
    public void shuffle() {
       int total = this.size();
       int half = (total + 1) / 2;

       QueueArray<Card> half1 = new QueueArray<>();
       QueueArray<Card> half2 = new QueueArray<>();

        for (int i = 0; i < half; ++i) {
            half1.enqueue(this.dequeue());
        }
        while (!this.isEmpty()) {
            half2.enqueue(this.dequeue());
        }
        while (!half1.isEmpty() && !half2.isEmpty()) {
            this.enqueue(half1.dequeue());
            this.enqueue(half2.dequeue());
        }
        while (!half1.isEmpty()) {
            this.enqueue(half1.dequeue());
        }
    }
}
