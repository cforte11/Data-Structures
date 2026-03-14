/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Doubly Linked List
 * Description: Personal take on a bidirectionally linked linear structure enabling O(1) insertion and removal at both ends.
 */

public class DoublyLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;
        Node(T data) { this.data = data; this.prev = null; this.next = null; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;

    // Initializes an empty doubly linked list.
    // Input:  none
    // Output: void
    public DoublyLinkedList() { head = null; tail = null; length = 0; }

    // Stitches a new node between two existing adjacent nodes.
    // Input:  before (Node<T>), after (Node<T>), value (T)
    // Output: void
    private void insertBetween(Node<T> before, Node<T> after, T value) {
        Node<T> node = new Node<>(value);
        node.prev = before;
        node.next = after;
        if (before != null) before.next = node; else head = node;
        if (after != null) after.prev = node; else tail = node;
        length++;
    }

    // Detaches a node from its neighbors and returns its value.
    // Input:  node (Node<T>)
    // Output: T
    private T unlink(Node<T> node) {
        if (node.prev != null) node.prev.next = node.next; else head = node.next;
        if (node.next != null) node.next.prev = node.prev; else tail = node.prev;
        length--;
        return node.data;
    }

    // Prepends a new value before the current head.
    // Input:  value (T)
    // Output: void
    public void addFirst(T value) { insertBetween(null, head, value); }

    // Appends a new value after the current tail.
    // Input:  value (T)
    // Output: void
    public void addLast(T value) { insertBetween(tail, null, value); }

    // Inserts a new value at the specified zero-based index.
    // Input:  index (int), value (T)
    // Output: void
    public void addAt(int index, T value) {
        if (index < 0 || index > length) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) { addFirst(value); return; }
        if (index == length) { addLast(value); return; }
        Node<T> curr = head;
        for (int i = 0; i < index; i++) curr = curr.next;
        insertBetween(curr.prev, curr, value);
    }

    // Removes and returns the first element.
    // Input:  none
    // Output: T
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException("List is empty");
        return unlink(head);
    }

    // Removes and returns the last element.
    // Input:  none
    // Output: T
    public T removeLast() {
        if (tail == null) throw new java.util.NoSuchElementException("List is empty");
        return unlink(tail);
    }

    // Removes and returns the element at the specified index, traversing from the nearer end.
    // Input:  index (int)
    // Output: T
    public T removeAt(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException("Index: " + index);
        Node<T> curr;
        if (index < length / 2) {
            curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
        } else {
            curr = tail;
            for (int i = length - 1; i > index; i--) curr = curr.prev;
        }
        return unlink(curr);
    }

    // Returns the element at the given index, traversing from the nearer end.
    // Input:  index (int)
    // Output: T
    public T get(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException("Index: " + index);
        Node<T> curr;
        if (index < length / 2) {
            curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
        } else {
            curr = tail;
            for (int i = length - 1; i > index; i--) curr = curr.prev;
        }
        return curr.data;
    }

    // Returns the head element without removing it.
    // Input:  none
    // Output: T
    public T first() {
        if (head == null) throw new java.util.NoSuchElementException("List is empty");
        return head.data;
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
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data != null && curr.data.equals(value)) return true;
            curr = curr.next;
        }
        return false;
    }

    // Reverses the list in place by swapping each node's prev and next references.
    // Input:  none
    // Output: void
    public void reverse() {
        Node<T> curr = head;
        Node<T> temp = head;
        head = tail;
        tail = temp;
        while (curr != null) {
            Node<T> swap = curr.prev;
            curr.prev = curr.next;
            curr.next = swap;
            curr = curr.prev;
        }
    }

    // Resets the list to empty by unlinking all nodes.
    // Input:  none
    // Output: void
    public void clear() { head = null; tail = null; length = 0; }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns a bidirectional arrow-notation string of all elements.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("null <-> ");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) sb.append(" <-> ");
            curr = curr.next;
        }
        return sb.append(" <-> null").toString();
    }
}
