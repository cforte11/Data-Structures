/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Queue
 * Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a singly linked list for O(1) enqueue and dequeue.
 */

class Queue {
  #head;
  #tail;
  #length;

  // Initializes an empty queue.
  // Input:  none
  // Output: void
  constructor() { this.#head = null; this.#tail = null; this.#length = 0; }

  // Adds a new value to the back of the queue.
  // Input:  value (*)
  // Output: void
  enqueue(value) {
    const node = { data: value, next: null };
    if (this.#tail) this.#tail.next = node;
    this.#tail = node;
    if (!this.#head) this.#head = this.#tail;
    this.#length++;
  }

  // Removes and returns the front element.
  // Input:  none
  // Output: *
  dequeue() {
    if (!this.#head) throw new RangeError("Queue is empty");
    const val = this.#head.data;
    this.#head = this.#head.next;
    if (!this.#head) this.#tail = null;
    this.#length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: *
  front() {
    if (!this.#head) throw new RangeError("Queue is empty");
    return this.#head.data;
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: *
  back() {
    if (!this.#tail) throw new RangeError("Queue is empty");
    return this.#tail.data;
  }

  // Removes all elements from the queue.
  // Input:  none
  // Output: void
  clear() { this.#head = null; this.#tail = null; this.#length = 0; }

  // Returns the number of elements in the queue.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the queue holds no elements.
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

module.exports = Queue;
