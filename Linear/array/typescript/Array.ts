/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Array
 * Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.
 */

export class DynamicArray<T> {
  private data: (T | undefined)[];
  private _capacity: number;
  private _length: number;

  // Initializes the array with a given starting capacity.
  // Input:  initialCapacity (number, default 8)
  // Output: void
  constructor(initialCapacity: number = 8) {
    if (initialCapacity < 1) throw new RangeError("Capacity must be >= 1");
    this._capacity = initialCapacity;
    this._length = 0;
    this.data = new Array<T | undefined>(this._capacity).fill(undefined);
  }

  // Doubles the internal storage capacity when the array is full.
  // Input:  none
  // Output: void
  private resize(): void {
    const newCapacity = this._capacity * 2;
    const newData = new Array<T | undefined>(newCapacity).fill(undefined);
    for (let i = 0; i < this._length; i++) newData[i] = this.data[i];
    this.data = newData;
    this._capacity = newCapacity;
  }

  // Validates that an index falls within the current element range.
  // Input:  index (number)
  // Output: void (throws RangeError on violation)
  private checkBounds(index: number): void {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
  }

  // Appends a value to the end of the array, resizing if necessary.
  // Input:  value (T)
  // Output: void
  append(value: T): void {
    if (this._length === this._capacity) this.resize();
    this.data[this._length++] = value;
  }

  // Inserts a value at the specified index, shifting subsequent elements right.
  // Input:  index (number), value (T)
  // Output: void
  insert(index: number, value: T): void {
    if (index < 0 || index > this._length) throw new RangeError("Index out of bounds");
    if (this._length === this._capacity) this.resize();
    for (let i = this._length; i > index; i--) this.data[i] = this.data[i - 1];
    this.data[index] = value;
    this._length++;
  }

  // Removes and returns the element at the specified index, shifting remaining elements left.
  // Input:  index (number)
  // Output: T
  remove(index: number): T {
    this.checkBounds(index);
    const removed = this.data[index] as T;
    for (let i = index; i < this._length - 1; i++) this.data[i] = this.data[i + 1];
    this.data[--this._length] = undefined;
    return removed;
  }

  // Returns the element at the given index.
  // Input:  index (number)
  // Output: T
  get(index: number): T {
    this.checkBounds(index);
    return this.data[index] as T;
  }

  // Replaces the value at the specified index.
  // Input:  index (number), value (T)
  // Output: void
  set(index: number, value: T): void {
    this.checkBounds(index);
    this.data[index] = value;
  }

  // Returns the index of the first occurrence of a value, or -1 if not found.
  // Input:  value (T)
  // Output: number
  indexOf(value: T): number {
    for (let i = 0; i < this._length; i++)
      if (this.data[i] === value) return i;
    return -1;
  }

  // Resets length to zero without freeing capacity.
  // Input:  none
  // Output: void
  clear(): void {
    this.data.fill(undefined);
    this._length = 0;
  }

  // Returns the number of elements currently stored.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if no elements are stored.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns the current allocated capacity.
  // Input:  none
  // Output: number
  capacity(): number { return this._capacity; }

  // Returns a string representation of all stored elements.
  // Input:  none
  // Output: string
  toString(): string {
    const items: T[] = [];
    for (let i = 0; i < this._length; i++) items.push(this.data[i] as T);
    return "[ " + items.join(", ") + " ]";
  }
}
