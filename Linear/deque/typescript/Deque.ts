/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Deque (Double-Ended Queue)
 * Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back via a doubly linked list.
 */

interface DequeNode<T> {
  data: T;
  prev: DequeNode<T> | null;
  next: DequeNode<T> | null;
}

export class Deque<T> {
  private head: DequeNode<T> | null = null;
  private tail: DequeNode<T> | null = null;
  private _length: number = 0;

  // Inserts a new value at the front of the deque.
  // Input:  value (T)
  // Output: void
  addFirst(value: T): void {
    const node: DequeNode<T> = { data: value, prev: null, next: this.head };
    if (this.head) this.head.prev = node;
    this.head = node;
    if (!this.tail) this.tail = this.head;
    this._length++;
  }

  // Appends a new value at the back of the deque.
  // Input:  value (T)
  // Output: void
  addLast(value: T): void {
    const node: DequeNode<T> = { data: value, prev: this.tail, next: null };
    if (this.tail) this.tail.next = node;
    this.tail = node;
    if (!this.head) this.head = this.tail;
    this._length++;
  }

  // Removes and returns the front element.
  // Input:  none
  // Output: T
  removeFirst(): T {
    if (!this.head) throw new RangeError("Deque is empty");
    const val = this.head.data;
    this.head = this.head.next;
    if (this.head) this.head.prev = null; else this.tail = null;
    this._length--;
    return val;
  }

  // Removes and returns the back element.
  // Input:  none
  // Output: T
  removeLast(): T {
    if (!this.tail) throw new RangeError("Deque is empty");
    const val = this.tail.data;
    this.tail = this.tail.prev;
    if (this.tail) this.tail.next = null; else this.head = null;
    this._length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: T
  first(): T {
    if (!this.head) throw new RangeError("Deque is empty");
    return this.head.data;
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: T
  last(): T {
    if (!this.tail) throw new RangeError("Deque is empty");
    return this.tail.data;
  }

  // Removes all nodes and resets the deque to empty.
  // Input:  none
  // Output: void
  clear(): void { this.head = null; this.tail = null; this._length = 0; }

  // Returns the number of elements in the deque.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the deque holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns a string of elements from front to back.
  // Input:  none
  // Output: string
  toString(): string {
    const parts: string[] = [];
    let curr = this.head;
    while (curr) { parts.push(String(curr.data)); curr = curr.next; }
    return "front [ " + parts.join(", ") + " ] back";
  }
}
