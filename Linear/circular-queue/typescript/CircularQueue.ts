/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circular Queue
 * Description: Personal take on a fixed-capacity FIFO queue using a circular array with modular index arithmetic.
 */

export class CircularQueue<T> {
  private data: (T | undefined)[];
  private readonly _capacity: number;
  private front: number = 0;
  private _length: number = 0;

  // Initializes a circular queue with a fixed capacity.
  // Input:  capacity (number)
  // Output: void
  constructor(capacity: number) {
    if (capacity < 1) throw new RangeError("Capacity must be >= 1");
    this._capacity = capacity;
    this.data = new Array<T | undefined>(capacity).fill(undefined);
  }

  // Adds a value to the back, wrapping around via modular arithmetic.
  // Input:  value (T)
  // Output: void
  enqueue(value: T): void {
    if (this.isFull()) throw new RangeError("Queue is full");
    const back = (this.front + this._length) % this._capacity;
    this.data[back] = value;
    this._length++;
  }

  // Removes and returns the front element, advancing the front index circularly.
  // Input:  none
  // Output: T
  dequeue(): T {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    const val = this.data[this.front] as T;
    this.data[this.front] = undefined;
    this.front = (this.front + 1) % this._capacity;
    this._length--;
    return val;
  }

  // Returns the front element without removing it.
  // Input:  none
  // Output: T
  peek(): T {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    return this.data[this.front] as T;
  }

  // Returns the back element without removing it.
  // Input:  none
  // Output: T
  back(): T {
    if (this.isEmpty()) throw new RangeError("Queue is empty");
    return this.data[(this.front + this._length - 1) % this._capacity] as T;
  }

  // Resets all slots, front index, and length.
  // Input:  none
  // Output: void
  clear(): void { this.data.fill(undefined); this.front = 0; this._length = 0; }

  // Returns the number of elements currently in the queue.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if no elements are present.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns true if the queue has reached its fixed capacity.
  // Input:  none
  // Output: boolean
  isFull(): boolean { return this._length === this._capacity; }

  // Returns a string of elements in FIFO order from front to back.
  // Input:  none
  // Output: string
  toString(): string {
    const parts: string[] = [];
    for (let i = 0; i < this._length; i++)
      parts.push(String(this.data[(this.front + i) % this._capacity]));
    return "front [ " + parts.join(", ") + " ] back";
  }
}
