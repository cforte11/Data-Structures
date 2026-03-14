/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Singly Linked List
 * Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.
 */

public class SinglyLinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; this.next = null; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;

    // Initializes an empty list with no nodes.
    // Input:  none
    // Output: void
    public SinglyLinkedList() { head = null; tail = null; length = 0; }

    // Prepends a new value before the current head.
    // Input:  value (T)
    // Output: void
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) tail = head;
        length++;
    }

    // Appends a new value after the current tail.
    // Input:  value (T)
    // Output: void
    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = tail;
        length++;
    }

    // Inserts a new value at the specified zero-based index.
    // Input:  index (int), value (T)
    // Output: void
    public void addAt(int index, T value) {
        if (index < 0 || index > length) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) { addFirst(value); return; }
        if (index == length) { addLast(value); return; }
        Node<T> prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev.next;
        Node<T> node = new Node<>(value);
        node.next = prev.next;
        prev.next = node;
        length++;
    }

    // Removes and returns the first element.
    // Input:  none
    // Output: T
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException("List is empty");
        T val = head.data;
        head = head.next;
        if (head == null) tail = null;
        length--;
        return val;
    }

    // Traverses to the second-to-last node and removes the tail.
    // Input:  none
    // Output: T
    public T removeLast() {
        if (head == null) throw new java.util.NoSuchElementException("List is empty");
        if (head == tail) return removeFirst();
        Node<T> prev = head;
        while (prev.next != tail) prev = prev.next;
        T val = tail.data;
        tail = prev;
        tail.next = null;
        length--;
        return val;
    }

    // Removes and returns the element at the specified index.
    // Input:  index (int)
    // Output: T
    public T removeAt(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();
        Node<T> prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev.next;
        Node<T> target = prev.next;
        prev.next = target.next;
        length--;
        return target.data;
    }

    // Returns the element at the specified index by linear traversal.
    // Input:  index (int)
    // Output: T
    public T get(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException("Index: " + index);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) curr = curr.next;
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

    // Reverses all nodes in place using iterative pointer reassignment.
    // Input:  none
    // Output: void
    public void reverse() {
        Node<T> prev = null, curr = head;
        tail = head;
        while (curr != null) {
            Node<T> next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
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

    // Returns a string of elements separated by arrows.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }
        return sb.append(" -> null").toString();
    }
}
