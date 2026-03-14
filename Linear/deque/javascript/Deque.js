/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Deque (Double-Ended Queue)
 * Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back via a doubly linked list.
 */

class Deque {
  #head;
  #tail;
  #length;

  // Initializes an empty deque.
  // Input:  none
  // Output: void
  constructor() { this.#head = null; this.#tail = null; this.#length = 0; }

  // Inserts a new value at the front of the deque.
  // Input:  value (*)
  // Output: void
  addFirst(value) {
    const node = { data: value, prev: null, next: this.#head };
    if (this.#head) this.#head.prev = node;
    this.#head = node;
    if (!this.#tail) this.#tail = this.#head;
    this.#length++;
  }

  // Appends a new value at the back of the deque.
  // Input:  value (*)
  // Output: void
  addLast(value) {
    const node = { data: value, prev: this.#tail, next: null };
    if (this.#tail) this.#tail.next = node;
    this.#tail = node;
    if (!this.#head) this.#head = this.#tail;
    this.#length++;
  }

  // Removes and returns the front element.
  // Input:  none
  // Output: *
  removeFirst() {
    if (!this.#head) throw new RangeError("Deque is empty");
    const val = this.#head.data;
    this.#head = this.#head.next;
    if (this.#head) this.#head.prev = null; else this.#tail = null;
    this.#length--;
    return val;
  }

  // Removes and returns the back element.
  // Input:  none
  // Output: *
  removeLast() {
    if (!this.#tail) throw new RangeError("Deque is empty");
    const val = this.#tail.data;
    this.#tail = this.#tail.prev;
    if (this.#tail) this.#tail.next = null; else this.#head = null;
    this.#length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: *
  first() {
    if (!this.#head) throw new RangeError("Deque is empty");
    return this.#head.data;
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: *
  last() {
    if (!this.#tail) throw new RangeError("Deque is empty");
    return this.#tail.data;
  }

  // Removes all nodes and resets the deque to empty.
  // Input:  none
  // Output: void
  clear() { this.#head = null; this.#tail = null; this.#length = 0; }

  // Returns the number of elements in the deque.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the deque holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns a string of elements from front to back.
  // Input:  none
  // Output: string
  toString() {
    const parts = [];
    let curr = this.#head;
    while (curr) { parts.push(curr.data); curr = curr.next; }
    return "front [ " + parts.join(", ") + " ] back";
  }
}

module.exports = Deque;
