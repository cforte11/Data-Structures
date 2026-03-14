/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circular Queue
 * Description: Personal take on a fixed-capacity FIFO queue using a circular array with modular index arithmetic.
 */

class CircularQueue<T>(private val capacity: Int) {

    init { require(capacity >= 1) { "Capacity must be >= 1" } }

    private val data: Array<Any?> = arrayOfNulls(capacity)
    private var front: Int = 0
    private var length: Int = 0

    // Adds a value to the back, wrapping around via modular arithmetic.
    // Input:  value (T)
    // Output: Unit
    fun enqueue(value: T) {
        if (isFull()) throw IllegalStateException("Queue is full")
        data[(front + length) % capacity] = value
        length++
    }

    // Removes and returns the front element, advancing the front index circularly.
    // Input:  none
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun dequeue(): T {
        if (isEmpty()) throw NoSuchElementException("Queue is empty")
        val val_ = data[front] as T
        data[front] = null
        front = (front + 1) % capacity
        length--
        return val_
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun peek(): T {
        if (isEmpty()) throw NoSuchElementException("Queue is empty")
        return data[front] as T
    }

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun back(): T {
        if (isEmpty()) throw NoSuchElementException("Queue is empty")
        return data[(front + length - 1) % capacity] as T
    }

    // Resets all slots, front index, and length.
    // Input:  none
    // Output: Unit
    fun clear() { data.fill(null); front = 0; length = 0 }

    // Returns the number of elements currently in the queue.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if no elements are present.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns true if the queue has reached its fixed capacity.
    // Input:  none
    // Output: Boolean
    fun isFull(): Boolean = length == capacity

    // Returns a string of elements in FIFO order from front to back.
    // Input:  none
    // Output: String
    override fun toString(): String =
        "front [ " + (0 until length).joinToString(", ") { data[(front + it) % capacity].toString() } + " ] back"
}
