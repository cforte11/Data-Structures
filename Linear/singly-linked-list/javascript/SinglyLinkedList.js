/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Singly Linked List
 * Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.
 */

class SinglyLinkedList {
  #head;
  #tail;
  #length;

  // Initializes an empty list with no nodes.
  // Input:  none
  // Output: void
  constructor() {
    this.#head = null;
    this.#tail = null;
    this.#length = 0;
  }

  // Prepends a new value before the current head.
  // Input:  value (*)
  // Output: void
  addFirst(value) {
    const node = { data: value, next: this.#head };
    this.#head = node;
    if (!this.#tail) this.#tail = this.#head;
    this.#length++;
  }

  // Appends a new value after the current tail.
  // Input:  value (*)
  // Output: void
  addLast(value) {
    const node = { data: value, next: null };
    if (this.#tail) this.#tail.next = node;
    this.#tail = node;
    if (!this.#head) this.#head = this.#tail;
    this.#length++;
  }

  // Inserts a new value at the specified zero-based index.
  // Input:  index (number), value (*)
  // Output: void
  addAt(index, value) {
    if (index < 0 || index > this.#length) throw new RangeError("Index out of bounds");
    if (index === 0) return this.addFirst(value);
    if (index === this.#length) return this.addLast(value);
    let prev = this.#head;
    for (let i = 0; i < index - 1; i++) prev = prev.next;
    const node = { data: value, next: prev.next };
    prev.next = node;
    this.#length++;
  }

  // Removes and returns the first element.
  // Input:  none
  // Output: *
  removeFirst() {
    if (!this.#head) throw new RangeError("List is empty");
    const val = this.#head.data;
    this.#head = this.#head.next;
    if (!this.#head) this.#tail = null;
    this.#length--;
    return val;
  }

  // Traverses to the second-to-last node and removes the tail.
  // Input:  none
  // Output: *
  removeLast() {
    if (!this.#head) throw new RangeError("List is empty");
    if (this.#head === this.#tail) return this.removeFirst();
    let prev = this.#head;
    while (prev.next !== this.#tail) prev = prev.next;
    const val = this.#tail.data;
    this.#tail = prev;
    this.#tail.next = null;
    this.#length--;
    return val;
  }

  // Removes and returns the element at the specified index.
  // Input:  index (number)
  // Output: *
  removeAt(index) {
    if (index < 0 || index >= this.#length) throw new RangeError("Index out of bounds");
    if (index === 0) return this.removeFirst();
    if (index === this.#length - 1) return this.removeLast();
    let prev = this.#head;
    for (let i = 0; i < index - 1; i++) prev = prev.next;
    const target = prev.next;
    prev.next = target.next;
    this.#length--;
    return target.data;
  }

  // Returns the element at the specified index by linear traversal.
  // Input:  index (number)
  // Output: *
  get(index) {
    if (index < 0 || index >= this.#length) throw new RangeError("Index out of bounds");
    let curr = this.#head;
    for (let i = 0; i < index; i++) curr = curr.next;
    return curr.data;
  }

  // Returns the head element without removing it.
  // Input:  none
  // Output: *
  first() {
    if (!this.#head) throw new RangeError("List is empty");
    return this.#head.data;
  }

  // Returns the tail element without removing it.
  // Input:  none
  // Output: *
  last() {
    if (!this.#tail) throw new RangeError("List is empty");
    return this.#tail.data;
  }

  // Returns true if the list contains the given value.
  // Input:  value (*)
  // Output: boolean
  contains(value) {
    let curr = this.#head;
    while (curr) {
      if (curr.data === value) return true;
      curr = curr.next;
    }
    return false;
  }

  // Reverses all nodes in place using iterative pointer reassignment.
  // Input:  none
  // Output: void
  reverse() {
    let prev = null, curr = this.#head;
    this.#tail = this.#head;
    while (curr) {
      const next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    this.#head = prev;
  }

  // Resets the list to empty by unlinking all nodes.
  // Input:  none
  // Output: void
  clear() { this.#head = null; this.#tail = null; this.#length = 0; }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns a string of elements separated by arrows.
  // Input:  none
  // Output: string
  toString() {
    const parts = [];
    let curr = this.#head;
    while (curr) { parts.push(curr.data); curr = curr.next; }
    return parts.join(" -> ") + " -> null";
  }
}

module.exports = SinglyLinkedList;
