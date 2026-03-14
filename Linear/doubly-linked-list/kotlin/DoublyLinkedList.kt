/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Doubly Linked List
 * Description: Personal take on a bidirectionally linked linear structure enabling O(1) insertion and removal at both ends.
 */

class DoublyLinkedList<T> {

    private inner class Node(var data: T, var prev: Node? = null, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    private var length: Int = 0

    // Stitches a new node between two existing adjacent nodes.
    // Input:  before (Node?), after (Node?), value (T)
    // Output: Unit
    private fun insertBetween(before: Node?, after: Node?, value: T) {
        val node = Node(value, before, after)
        if (before != null) before.next = node else head = node
        if (after != null) after.prev = node else tail = node
        length++
    }

    // Detaches a node from its neighbors and returns its value.
    // Input:  node (Node)
    // Output: T
    private fun unlink(node: Node): T {
        if (node.prev != null) node.prev!!.next = node.next else head = node.next
        if (node.next != null) node.next!!.prev = node.prev else tail = node.prev
        length--
        return node.data
    }

    // Prepends a new value before the current head.
    // Input:  value (T)
    // Output: Unit
    fun addFirst(value: T) = insertBetween(null, head, value)

    // Appends a new value after the current tail.
    // Input:  value (T)
    // Output: Unit
    fun addLast(value: T) = insertBetween(tail, null, value)

    // Inserts a new value at the specified zero-based index.
    // Input:  index (Int), value (T)
    // Output: Unit
    fun addAt(index: Int, value: T) {
        if (index < 0 || index > length) throw IndexOutOfBoundsException("Index: $index")
        when (index) {
            0 -> addFirst(value)
            length -> addLast(value)
            else -> {
                var curr = head!!
                repeat(index) { curr = curr.next!! }
                insertBetween(curr.prev, curr, value)
            }
        }
    }

    // Removes and returns the first element.
    // Input:  none
    // Output: T
    fun removeFirst(): T = unlink(head ?: throw NoSuchElementException("List is empty"))

    // Removes and returns the last element.
    // Input:  none
    // Output: T
    fun removeLast(): T = unlink(tail ?: throw NoSuchElementException("List is empty"))

    // Removes and returns the element at the specified index, traversing from the nearer end.
    // Input:  index (Int)
    // Output: T
    fun removeAt(index: Int): T {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
        val curr = if (index < length / 2) {
            var c = head!!; repeat(index) { c = c.next!! }; c
        } else {
            var c = tail!!; repeat(length - 1 - index) { c = c.prev!! }; c
        }
        return unlink(curr)
    }

    // Returns the element at the given index, traversing from the nearer end.
    // Input:  index (Int)
    // Output: T
    fun get(index: Int): T {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException("Index: $index")
        return if (index < length / 2) {
            var c = head!!; repeat(index) { c = c.next!! }; c.data
        } else {
            var c = tail!!; repeat(length - 1 - index) { c = c.prev!! }; c.data
        }
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
        while (curr != null) { if (curr.data == value) return true; curr = curr.next }
        return false
    }

    // Reverses the list in place by swapping each node's prev and next references.
    // Input:  none
    // Output: Unit
    fun reverse() {
        var curr = head
        val temp = head; head = tail; tail = temp
        while (curr != null) {
            val swap = curr.prev; curr.prev = curr.next; curr.next = swap
            curr = curr.prev
        }
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

    // Returns a bidirectional arrow-notation string of all elements.
    // Input:  none
    // Output: String
    override fun toString(): String {
        val parts = mutableListOf<String>()
        var curr = head
        while (curr != null) { parts.add(curr.data.toString()); curr = curr.next }
        return "null <-> " + parts.joinToString(" <-> ") + " <-> null"
    }
}
