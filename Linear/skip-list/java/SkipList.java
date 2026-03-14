/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Skip List
 * Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.
 */

import java.util.Random;

public class SkipList<T extends Comparable<T>> {

    private static final int MAX_LEVEL = 16;
    private static final double PROB = 0.5;

    private static class Node<T> {
        T key;
        Node<T>[] forward;

        @SuppressWarnings("unchecked")
        Node(T key, int level) {
            this.key = key;
            this.forward = new Node[level + 1];
        }
    }

    private final Node<T> header;
    private int currentLevel;
    private int length;
    private final Random random = new Random();

    // Initializes the skip list with a sentinel header and level tracking.
    // Input:  none
    // Output: void
    public SkipList() {
        header = new Node<>(null, MAX_LEVEL - 1);
        currentLevel = 0;
        length = 0;
    }

    // Generates a random level for a new node using geometric distribution.
    // Input:  none
    // Output: int (level between 0 and MAX_LEVEL-1)
    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < PROB && level < MAX_LEVEL - 1) level++;
        return level;
    }

    // Searches for a key and returns true if it exists in the list.
    // Input:  key (T)
    // Output: boolean
    public boolean contains(T key) {
        Node<T> curr = header;
        for (int i = currentLevel; i >= 0; i--)
            while (curr.forward[i] != null && curr.forward[i].key.compareTo(key) < 0)
                curr = curr.forward[i];
        curr = curr.forward[0];
        return curr != null && curr.key.compareTo(key) == 0;
    }

    // Inserts a key into the skip list at the correct sorted position.
    // Input:  key (T)
    // Output: void
    @SuppressWarnings("unchecked")
    public void insert(T key) {
        Node<T>[] update = new Node[MAX_LEVEL];
        Node<T> curr = header;
        for (int i = currentLevel; i >= 0; i--) {
            while (curr.forward[i] != null && curr.forward[i].key.compareTo(key) < 0)
                curr = curr.forward[i];
            update[i] = curr;
        }
        curr = curr.forward[0];
        if (curr != null && curr.key.compareTo(key) == 0) return;
        int newLevel = randomLevel();
        if (newLevel > currentLevel) {
            for (int i = currentLevel + 1; i <= newLevel; i++) update[i] = header;
            currentLevel = newLevel;
        }
        Node<T> node = new Node<>(key, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            node.forward[i] = update[i].forward[i];
            update[i].forward[i] = node;
        }
        length++;
    }

    // Removes the node with the given key if it exists.
    // Input:  key (T)
    // Output: void
    @SuppressWarnings("unchecked")
    public void remove(T key) {
        Node<T>[] update = new Node[MAX_LEVEL];
        Node<T> curr = header;
        for (int i = currentLevel; i >= 0; i--) {
            while (curr.forward[i] != null && curr.forward[i].key.compareTo(key) < 0)
                curr = curr.forward[i];
            update[i] = curr;
        }
        curr = curr.forward[0];
        if (curr == null || curr.key.compareTo(key) != 0) return;
        for (int i = 0; i <= currentLevel; i++) {
            if (update[i].forward[i] != curr) break;
            update[i].forward[i] = curr.forward[i];
        }
        while (currentLevel > 0 && header.forward[currentLevel] == null) currentLevel--;
        length--;
    }

    // Removes all nodes and resets the list to its initial empty state.
    // Input:  none
    // Output: void
    public void clear() {
        java.util.Arrays.fill(header.forward, null);
        currentLevel = 0;
        length = 0;
    }

    // Returns the number of elements stored in the skip list.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the skip list holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns a string of all keys at level 0 in sorted order.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        Node<T> curr = header.forward[0];
        while (curr != null) {
            sb.append(curr.key);
            if (curr.forward[0] != null) sb.append(", ");
            curr = curr.forward[0];
        }
        return sb.append(" ]").toString();
    }
}
