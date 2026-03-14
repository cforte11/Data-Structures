/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Singly Linked List
 * Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.
 */

interface SLLNode<T> {
  data: T;
  next: SLLNode<T> | null;
}

export class SinglyLinkedList<T> {
  private head: SLLNode<T> | null = null;
  private tail: SLLNode<T> | null = null;
  private _length: number = 0;

  // Prepends a new value before the current head.
  // Input:  value (T)
  // Output: void
  addFirst(value: T): void {
    const node: SLLNode<T> = { data: value, next: this.head };
    this.head = node;
    if (!this.tail) this.tail = this.head;
    this._length++;
  }

  // Appends a new value after the current tail.
  // Input:  value (T)
  // Output: void
  addLast(value: T): void {
    const node: SLLNode<T> = { data: value, next: null };
    if (this.tail) this.tail.next = node;
    this.tail = node;
    if (!this.head) this.head = this.tail;
    this._length++;
  }

  // Inserts a new value at the specified zero-based index.
  // Input:  index (number), value (T)
  // Output: void
  addAt(index: number, value: T): void {
    if (index < 0 || index > this._length) throw new RangeError("Index out of bounds");
    if (index === 0) return this.addFirst(value);
    if (index === this._length) return this.addLast(value);
    let prev = this.head!;
    for (let i = 0; i < index - 1; i++) prev = prev.next!;
    const node: SLLNode<T> = { data: value, next: prev.next };
    prev.next = node;
    this._length++;
  }

  // Removes and returns the first element.
  // Input:  none
  // Output: T
  removeFirst(): T {
    if (!this.head) throw new RangeError("List is empty");
    const val = this.head.data;
    this.head = this.head.next;
    if (!this.head) this.tail = null;
    this._length--;
    return val;
  }

  // Traverses to the second-to-last node and removes the tail.
  // Input:  none
  // Output: T
  removeLast(): T {
    if (!this.head) throw new RangeError("List is empty");
    if (this.head === this.tail) return this.removeFirst();
    let prev = this.head;
    while (prev.next !== this.tail) prev = prev.next!;
    const val = this.tail!.data;
    this.tail = prev;
    this.tail.next = null;
    this._length--;
    return val;
  }

  // Removes and returns the element at the specified index.
  // Input:  index (number)
  // Output: T
  removeAt(index: number): T {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
    if (index === 0) return this.removeFirst();
    if (index === this._length - 1) return this.removeLast();
    let prev = this.head!;
    for (let i = 0; i < index - 1; i++) prev = prev.next!;
    const target = prev.next!;
    prev.next = target.next;
    this._length--;
    return target.data;
  }

  // Returns the element at the specified index by linear traversal.
  // Input:  index (number)
  // Output: T
  get(index: number): T {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
    let curr = this.head!;
    for (let i = 0; i < index; i++) curr = curr.next!;
    return curr.data;
  }

  // Returns the head element without removing it.
  // Input:  none
  // Output: T
  first(): T {
    if (!this.head) throw new RangeError("List is empty");
    return this.head.data;
  }

  // Returns the tail element without removing it.
  // Input:  none
  // Output: T
  last(): T {
    if (!this.tail) throw new RangeError("List is empty");
    return this.tail.data;
  }

  // Returns true if the list contains the given value.
  // Input:  value (T)
  // Output: boolean
  contains(value: T): boolean {
    let curr = this.head;
    while (curr) {
      if (curr.data === value) return true;
      curr = curr.next;
    }
    return false;
  }

  // Reverses all nodes in place using iterative pointer reassignment.
  // Input:  none
  // Output: void
  reverse(): void {
    let prev: SLLNode<T> | null = null;
    let curr: SLLNode<T> | null = this.head;
    this.tail = this.head;
    while (curr) {
      const next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    this.head = prev;
  }

  // Resets the list to empty by unlinking all nodes.
  // Input:  none
  // Output: void
  clear(): void { this.head = null; this.tail = null; this._length = 0; }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns a string of elements separated by arrows.
  // Input:  none
  // Output: string
  toString(): string {
    const parts: string[] = [];
    let curr = this.head;
    while (curr) { parts.push(String(curr.data)); curr = curr.next; }
    return parts.join(" -> ") + " -> null";
  }
}
