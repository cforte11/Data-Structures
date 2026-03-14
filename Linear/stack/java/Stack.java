/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Stack
 * Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic array.
 */

import java.util.ArrayList;

public class Stack<T> {

    private final ArrayList<T> data = new ArrayList<>();

    // Pushes a new value onto the top of the stack.
    // Input:  value (T)
    // Output: void
    public void push(T value) { data.add(value); }

    // Removes and returns the top element.
    // Input:  none
    // Output: T
    public T pop() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack is empty");
        return data.remove(data.size() - 1);
    }

    // Returns the top element without removing it.
    // Input:  none
    // Output: T
    public T peek() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Stack is empty");
        return data.get(data.size() - 1);
    }

    // Removes all elements from the stack.
    // Input:  none
    // Output: void
    public void clear() { data.clear(); }

    // Returns the number of elements currently on the stack.
    // Input:  none
    // Output: int
    public int size() { return data.size(); }

    // Returns true if the stack holds no elements.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return data.isEmpty(); }

    // Returns a string of elements from bottom to top.
    // Input:  none
    // Output: String
    @Override
    public String toString() { return "bottom " + data.toString() + " top"; }
}
