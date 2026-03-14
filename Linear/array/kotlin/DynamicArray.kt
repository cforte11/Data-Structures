/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Array
 * Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.
 */

class DynamicArray<T>(initialCapacity: Int = 8) {

    private var data: Array<Any?> = arrayOfNulls(initialCapacity)
    private var capacity: Int = initialCapacity
    private var length: Int = 0

    init {
        require(initialCapacity >= 1) { "Capacity must be >= 1" }
    }

    // Doubles the internal storage capacity when the array is full.
    // Input:  none
    // Output: Unit
    private fun resize() {
        val newCapacity = capacity * 2
        data = data.copyOf(newCapacity)
        capacity = newCapacity
    }

    // Validates that an index falls within the current element range.
    // Input:  index (Int)
    // Output: Unit (throws IndexOutOfBoundsException on violation)
    private fun checkBounds(index: Int) {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
    }

    // Appends a value to the end of the array, resizing if necessary.
    // Input:  value (T)
    // Output: Unit
    fun append(value: T) {
        if (length == capacity) resize()
        data[length++] = value
    }

    // Inserts a value at the specified index, shifting subsequent elements right.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun insert(index: Int, value: T) {
        if (index < 0 || index > length) throw IndexOutOfBoundsException("Index: $index")
        if (length == capacity) resize()
        data.copyInto(data, index + 1, index, length)
        data[index] = value
        length++
    }

    // Removes and returns the element at the specified index, shifting remaining elements left.
    // Input:  index (Int)
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun remove(index: Int): T {
        checkBounds(index)
        val removed = data[index] as T
        data.copyInto(data, index, index + 1, length)
        data[--length] = null
        return removed
    }

    // Returns the element at the given index.
    // Input:  index (Int)
    // Output: T
    @Suppress("UNCHECKED_CAST")
    fun get(index: Int): T {
        checkBounds(index)
        return data[index] as T
    }

    // Replaces the value at the specified index.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun set(index: Int, value: T) {
        checkBounds(index)
        data[index] = value
    }

    // Returns the index of the first occurrence of a value, or -1 if not found.
    // Input:  value (T)
    // Output: Int
    fun indexOf(value: T): Int {
        for (i in 0 until length) if (data[i] == value) return i
        return -1
    }

    // Resets length to zero without freeing capacity.
    // Input:  none
    // Output: Unit
    fun clear() {
        data.fill(null, 0, length)
        length = 0
    }

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
