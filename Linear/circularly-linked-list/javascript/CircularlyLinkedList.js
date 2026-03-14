/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circularly Linked List
 * Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.
 */

class CircularlyLinkedList {
  #tail;
  #length;

  // Initializes an empty circular list with no nodes.
  // Input:  none
  // Output: void
  constructor() {
    this.#tail = null;
    this.#length = 0;
  }

  // Inserts a new value immediately after the tail, making it the new head.
  // Input:  value (*)
  // Output: void
  addFirst(value) {
    const node = { data: value, next: null };
    if (!this.#tail) {
      node.next = node;
      this.#tail = node;
    } else {
      node.next = this.#tail.next;
      this.#tail.next = node;
    }
    this.#length++;
  }

  // Appends a new value as the new tail of the circular list.
  // Input:  value (*)
  // Output: void
  addLast(value) {
    this.addFirst(value);
    this.#tail = this.#tail.next;
  }

  // Removes and returns the front (head) element.
  // Input:  none
  // Output: *
  removeFirst() {
    if (!this.#tail) throw new RangeError("List is empty");
    const head = this.#tail.next;
    const val = head.data;
    if (this.#tail === head) this.#tail = null;
    else this.#tail.next = head.next;
    this.#length--;
    return val;
  }

  // Advances the tail pointer one step forward, rotating the front to the back.
  // Input:  none
  // Output: void
  rotate() { if (this.#tail) this.#tail = this.#tail.next; }

  // Returns the head element without removing it.
  // Input:  none
  // Output: *
  first() {
    if (!this.#tail) throw new RangeError("List is empty");
    return this.#tail.next.data;
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
    if (!this.#tail) return false;
    let curr = this.#tail.next;
    do {
      if (curr.data === value) return true;
      curr = curr.next;
    } while (curr !== this.#tail.next);
    return false;
  }

  // Resets the list to empty by discarding all node references.
  // Input:  none
  // Output: void
  clear() { this.#tail = null; this.#length = 0; }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Returns a string of all elements starting from the head, noting the circular wrap.
  // Input:  none
  // Output: string
  toString() {
    if (!this.#tail) return "(empty)";
    const parts = [];
    let curr = this.#tail.next;
    do { parts.push(curr.data); curr = curr.next; } while (curr !== this.#tail.next);
    return parts.join(" -> ") + " -> (head)";
  }
}

module.exports = CircularlyLinkedList;
