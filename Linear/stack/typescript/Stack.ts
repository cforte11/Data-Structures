/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Stack
 * Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic array.
 */

export class Stack<T> {
  private data: T[] = [];

  // Pushes a new value onto the top of the stack.
  // Input:  value (T)
  // Output: void
  push(value: T): void { this.data.push(value); }

  // Removes and returns the top element.
  // Input:  none
  // Output: T
  pop(): T {
    if (this.isEmpty()) throw new RangeError("Stack is empty");
    return this.data.pop()!;
  }

  // Returns the top element without removing it.
  // Input:  none
  // Output: T
  peek(): T {
    if (this.isEmpty()) throw new RangeError("Stack is empty");
    return this.data[this.data.length - 1];
  }

  // Removes all elements from the stack.
  // Input:  none
  // Output: void
  clear(): void { this.data = []; }

  // Returns the number of elements currently on the stack.
  // Input:  none
  // Output: number
  size(): number { return this.data.length; }

  // Returns true if the stack holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this.data.length === 0; }

  // Returns a string of elements from bottom to top.
  // Input:  none
  // Output: string
  toString(): string { return "bottom [ " + this.data.join(", ") + " ] top"; }
}
