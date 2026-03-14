/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Deque (Double-Ended Queue)
 * Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back via a doubly linked list.
 */

class Deque<T> {

    private inner class Node(var data: T, var prev: Node? = null, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    private var length: Int = 0

    // Inserts a new value at the front of the deque.
    // Input:  value (T)
    // Output: Unit
    fun addFirst(value: T) {
        val node = Node(value, null, head)
        head?.prev = node
        head = node
        if (tail == null) tail = head
        length++
    }

    // Appends a new value at the back of the deque.
    // Input:  value (T)
    // Output: Unit
    fun addLast(value: T) {
        val node = Node(value, tail, null)
        tail?.next = node
        tail = node
        if (head == null) head = tail
        length++
    }

    // Removes and returns the front element.
    // Input:  none
    // Output: T
    fun removeFirst(): T {
        val h = head ?: throw NoSuchElementException("Deque is empty")
        val val_ = h.data
        head = h.next
        if (head != null) head!!.prev = null else tail = null
        length--
        return val_
    }

    // Removes and returns the back element.
    // Input:  none
    // Output: T
    fun removeLast(): T {
        val t = tail ?: throw NoSuchElementException("Deque is empty")
        val val_ = t.data
        tail = t.prev
        if (tail != null) tail!!.next = null else head = null
        length--
        return val_
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    fun first(): T = head?.data ?: throw NoSuchElementException("Deque is empty")

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    fun last(): T = tail?.data ?: throw NoSuchElementException("Deque is empty")

    // Removes all nodes and resets the deque to empty.
    // Input:  none
    // Output: Unit
    fun clear() { head = null; tail = null; length = 0 }

    // Returns the number of elements in the deque.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the deque holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns a string of elements from front to back.
    // Input:  none
    // Output: String
    override fun toString(): String {
        val parts = mutableListOf<String>()
        var curr = head
        while (curr != null) { parts.add(curr.data.toString()); curr = curr.next }
        return "front [ " + parts.joinToString(", ") + " ] back"
    }
}
