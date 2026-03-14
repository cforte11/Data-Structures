/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Queue
 * Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a singly linked list for O(1) enqueue and dequeue.
 */

public class Queue<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; this.next = null; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;

    // Initializes an empty queue.
    // Input:  none
    // Output: void
    public Queue() { head = null; tail = null; length = 0; }

    // Adds a new value to the back of the queue.
    // Input:  value (T)
    // Output: void
    public void enqueue(T value) {
        Node<T> node = new Node<>(value);
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = tail;
        length++;
    }

    // Removes and returns the front element.
    // Input:  none
    // Output: T
    public T dequeue() {
        if (head == null) throw new java.util.NoSuchElementException("Queue is empty");
        T val = head.data;
        head = head.next;
        if (head == null) tail = null;
        length--;
        return val;
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    public T front() {
        if (head == null) throw new java.util.NoSuchElementException("Queue is empty");
        return head.data;
    }

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    public T back() {
        if (tail == null) throw new java.util.NoSuchElementException("Queue is empty");
        return tail.data;
    }

    // Removes all elements from the queue.
    // Input:  none
    // Output: void
    public void clear() { head = null; tail = null; length = 0; }

    // Returns the number of elements in the queue.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the queue holds no elements.
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
