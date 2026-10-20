/* QueueArray<Item>  (array-based, iterable)
 *
 * Methods
 * =======
 * boolean isEmpty()             : returns true if the queue is empty, false otherwise
 * int size()                    : returns the number of elements in the queue
 * void enqueue(Item item)       : adds item to the end of the queue
 * Item dequeue()                : removes the front-most item from the queue and returns it
 * Iterator<Item> iterator()     : returns a head-to-tail iterator for the queue
 */

import java.util.Iterator;
import java.util.Scanner;

public class QueueArray<Item> implements Iterable<Item>, Queue<Item>{

    private Item[] items;
    private int    head;
    private int    tail;
    private int    size;

    public QueueArray() {
        items = (Item[]) (new Object[10]);
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (size == items.length - 1) {
            resize(2*items.length);
        }

        items[tail++] = item;

        if (tail == items.length) {
            tail = 0;
        }

        size++;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Tried to dequeue an empty queue");
        }
        else {
            Item itemToReturn = items[head];
            items[head++] = null; //prevents loitering
            size--;
            if (head == items.length) {
                head = 0;
            }
            if (size == items.length / 4) {
                resize(2*items.length);
            }

            return itemToReturn;
        }
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; ++i) newArray[i] = items[(head+i)%items.length];
        items = newArray;
        head = 0;
        tail = size;
    }

    public Iterator<Item> iterator() {
        /**
         * Returns an iterator that traverses the queue from head to tail.
         * The iterator reflects the state of the queue at the time it is created;
         * modifications to the queue after the iterator is created may produce
         * unpredictable results.
         *
         * @return a head-to-tail Iterator over the elements of type Item
         */
        return (new Iterator<Item>() {

            private int pos = head;
            private int count = 0;

            /**
             * Returns true if the iteration has more elements.
             * In other words, returns true if a subsequent call to next()
             * would return an element rather than throwing an exception.
             *
             * @return true if there are more elements to iterate over, false otherwise
             */
            public boolean hasNext() {
                //TODO Implement the hasNext() method
                return pos < tail;
            }

            /**
             * Returns the next element in the iteration, advancing the iterator
             * one position toward the tail of the queue.
             *
             * @return the next Item in head-to-tail order
             * @throws java.util.NoSuchElementException if the iteration has no more elements
             */
            public Item next() {
                //TODO Implement the next() method
                Item item = items[0];
                if (hasNext()) {
                    item = items[++pos];
                    ++count;
                }
                return item;
            }
        });
    }
}
