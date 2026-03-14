/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Positional List
 * Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PositionalList<T> implements Iterable<T> {

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;
        PositionalList<T> container;

        Node(T data, Node<T> prev, Node<T> next, PositionalList<T> container) {
            this.data = data;
            this.prev = prev;
            this.next = next;
            this.container = container;
        }
    }

    // A lightweight handle wrapping an internal node reference.
    public class Position {
        private final Node<T> node;

        // Wraps a node reference belonging to this list.
        // Input:  node (Node<T>)
        // Output: void
        private Position(Node<T> node) { this.node = node; }

        // Returns the element stored at this position.
        // Input:  none
        // Output: T
        public T element() { return node.data; }

        // Returns true if this position refers to the same node as another.
        // Input:  other (Position)
        // Output: boolean
        public boolean equals(Position other) { return this.node == other.node; }
    }

    private final Node<T> header;
    private final Node<T> trailer;
    private int length;

    // Constructs the list with sentinel header and trailer nodes.
    // Input:  none
    // Output: void
    public PositionalList() {
        header  = new Node<>(null, null, null, this);
        trailer = new Node<>(null, null, null, this);
        header.next  = trailer;
        trailer.prev = header;
        length = 0;
    }

    // Validates a position belongs to this list and has not been deleted.
    // Input:  p (Position)
    // Output: Node<T>
    private Node<T> validate(Position p) {
        if (p.node.container != this) throw new IllegalArgumentException("Position does not belong to this list");
        if (p.node.next == null) throw new IllegalStateException("Position is no longer valid");
        return p.node;
    }

    // Returns a Position for a node, or null if it is a sentinel.
    // Input:  node (Node<T>)
    // Output: Position | null
    private Position makePosition(Node<T> node) {
        if (node == header || node == trailer) return null;
        return new Position(node);
    }

    // Creates a new node between two existing nodes and returns its position.
    // Input:  value (T), before (Node<T>), after (Node<T>)
    // Output: Position
    private Position insertBetween(T value, Node<T> before, Node<T> after) {
        Node<T> node = new Node<>(value, before, after, this);
        before.next = node;
        after.prev  = node;
        length++;
        return new Position(node);
    }

    // Returns the position of the first element, or null if empty.
    // Input:  none
    // Output: Position | null
    public Position first() { return makePosition(header.next); }

    // Returns the position of the last element, or null if empty.
    // Input:  none
    // Output: Position | null
    public Position last() { return makePosition(trailer.prev); }

    // Returns the position immediately before the given position.
    // Input:  p (Position)
    // Output: Position | null
    public Position before(Position p) { return makePosition(validate(p).prev); }

    // Returns the position immediately after the given position.
    // Input:  p (Position)
    // Output: Position | null
    public Position after(Position p) { return makePosition(validate(p).next); }

    // Inserts a new element at the front and returns its position.
    // Input:  value (T)
    // Output: Position
    public Position addFirst(T value) { return insertBetween(value, header, header.next); }

    // Inserts a new element at the back and returns its position.
    // Input:  value (T)
    // Output: Position
    public Position addLast(T value) { return insertBetween(value, trailer.prev, trailer); }

    // Inserts a new element immediately before the given position.
    // Input:  p (Position), value (T)
    // Output: Position
    public Position addBefore(Position p, T value) {
        Node<T> node = validate(p);
        return insertBetween(value, node.prev, node);
    }

    // Inserts a new element immediately after the given position.
    // Input:  p (Position), value (T)
    // Output: Position
    public Position addAfter(Position p, T value) {
        Node<T> node = validate(p);
        return insertBetween(value, node, node.next);
    }

    // Removes the element at the given position and returns its value.
    // Input:  p (Position)
    // Output: T
    public T delete(Position p) {
        Node<T> node = validate(p);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        length--;
        T val = node.data;
        node.prev = node.next = null;
        node.container = null;
        node.data = null;
        return val;
    }

    // Replaces the element at the given position and returns the old value.
    // Input:  p (Position), value (T)
    // Output: T
    public T replace(Position p, T value) {
        Node<T> node = validate(p);
        T old = node.data;
        node.data = value;
        return old;
    }

    // Removes all non-sentinel nodes and resets length to zero.
    // Input:  none
    // Output: void
    public void clear() {
        header.next  = trailer;
        trailer.prev = header;
        length = 0;
    }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns an iterator that yields each element value from first to last.
    // Input:  none
    // Output: Iterator<T>
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Position cursor = first();
            public boolean hasNext() { return cursor != null; }
            public T next() {
                if (cursor == null) throw new NoSuchElementException();
                T val = cursor.element();
                cursor = after(cursor);
                return val;
            }
        };
    }

    // Returns a string of all element values from first to last.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        Position curr = first();
        while (curr != null) {
            sb.append(curr.element());
            curr = after(curr);
            if (curr != null) sb.append(", ");
        }
        return sb.append(" ]").toString();
    }
}
