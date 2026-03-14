/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circularly Linked List
 * Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.
 */

public class CircularlyLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; this.next = null; }
    }

    private Node<T> tail;
    private int length;

    // Initializes an empty circular list with no nodes.
    // Input:  none
    // Output: void
    public CircularlyLinkedList() { tail = null; length = 0; }

    // Inserts a new value immediately after the tail, making it the new head.
    // Input:  value (T)
    // Output: void
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            node.next = node;
            tail = node;
        } else {
            node.next = tail.next;
            tail.next = node;
        }
        length++;
    }

    // Appends a new value as the new tail of the circular list.
    // Input:  value (T)
    // Output: void
    public void addLast(T value) {
        addFirst(value);
        tail = tail.next;
    }

    // Removes and returns the front (head) element.
    // Input:  none
    // Output: T
    public T removeFirst() {
        if (tail == null) throw new java.util.NoSuchElementException("List is empty");
        Node<T> head = tail.next;
        T val = head.data;
        if (tail == head) tail = null;
        else tail.next = head.next;
        length--;
        return val;
    }

    // Advances the tail pointer one step forward, rotating the front to the back.
    // Input:  none
    // Output: void
    public void rotate() { if (tail != null) tail = tail.next; }

    // Returns the head element without removing it.
    // Input:  none
    // Output: T
    public T first() {
        if (tail == null) throw new java.util.NoSuchElementException("List is empty");
        return tail.next.data;
    }

    // Returns the tail element without removing it.
    // Input:  none
    // Output: T
    public T last() {
        if (tail == null) throw new java.util.NoSuchElementException("List is empty");
        return tail.data;
    }

    // Returns true if the list contains the given value.
    // Input:  value (T)
    // Output: boolean
    public boolean contains(T value) {
        if (tail == null) return false;
        Node<T> curr = tail.next;
        do {
            if (curr.data != null && curr.data.equals(value)) return true;
            curr = curr.next;
        } while (curr != tail.next);
        return false;
    }

    // Resets the list to empty by discarding all node references.
    // Input:  none
    // Output: void
    public void clear() { tail = null; length = 0; }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns a string of all elements starting from the head, noting the circular wrap.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        if (tail == null) return "(empty)";
        StringBuilder sb = new StringBuilder();
        Node<T> curr = tail.next;
        do {
            sb.append(curr.data);
            if (curr.next != tail.next) sb.append(" -> ");
            curr = curr.next;
        } while (curr != tail.next);
        return sb.append(" -> (head)").toString();
    }
}
