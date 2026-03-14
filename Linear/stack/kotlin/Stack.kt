/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Stack
 * Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic list.
 */

class Stack<T> {

    private val data = mutableListOf<T>()

    // Pushes a new value onto the top of the stack.
    // Input:  value (T)
    // Output: Unit
    fun push(value: T) { data.add(value) }

    // Removes and returns the top element.
    // Input:  none
    // Output: T
    fun pop(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty")
        return data.removeAt(data.size - 1)
    }

    // Returns the top element without removing it.
    // Input:  none
    // Output: T
    fun peek(): T {
        if (isEmpty()) throw NoSuchElementException("Stack is empty")
        return data.last()
    }

    // Removes all elements from the stack.
    // Input:  none
    // Output: Unit
    fun clear() { data.clear() }

    // Returns the number of elements currently on the stack.
    // Input:  none
    // Output: Int
    fun size(): Int = data.size

    // Returns true if the stack holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = data.isEmpty()

    // Returns a string of elements from bottom to top.
    // Input:  none
    // Output: String
    override fun toString(): String = "bottom $data top"
}
