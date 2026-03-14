/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circularly Linked List
 * Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.
 */

class CircularlyLinkedList<T> {

    private inner class Node(val data: T, var next: Node? = null)

    private var tail: Node? = null
    private var length: Int = 0

    // Inserts a new value immediately after the tail, making it the new head.
    // Input:  value (T)
    // Output: Unit
    fun addFirst(value: T) {
        val node = Node(value)
        if (tail == null) {
            node.next = node
            tail = node
        } else {
            node.next = tail!!.next
            tail!!.next = node
        }
        length++
    }

    // Appends a new value as the new tail of the circular list.
    // Input:  value (T)
    // Output: Unit
    fun addLast(value: T) {
        addFirst(value)
        tail = tail!!.next
    }

    // Removes and returns the front (head) element.
    // Input:  none
    // Output: T
    fun removeFirst(): T {
        val t = tail ?: throw NoSuchElementException("List is empty")
        val head = t.next!!
        val val_ = head.data
        if (tail === head) tail = null else t.next = head.next
        length--
        return val_
    }

    // Advances the tail pointer one step forward, rotating the front to the back.
    // Input:  none
    // Output: Unit
    fun rotate() { tail = tail?.next }

    // Returns the head element without removing it.
    // Input:  none
    // Output: T
    fun first(): T = tail?.next?.data ?: throw NoSuchElementException("List is empty")

    // Returns the tail element without removing it.
    // Input:  none
    // Output: T
    fun last(): T = tail?.data ?: throw NoSuchElementException("List is empty")

    // Returns true if the list contains the given value.
    // Input:  value (T)
    // Output: Boolean
    fun contains(value: T): Boolean {
        val t = tail ?: return false
        var curr = t.next!!
        do {
            if (curr.data == value) return true
            curr = curr.next!!
        } while (curr !== t.next)
        return false
    }

    // Resets the list to empty by discarding all node references.
    // Input:  none
    // Output: Unit
    fun clear() { tail = null; length = 0 }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns a string of all elements starting from the head, noting the circular wrap.
    // Input:  none
    // Output: String
    override fun toString(): String {
        val t = tail ?: return "(empty)"
        val parts = mutableListOf<String>()
        var curr = t.next!!
        do { parts.add(curr.data.toString()); curr = curr.next!! } while (curr !== t.next)
        return parts.joinToString(" -> ") + " -> (head)"
    }
}
