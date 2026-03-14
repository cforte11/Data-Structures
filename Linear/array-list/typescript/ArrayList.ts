/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: ArrayList
 * Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.
 */

export class ArrayList<T> {
  private data: (T | undefined)[];
  private _capacity: number;
  private _length: number = 0;

  // Initializes the list with an optional starting capacity.
  // Input:  initialCapacity (number, default 8)
  // Output: void
  constructor(initialCapacity: number = 8) {
    if (initialCapacity < 1) throw new RangeError("Capacity must be >= 1");
    this._capacity = initialCapacity;
    this.data = new Array<T | undefined>(this._capacity).fill(undefined);
  }

  // Doubles the internal array capacity and migrates all elements.
  // Input:  none
  // Output: void
  private resize(): void {
    const newCap = this._capacity * 2;
    const newData = new Array<T | undefined>(newCap).fill(undefined);
    for (let i = 0; i < this._length; i++) newData[i] = this.data[i];
    this.data = newData;
    this._capacity = newCap;
  }

  // Validates that an index falls within the current element range.
  // Input:  index (number)
  // Output: void (throws RangeError on violation)
  private checkBounds(index: number): void {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
  }

  // Appends to the end or inserts at the given index, shifting elements right.
  // Input:  value (T), index (number | undefined)
  // Output: void
  add(value: T, index?: number): void {
    if (index === undefined) {
      if (this._length === this._capacity) this.resize();
      this.data[this._length++] = value;
    } else {
      if (index < 0 || index > this._length) throw new RangeError("Index out of bounds");
      if (this._length === this._capacity) this.resize();
      for (let i = this._length; i > index; i--) this.data[i] = this.data[i - 1];
      this.data[index] = value;
      this._length++;
    }
  }

  // Removes and returns the element at the given index, shifting elements left.
  // Input:  index (number)
  // Output: T
  remove(index: number): T {
    this.checkBounds(index);
    const val = this.data[index] as T;
    for (let i = index; i < this._length - 1; i++) this.data[i] = this.data[i + 1];
    this.data[--this._length] = undefined;
    return val;
  }

  // Returns the element at the given index.
  // Input:  index (number)
  // Output: T
  get(index: number): T {
    this.checkBounds(index);
    return this.data[index] as T;
  }

  // Replaces the element at the given index with a new value.
  // Input:  index (number), value (T)
  // Output: void
  set(index: number, value: T): void {
    this.checkBounds(index);
    this.data[index] = value;
  }

  // Returns the index of the first matching element, or -1 if absent.
  // Input:  value (T)
  // Output: number
  indexOf(value: T): number {
    for (let i = 0; i < this._length; i++) if (this.data[i] === value) return i;
    return -1;
  }

  // Returns true if the list contains the given value.
  // Input:  value (T)
  // Output: boolean
  contains(value: T): boolean { return this.indexOf(value) !== -1; }

  // Resets length to zero, retaining allocated capacity.
  // Input:  none
  // Output: void
  clear(): void { this.data.fill(undefined); this._length = 0; }

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
    const items: string[] = [];
    for (let i = 0; i < this._length; i++) items.push(String(this.data[i]));
    return "[ " + items.join(", ") + " ]";
  }
}
