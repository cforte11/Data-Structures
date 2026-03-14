/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Skip List
 * Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.
 */

const MAX_LEVEL = 16;
const PROB = 0.5;

class SkipList {
  #header;
  #currentLevel;
  #length;

  // Initializes the skip list with a sentinel header and level tracking.
  // Input:  none
  // Output: void
  constructor() {
    this.#header = { key: null, forward: new Array(MAX_LEVEL).fill(null) };
    this.#currentLevel = 0;
    this.#length = 0;
  }

  // Generates a random level using geometric distribution.
  // Input:  none
  // Output: number (level between 0 and MAX_LEVEL-1)
  #randomLevel() {
    let level = 0;
    while (Math.random() < PROB && level < MAX_LEVEL - 1) level++;
    return level;
  }

  // Searches for a key and returns true if it exists in the list.
  // Input:  key (*)
  // Output: boolean
  contains(key) {
    let curr = this.#header;
    for (let i = this.#currentLevel; i >= 0; i--)
      while (curr.forward[i] && curr.forward[i].key < key) curr = curr.forward[i];
    curr = curr.forward[0];
    return curr !== null && curr.key === key;
  }

  // Inserts a key into the skip list at the correct sorted position.
  // Input:  key (*)
  // Output: void
  insert(key) {
    const update = new Array(MAX_LEVEL).fill(null);
    let curr = this.#header;
    for (let i = this.#currentLevel; i >= 0; i--) {
      while (curr.forward[i] && curr.forward[i].key < key) curr = curr.forward[i];
      update[i] = curr;
    }
    curr = curr.forward[0];
    if (curr && curr.key === key) return;
    const newLevel = this.#randomLevel();
    if (newLevel > this.#currentLevel) {
      for (let i = this.#currentLevel + 1; i <= newLevel; i++) update[i] = this.#header;
      this.#currentLevel = newLevel;
    }
    const node = { key, forward: new Array(newLevel + 1).fill(null) };
    for (let i = 0; i <= newLevel; i++) {
      node.forward[i] = update[i].forward[i];
      update[i].forward[i] = node;
    }
    this.#length++;
  }

  // Removes the node with the given key if it exists.
  // Input:  key (*)
  // Output: void
  remove(key) {
    const update = new Array(MAX_LEVEL).fill(null);
    let curr = this.#header;
    for (let i = this.#currentLevel; i >= 0; i--) {
      while (curr.forward[i] && curr.forward[i].key < key) curr = curr.forward[i];
      update[i] = curr;
    }
    curr = curr.forward[0];
    if (!curr || curr.key !== key) return;
    for (let i = 0; i <= this.#currentLevel; i++) {
      if (update[i].forward[i] !== curr) break;
      update[i].forward[i] = curr.forward[i];
    }
    while (this.#currentLevel > 0 && !this.#header.forward[this.#currentLevel])
      this.#currentLevel--;
    this.#length--;
  }

  // Resets all header forward pointers and restores initial state.
  // Input:  none
  // Output: void
  clear() {
    this.#header.forward = new Array(MAX_LEVEL).fill(null);
    this.#currentLevel = 0;
    this.#length = 0;
  }

  // Returns the number of elements stored in the skip list.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the skip list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns a string of all keys at level 0 in sorted order.
  // Input:  none
  // Output: string
  toString() {
    const parts = [];
    let curr = this.#header.forward[0];
    while (curr) { parts.push(curr.key); curr = curr.forward[0]; }
    return "[ " + parts.join(", ") + " ]";
  }
}

module.exports = SkipList;
