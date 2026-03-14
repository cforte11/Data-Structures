/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Queue
 * Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a singly linked list for O(1) enqueue and dequeue.
 */

class Queue<T> {

    private inner class Node(val data: T, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    private var length: Int = 0

    // Adds a new value to the back of the queue.
    // Input:  value (T)
    // Output: Unit
    fun enqueue(value: T) {
        val node = Node(value)
        tail?.next = node
        tail = node
        if (head == null) head = tail
        length++
    }

    // Removes and returns the front element.
    // Input:  none
    // Output: T
    fun dequeue(): T {
        val h = head ?: throw NoSuchElementException("Queue is empty")
        val val_ = h.data
        head = h.next
        if (head == null) tail = null
        length--
        return val_
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    fun front(): T = head?.data ?: throw NoSuchElementException("Queue is empty")

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    fun back(): T = tail?.data ?: throw NoSuchElementException("Queue is empty")

    // Removes all elements from the queue.
    // Input:  none
    // Output: Unit
    fun clear() { head = null; tail = null; length = 0 }

    // Returns the number of elements in the queue.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the queue holds no elements.
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
