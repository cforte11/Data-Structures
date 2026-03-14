/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Deque (Double-Ended Queue)
 * Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back via a doubly linked list.
 */

public class Deque<T> {

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;
        Node(T data) { this.data = data; this.prev = null; this.next = null; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;

    // Initializes an empty deque.
    // Input:  none
    // Output: void
    public Deque() { head = null; tail = null; length = 0; }

    // Inserts a new value at the front of the deque.
    // Input:  value (T)
    // Output: void
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = head;
        length++;
    }

    // Appends a new value at the back of the deque.
    // Input:  value (T)
    // Output: void
    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        node.prev = tail;
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = tail;
        length++;
    }

    // Removes and returns the front element.
    // Input:  none
    // Output: T
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException("Deque is empty");
        T val = head.data;
        head = head.next;
        if (head != null) head.prev = null; else tail = null;
        length--;
        return val;
    }

    // Removes and returns the back element.
    // Input:  none
    // Output: T
    public T removeLast() {
        if (tail == null) throw new java.util.NoSuchElementException("Deque is empty");
        T val = tail.data;
        tail = tail.prev;
        if (tail != null) tail.next = null; else head = null;
        length--;
        return val;
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    public T first() {
        if (head == null) throw new java.util.NoSuchElementException("Deque is empty");
        return head.data;
    }

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    public T last() {
        if (tail == null) throw new java.util.NoSuchElementException("Deque is empty");
        return tail.data;
    }

    // Removes all nodes and resets the deque to empty.
    // Input:  none
    // Output: void
    public void clear() { head = null; tail = null; length = 0; }

    // Returns the number of elements in the deque.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the deque holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns a string of elements from front to back.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("front [ ");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) sb.append(", ");
            curr = curr.next;
        }
        return sb.append(" ] back").toString();
    }
}
