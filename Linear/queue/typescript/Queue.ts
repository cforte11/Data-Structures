/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Queue
 * Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a singly linked list for O(1) enqueue and dequeue.
 */

interface QNode<T> {
  data: T;
  next: QNode<T> | null;
}

export class Queue<T> {
  private head: QNode<T> | null = null;
  private tail: QNode<T> | null = null;
  private _length: number = 0;

  // Adds a new value to the back of the queue.
  // Input:  value (T)
  // Output: void
  enqueue(value: T): void {
    const node: QNode<T> = { data: value, next: null };
    if (this.tail) this.tail.next = node;
    this.tail = node;
    if (!this.head) this.head = this.tail;
    this._length++;
  }

  // Removes and returns the front element.
  // Input:  none
  // Output: T
  dequeue(): T {
    if (!this.head) throw new RangeError("Queue is empty");
    const val = this.head.data;
    this.head = this.head.next;
    if (!this.head) this.tail = null;
    this._length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: T
  front(): T {
    if (!this.head) throw new RangeError("Queue is empty");
    return this.head.data;
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: T
  back(): T {
    if (!this.tail) throw new RangeError("Queue is empty");
    return this.tail.data;
  }

  // Removes all elements from the queue.
  // Input:  none
  // Output: void
  clear(): void { this.head = null; this.tail = null; this._length = 0; }

  // Returns the number of elements in the queue.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the queue holds no elements.
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
