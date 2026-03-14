/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Skip List
 * Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.
 */

import kotlin.random.Random

class SkipList<T : Comparable<T>> {

    private companion object {
        const val MAX_LEVEL = 16
        const val PROB = 0.5
    }

    private inner class Node(val key: T?, val level: Int) {
        val forward: Array<Node?> = arrayOfNulls(level + 1)
    }

    private val header: Node = Node(null, MAX_LEVEL - 1)
    private var currentLevel: Int = 0
    private var length: Int = 0

    // Generates a random level for a new node using geometric distribution.
    // Input:  none
    // Output: Int (level between 0 and MAX_LEVEL-1)
    private fun randomLevel(): Int {
        var level = 0
        while (Random.nextDouble() < PROB && level < MAX_LEVEL - 1) level++
        return level
    }

    // Searches for a key and returns true if it exists in the list.
    // Input:  key (T)
    // Output: Boolean
    fun contains(key: T): Boolean {
        var curr = header
        for (i in currentLevel downTo 0)
            while (curr.forward[i] != null && curr.forward[i]!!.key!! < key) curr = curr.forward[i]!!
        curr = curr.forward[0] ?: return false
        return curr.key == key
    }

    // Inserts a key into the skip list at the correct sorted position.
    // Input:  key (T)
    // Output: Unit
    fun insert(key: T) {
        val update = arrayOfNulls<Node>(MAX_LEVEL)
        var curr = header
        for (i in currentLevel downTo 0) {
            while (curr.forward[i] != null && curr.forward[i]!!.key!! < key) curr = curr.forward[i]!!
            update[i] = curr
        }
        curr = curr.forward[0] ?: run { insertNode(key, update); return }
        if (curr.key == key) return
        insertNode(key, update)
    }

    // Creates and links a new node at the appropriate level using the update array.
    // Input:  key (T), update (Array<Node?>)
    // Output: Unit
    private fun insertNode(key: T, update: Array<Node?>) {
        val newLevel = randomLevel()
        if (newLevel > currentLevel) {
            for (i in currentLevel + 1..newLevel) update[i] = header
            currentLevel = newLevel
        }
        val node = Node(key, newLevel)
        for (i in 0..newLevel) {
            node.forward[i] = update[i]!!.forward[i]
            update[i]!!.forward[i] = node
        }
        length++
    }

    // Removes the node with the given key if it exists.
    // Input:  key (T)
    // Output: Unit
    fun remove(key: T) {
        val update = arrayOfNulls<Node>(MAX_LEVEL)
        var curr = header
        for (i in currentLevel downTo 0) {
            while (curr.forward[i] != null && curr.forward[i]!!.key!! < key) curr = curr.forward[i]!!
            update[i] = curr
        }
        curr = curr.forward[0] ?: return
        if (curr.key != key) return
        for (i in 0..currentLevel) {
            if (update[i]!!.forward[i] !== curr) break
            update[i]!!.forward[i] = curr.forward[i]
        }
        while (currentLevel > 0 && header.forward[currentLevel] == null) currentLevel--
        length--
    }

    // Resets all header forward pointers and restores initial state.
    // Input:  none
    // Output: Unit
    fun clear() {
        header.forward.fill(null)
        currentLevel = 0
        length = 0
    }

    // Returns the number of elements stored in the skip list.
    // Input:  none
    // Output: Int
    fun size(): Int = length

    // Returns true if the skip list holds no elements.
    // Input:  none
    // Output: Boolean
    fun isEmpty(): Boolean = length == 0

    // Returns a string of all keys at level 0 in sorted order.
    // Input:  none
    // Output: String
    override fun toString(): String {
        val parts = mutableListOf<String>()
        var curr = header.forward[0]
        while (curr != null) { parts.add(curr.key.toString()); curr = curr.forward[0] }
        return "[ " + parts.joinToString(", ") + " ]"
    }
}
