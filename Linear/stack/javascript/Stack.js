/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Stack
 * Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic array.
 */

class Stack {
  #data;

  // Initializes an empty stack.
  // Input:  none
  // Output: void
  constructor() { this.#data = []; }

  // Pushes a new value onto the top of the stack.
  // Input:  value (*)
  // Output: void
  push(value) { this.#data.push(value); }

  // Removes and returns the top element.
  // Input:  none
  // Output: *
  pop() {
    if (this.isEmpty()) throw new RangeError("Stack is empty");
    return this.#data.pop();
  }

  // Returns the top element without removing it.
  // Input:  none
  // Output: *
  peek() {
    if (this.isEmpty()) throw new RangeError("Stack is empty");
    return this.#data[this.#data.length - 1];
  }

  // Removes all elements from the stack.
  // Input:  none
  // Output: void
  clear() { this.#data = []; }

  // Returns the number of elements currently on the stack.
  // Input:  none
  // Output: number
  size() { return this.#data.length; }

  // Returns true if the stack holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#data.length === 0; }

  // Returns a string of elements from bottom to top.
  // Input:  none
  // Output: string
  toString() { return "bottom [ " + this.#data.join(", ") + " ] top"; }
}

module.exports = Stack;
