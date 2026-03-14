/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circular Queue
 * Description: Personal take on a fixed-capacity FIFO queue using a circular array with modular index arithmetic.
 */

class CircularQueue {
  #data;
  #capacity;
  #front;
  #length;

  // Initializes a circular queue with a fixed capacity.
  // Input:  capacity (number)
  // Output: void
  constructor(capacity) {
    if (capacity < 1) throw new RangeError("Capacity must be >= 1");
    this.#capacity = capacity;
    this.#data = new Array(capacity).fill(undefined);
    this.#front = 0;
    this.#length = 0;
  }

  // Adds a value to the back, wrapping around via modular arithmetic.
  // Input:  value (*)
  // Output: void
  enqueue(value) {
    if (this.isFull()) throw new RangeError("Queue is full");
    const back = (this.#front + this.#length) % this.#capacity;
    this.#data[back] = value;
    this.#length++;
  }

  // Removes and returns the front element, advancing the front index circularly.
  // Input:  none
  // Output: *
  dequeue() {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    const val = this.#data[this.#front];
    this.#data[this.#front] = undefined;
    this.#front = (this.#front + 1) % this.#capacity;
    this.#length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: *
  front() {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    return this.#data[this.#front];
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: *
  back() {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    return this.#data[(this.#front + this.#length - 1) % this.#capacity];
  }

  // Resets all slots, front index, and length.
  // Input:  none
  // Output: void
  clear() { this.#data.fill(undefined); this.#front = 0; this.#length = 0; }

  // Returns the number of elements currently in the queue.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if no elements are present.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns true if the queue has reached its fixed capacity.
  // Input:  none
  // Output: boolean
  isFull() { return this.#length === this.#capacity; }

  // Returns a string of elements in FIFO order from front to back.
  // Input:  none
  // Output: string
  toString() {
    const parts = [];
    for (let i = 0; i < this.#length; i++)
      parts.push(this.#data[(this.#front + i) % this.#capacity]);
    return "front [ " + parts.join(", ") + " ] back";
  }
}

module.exports = CircularQueue;
