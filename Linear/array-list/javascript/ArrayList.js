/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: ArrayList
 * Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.
 */

class ArrayList {
  #data;
  #capacity;
  #length;

  // Initializes the list with an optional starting capacity.
  // Input:  initialCapacity (number, default 8)
  // Output: void
  constructor(initialCapacity = 8) {
    if (initialCapacity < 1) throw new RangeError("Capacity must be >= 1");
    this.#capacity = initialCapacity;
    this.#length = 0;
    this.#data = new Array(this.#capacity).fill(undefined);
  }

  // Doubles the internal array capacity and migrates all elements.
  // Input:  none
  // Output: void
  #resize() {
    const newCap = this.#capacity * 2;
    const newData = new Array(newCap).fill(undefined);
    for (let i = 0; i < this.#length; i++) newData[i] = this.#data[i];
    this.#data = newData;
    this.#capacity = newCap;
  }

  // Validates that an index falls within the current element range.
  // Input:  index (number)
  // Output: void (throws RangeError on violation)
  #checkBounds(index) {
    if (index < 0 || index >= this.#length) throw new RangeError("Index out of bounds");
  }

  // Appends to the end or inserts at the given index, shifting elements right.
  // Input:  value (*), index (number | undefined)
  // Output: void
  add(value, index = undefined) {
    if (index === undefined) {
      if (this.#length === this.#capacity) this.#resize();
      this.#data[this.#length++] = value;
    } else {
      if (index < 0 || index > this.#length) throw new RangeError("Index out of bounds");
      if (this.#length === this.#capacity) this.#resize();
      for (let i = this.#length; i > index; i--) this.#data[i] = this.#data[i - 1];
      this.#data[index] = value;
      this.#length++;
    }
  }

  // Removes and returns the element at the given index, shifting elements left.
  // Input:  index (number)
  // Output: *
  remove(index) {
    this.#checkBounds(index);
    const val = this.#data[index];
    for (let i = index; i < this.#length - 1; i++) this.#data[i] = this.#data[i + 1];
    this.#data[--this.#length] = undefined;
    return val;
  }

  // Returns the element at the given index.
  // Input:  index (number)
  // Output: *
  get(index) { this.#checkBounds(index); return this.#data[index]; }

  // Replaces the element at the given index with a new value.
  // Input:  index (number), value (*)
  // Output: void
  set(index, value) { this.#checkBounds(index); this.#data[index] = value; }

  // Returns the index of the first matching element, or -1 if absent.
  // Input:  value (*)
  // Output: number
  indexOf(value) {
    for (let i = 0; i < this.#length; i++) if (this.#data[i] === value) return i;
    return -1;
  }

  // Returns true if the list contains the given value.
  // Input:  value (*)
  // Output: boolean
  contains(value) { return this.indexOf(value) !== -1; }

  // Resets length to zero, retaining allocated capacity.
  // Input:  none
  // Output: void
  clear() { this.#data.fill(undefined); this.#length = 0; }

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

module.exports = ArrayList;
