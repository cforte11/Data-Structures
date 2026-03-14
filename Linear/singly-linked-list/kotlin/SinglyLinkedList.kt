/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Singly Linked List
 * Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.
 */

class SinglyLinkedList<T> {

    private inner class Node(val data: T, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    private var length: Int = 0

    // Prepends a new value before the current head.
    // Input:  value (T)
    // Output: Unit
    fun addFirst(value: T) {
        val node = Node(value, head)
        head = node
        if (tail == null) tail = head
        length++
    }

    // Appends a new value after the current tail.
    // Input:  value (T)
    // Output: Unit
    fun addLast(value: T) {
        val node = Node(value)
        tail?.next = node
        tail = node
        if (head == null) head = tail
        length++
    }

    // Inserts a new value at the specified zero-based index.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun addAt(index: Int, value: T) {
        if (index < 0 || index > length) throw IndexOutOfBoundsException("Index: $index")
        when (index) {
            0 -> addFirst(value)
            length -> addLast(value)
            else -> {
                var prev = head!!
                repeat(index - 1) { prev = prev.next!! }
                val node = Node(value, prev.next)
                prev.next = node
                length++
            }
        }
    }

    // Removes and returns the first element.
    // Input:  none
    // Output: T
    fun removeFirst(): T {
        val h = head ?: throw NoSuchElementException("List is empty")
        val val_ = h.data
        head = h.next
        if (head == null) tail = null
        length--
        return val_
    }

    // Traverses to the second-to-last node and removes the tail.
    // Input:  none
    // Output: T
    fun removeLast(): T {
        if (head == null) throw NoSuchElementException("List is empty")
        if (head === tail) return removeFirst()
        var prev = head!!
        while (prev.next !== tail) prev = prev.next!!
        val val_ = tail!!.data
        tail = prev
        tail!!.next = null
        length--
        return val_
    }

    // Removes and returns the element at the specified index.
    // Input:  index (Int)
    // Output: T
    fun removeAt(index: Int): T {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
        return when (index) {
            0 -> removeFirst()
            length - 1 -> removeLast()
            else -> {
                var prev = head!!
                repeat(index - 1) { prev = prev.next!! }
                val target = prev.next!!
                prev.next = target.next
                length--
                target.data
            }
        }
    }

    // Returns the element at the specified index by linear traversal.
    // Input:  index (Int)
    // Output: T
    fun get(index: Int): T {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
        var curr = head!!
        repeat(index) { curr = curr.next!! }
        return curr.data
    }

    // Returns the head element without removing it.
    // Input:  none
    // Output: T
    fun first(): T = head?.data ?: throw NoSuchElementException("List is empty")

    // Returns the tail element without removing it.
    // Input:  none
    // Output: T
    fun last(): T = tail?.data ?: throw NoSuchElementException("List is empty")

    // Returns true if the list contains the given value.
    // Input:  value (T)
    // Output: Boolean
    fun contains(value: T): Boolean {
        var curr = head
        while (curr != null) {
            if (curr.data == value) return true
            curr = curr.next
        }
        return false
    }

    // Reverses all nodes in place using iterative pointer reassignment.
    // Input:  none
    // Output: Unit
    fun reverse() {
        var prev: Node? = null
        var curr = head
        tail = head
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        head = prev
    }

    // Resets the list to empty by unlinking all nodes.
    // Input:  none
    // Output: Unit
    fun clear() { head = null; tail = null; length = 0 }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns a string of elements separated by arrows.
    // Input:  none
    // Output: String
    override fun toString(): String {
        val parts = mutableListOf<String>()
        var curr = head
        while (curr != null) { parts.add(curr.data.toString()); curr = curr.next }
        return parts.joinToString(" -> ") + " -> null"
    }
}
