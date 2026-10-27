import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class War {

    // ADD ADDITIONAL CONSTANTS HERE, IF DESIRED...

    static Pile pile1;
    static Pile pile2;
    static int numTurns;

    // ADD ADDITIONAL VARIABLES HERE, IF DESIRED...

    public static void printPiles() {

        // For your convenience, this prints the cards in
        // pile1 and pile2 along with the turn number, as specified
        // by numTurns

        System.out.println("Turn: " + numTurns + System.lineSeparator());
        System.out.println("player1:");
        System.out.println(pile1.toString() + System.lineSeparator());
        System.out.println("player2:");
        System.out.println(pile2.toString());
        System.out.println("--------------------------------------");
    }

    public static void printWar() {

        // Call this method every time there is a tie in the ranks
        // of cards compared

        System.out.println("                                        *** WAR! ***" + System.lineSeparator());
    }


    // ADD ADDITIONAL METHODS HERE, IF DESIRED...
    public int iter() {
        if (pile1.isEmpty()) return 2;
        if (pile2.isEmpty()) return 1;

        QueueArray<Card> warPile = new QueueArray<>();
        Card c1 = pile1.dequeue(), c2 = pile2.dequeue();
        warPile.enqueue(c1); warPile.enqueue(c2);

        while (true) {
            int res = c1.compareTo(c2);
            if (res > 0) {
                while (!warPile.isEmpty()) {
                    pile1.enqueue(warPile.dequeue());
                }
                return -1;
            }
            if (res < 0) {
                while (!warPile.isEmpty()) {
                    pile2.enqueue(warPile.dequeue());
                }
                return -1;
            }

            printWar();
            
            if (pile1.size() < 4 && pile2.size() < 4) return 0;
            if (pile1.size() < 4) return 2;
            else if (pile2.size() < 4) return 1;

            for (int i = 0; i < 3; ++i) {
                warPile.enqueue(pile1.dequeue());
                warPile.enqueue(pile2.dequeue());
            }

            c1 = pile1.dequeue(); c2 = pile2.dequeue();
            warPile.enqueue(c1); warPile.enqueue(c2);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java War <deck-file>");
            return;
        }

        // args[0] is the deck filename.
        try {
            
            Scanner scanner = new Scanner(new File(args[0]));
            System.out.println("Loading deck from: " + args[0]);
            pile1 = new Pile(scanner.nextLine());
            pile2 = new Pile(scanner.nextLine());



            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + args[0]);
            return;
        }

        numTurns = 1;

        War war = new War();
        printPiles();
        int res = war.iter();
        while (res < 0) {
            numTurns++;
            if (numTurns > 5000) {
                res = 0;
                break;
            }
            printPiles();
            res = war.iter();
        }
        
        if (res == 0) {
            System.out.println("draw!");
        }
        else if (res == 2) {
            System.out.println("player 2 wins!");
        }
        else {
            System.out.println("player 1 wins!");
        }
    }
}
