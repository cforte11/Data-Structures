/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: ArrayList
 * Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.
 */

class ArrayList<T>(initialCapacity: Int = 8) {

    init { require(initialCapacity >= 1) { "Capacity must be >= 1" } }

    private var data: Array<Any?> = arrayOfNulls(initialCapacity)
    private var capacity: Int = initialCapacity
    private var length: Int = 0

    // Doubles the internal array capacity and migrates all elements.
    // Input:  none
    // Output: Unit
    private fun resize() {
        val newCap = capacity * 2
        data = data.copyOf(newCap)
        capacity = newCap
    }

    // Validates that an index falls within the current element range.
    // Input:  index (Int)
    // Output: Unit (throws IndexOutOfBoundsException on violation)
    private fun checkBounds(index: Int) {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
    }

    // Appends a value to the end of the list, resizing if necessary.
    // Input:  value (T)
    // Output: Unit
    fun add(value: T) {
        if (length == capacity) resize()
        data[length++] = value
    }

    // Inserts a value at the specified index, shifting subsequent elements right.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun add(index: Int, value: T) {
        if (index < 0 || index > length) throw IndexOutOfBoundsException("Index: $index")
        if (length == capacity) resize()
        data.copyInto(data, index + 1, index, length)
        data[index] = value
        length++
    }

    // Removes and returns the element at the given index, shifting elements left.
    // Input:  index (Int)
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun remove(index: Int): T {
        checkBounds(index)
        val val_ = data[index] as T
        data.copyInto(data, index, index + 1, length)
        data[--length] = null
        return val_
    }

    // Returns the element at the given index.
    // Input:  index (Int)
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun get(index: Int): T {
        checkBounds(index)
        return data[index] as T
    }

    // Replaces the element at the given index with a new value.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun set(index: Int, value: T) {
        checkBounds(index)
        data[index] = value
    }

    // Returns the index of the first matching element, or -1 if absent.
    // Input:  value (T)
    // Output: Int
    fun indexOf(value: T): Int {
        for (i in 0 until length) if (data[i] == value) return i
        return -1
    }

    // Returns true if the list contains the given value.
    // Input:  value (T)
    // Output: Boolean
    fun contains(value: T): Boolean = indexOf(value) != -1

    // Resets length to zero, retaining allocated capacity.
    // Input:  none
    // Output: Unit
    fun clear() { data.fill(null, 0, length); length = 0 }

    // Returns the number of elements currently stored.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if no elements are stored.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns the current allocated capacity.
    // Input:  none
    // Output: Int
    fun capacity(): Int = capacity

    // Returns a string representation of all stored elements.
    // Input:  none
    // Output: String
    override fun toString(): String =
        "[ " + (0 until length).joinToString(", ") { data[it].toString() } + " ]"
}
