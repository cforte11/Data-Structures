/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Positional List
 * Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.
 */

class PositionalList<T> : Iterable<T> {

    private inner class Node(
        var data: T?,
        var prev: Node?,
        var next: Node?,
        var container: PositionalList<T>?
    )

    // A lightweight handle wrapping an internal node reference.
    inner class Position internal constructor(internal val node: Node) {

        // Returns the element stored at this position.
        // Input:  none
        // Output: T
        fun element(): T = node.data!!

        // Returns true if this position refers to the same node as another.
        // Input:  other (Position)
        // Output: Boolean
        fun equalsPosition(other: Position): Boolean = this.node === other.node
    }

    private val header: Node  = Node(null, null, null, this)
    private val trailer: Node = Node(null, null, null, this)
    private var length: Int = 0

    init {
        header.next  = trailer
        trailer.prev = header
    }

    // Validates a position belongs to this list and has not been deleted.
    // Input:  p (Position)
    // Output: Node
    private fun validate(p: Position): Node {
        if (p.node.container !== this) throw IllegalArgumentException("Position does not belong to this list")
        if (p.node.next == null) throw IllegalStateException("Position is no longer valid")
        return p.node
    }

    // Returns a Position for a node, or null if it is a sentinel.
    // Input:  node (Node)
    // Output: Position?
    private fun makePosition(node: Node): Position? =
        if (node === header || node === trailer) null else Position(node)

    // Creates a new node between two existing nodes and returns its position.
    // Input:  value (T), before (Node), after (Node)
    // Output: Position
    private fun insertBetween(value: T, before: Node, after: Node): Position {
        val node = Node(value, before, after, this)
        before.next = node
        after.prev  = node
        length++
        return Position(node)
    }

    // Returns the position of the first element, or null if empty.
    // Input:  none
    // Output: Position?
    fun first(): Position? = makePosition(header.next!!)

    // Returns the position of the last element, or null if empty.
    // Input:  none
    // Output: Position?
    fun last(): Position? = makePosition(trailer.prev!!)

    // Returns the position immediately before the given position.
    // Input:  p (Position)
    // Output: Position?
    fun before(p: Position): Position? = makePosition(validate(p).prev!!)

    // Returns the position immediately after the given position.
    // Input:  p (Position)
    // Output: Position?
    fun after(p: Position): Position? = makePosition(validate(p).next!!)

    // Inserts a new element at the front and returns its position.
    // Input:  value (T)
    // Output: Position
    fun addFirst(value: T): Position = insertBetween(value, header, header.next!!)

    // Inserts a new element at the back and returns its position.
    // Input:  value (T)
    // Output: Position
    fun addLast(value: T): Position = insertBetween(value, trailer.prev!!, trailer)

    // Inserts a new element immediately before the given position.
    // Input:  p (Position), value (T)
    // Output: Position
    fun addBefore(p: Position, value: T): Position {
        val node = validate(p)
        return insertBetween(value, node.prev!!, node)
    }

    // Inserts a new element immediately after the given position.
    // Input:  p (Position), value (T)
    // Output: Position
    fun addAfter(p: Position, value: T): Position {
        val node = validate(p)
        return insertBetween(value, node, node.next!!)
    }

    // Removes the element at the given position and returns its value.
    // Input:  p (Position)
    // Output: T
    fun delete(p: Position): T {
        val node = validate(p)
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
        length--
        val val_ = node.data!!
        node.prev = null; node.next = null; node.container = null; node.data = null
        return val_
    }

    // Replaces the element at the given position and returns the old value.
    // Input:  p (Position), value (T)
    // Output: T
    fun replace(p: Position, value: T): T {
        val node = validate(p)
        val old = node.data!!
        node.data = value
        return old
    }

    // Removes all non-sentinel nodes and resets length to zero.
    // Input:  none
    // Output: Unit
    fun clear() { header.next = trailer; trailer.prev = header; length = 0 }

    // Returns the number of elements in the list.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the list holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns an iterator that yields each element value from first to last.
    // Input:  none
    // Output: Iterator<T>
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var cursor = first()
        override fun hasNext(): Boolean = cursor != null
        override fun next(): T {
            val val_ = cursor?.element() ?: throw NoSuchElementException()
            cursor = after(cursor!!)
            return val_
        }
    }

    // Returns a string of all element values from first to last.
    // Input:  none
    // Output: String
    override fun toString(): String = "[ " + joinToString(", ") + " ]"
}
