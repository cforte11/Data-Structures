/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Array
 * Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.
 */

class Array {
  #data;
  #capacity;
  #length;

  // Initializes the array with a given starting capacity.
  // Input:  initialCapacity (number, default 8)
  // Output: void
  constructor(initialCapacity = 8) {
    if (initialCapacity < 1) throw new RangeError("Capacity must be >= 1");
    this.#capacity = initialCapacity;
    this.#length = 0;
    this.#data = new Array(this.#capacity).fill(undefined);
  }

  // Doubles the internal storage capacity when the array is full.
  // Input:  none
  // Output: void
  #resize() {
    const newCapacity = this.#capacity * 2;
    const newData = new Array(newCapacity).fill(undefined);
    for (let i = 0; i < this.#length; i++) newData[i] = this.#data[i];
    this.#data = newData;
    this.#capacity = newCapacity;
  }

  // Validates that an index falls within the current element range.
  // Input:  index (number)
  // Output: void (throws RangeError on violation)
  #checkBounds(index) {
    if (index < 0 || index >= this.#length) throw new RangeError("Index out of bounds");
  }

  // Appends a value to the end of the array, resizing if necessary.
  // Input:  value (*)
  // Output: void
  append(value) {
    if (this.#length === this.#capacity) this.#resize();
    this.#data[this.#length++] = value;
  }

  // Inserts a value at the specified index, shifting subsequent elements right.
  // Input:  index (number), value (*)
  // Output: void
  insert(index, value) {
    if (index < 0 || index > this.#length) throw new RangeError("Index out of bounds");
    if (this.#length === this.#capacity) this.#resize();
    for (let i = this.#length; i > index; i--) this.#data[i] = this.#data[i - 1];
    this.#data[index] = value;
    this.#length++;
  }

  // Removes and returns the element at the specified index, shifting remaining elements left.
  // Input:  index (number)
  // Output: * (removed element)
  remove(index) {
    this.#checkBounds(index);
    const removed = this.#data[index];
    for (let i = index; i < this.#length - 1; i++) this.#data[i] = this.#data[i + 1];
    this.#data[--this.#length] = undefined;
    return removed;
  }

  // Returns the element at the given index.
  // Input:  index (number)
  // Output: *
  get(index) {
    this.#checkBounds(index);
    return this.#data[index];
  }

  // Replaces the value at the specified index.
  // Input:  index (number), value (*)
  // Output: void
  set(index, value) {
    this.#checkBounds(index);
    this.#data[index] = value;
  }

  // Returns the index of the first occurrence of a value, or -1 if not found.
  // Input:  value (*)
  // Output: number
  indexOf(value) {
    for (let i = 0; i < this.#length; i++)
      if (this.#data[i] === value) return i;
    return -1;
  }

  // Resets length to zero without freeing capacity.
  // Input:  none
  // Output: void
  clear() {
    this.#data.fill(undefined);
    this.#length = 0;
  }

  // Returns the number of elements currently stored.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if no elements are stored.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns the current allocated capacity.
  // Input:  none
  // Output: number
  capacity() { return this.#capacity; }

  // Returns a string representation of all stored elements.
  // Input:  none
  // Output: string
  toString() {
    const items = [];
    for (let i = 0; i < this.#length; i++) items.push(this.#data[i]);
    return "[ " + items.join(", ") + " ]";
  }
}

module.exports = Array;
