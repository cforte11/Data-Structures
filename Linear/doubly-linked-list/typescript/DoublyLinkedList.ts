/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Doubly Linked List
 * Description: Personal take on a bidirectionally linked linear structure enabling O(1) insertion and removal at both ends.
 */

interface DLLNode<T> {
  data: T;
  prev: DLLNode<T> | null;
  next: DLLNode<T> | null;
}

export class DoublyLinkedList<T> {
  private head: DLLNode<T> | null = null;
  private tail: DLLNode<T> | null = null;
  private _length: number = 0;

  // Stitches a new node between two existing adjacent nodes.
  // Input:  before (DLLNode<T> | null), after (DLLNode<T> | null), value (T)
  // Output: void
  private insertBetween(before: DLLNode<T> | null, after: DLLNode<T> | null, value: T): void {
    const node: DLLNode<T> = { data: value, prev: before, next: after };
    if (before) before.next = node; else this.head = node;
    if (after) after.prev = node; else this.tail = node;
    this._length++;
  }

  // Detaches a node from its neighbors and returns its value.
  // Input:  node (DLLNode<T>)
  // Output: T
  private unlink(node: DLLNode<T>): T {
    if (node.prev) node.prev.next = node.next; else this.head = node.next;
    if (node.next) node.next.prev = node.prev; else this.tail = node.prev;
    this._length--;
    return node.data;
  }

  // Prepends a new value before the current head.
  // Input:  value (T)
  // Output: void
  addFirst(value: T): void { this.insertBetween(null, this.head, value); }

  // Appends a new value after the current tail.
  // Input:  value (T)
  // Output: void
  addLast(value: T): void { this.insertBetween(this.tail, null, value); }

  // Inserts a new value at the specified zero-based index.
  // Input:  index (number), value (T)
  // Output: void
  addAt(index: number, value: T): void {
    if (index < 0 || index > this._length) throw new RangeError("Index out of bounds");
    if (index === 0) return this.addFirst(value);
    if (index === this._length) return this.addLast(value);
    let curr = this.head!;
    for (let i = 0; i < index; i++) curr = curr.next!;
    this.insertBetween(curr.prev, curr, value);
  }

  // Removes and returns the first element.
  // Input:  none
  // Output: T
  removeFirst(): T {
    if (!this.head) throw new RangeError("List is empty");
    return this.unlink(this.head);
  }

  // Removes and returns the last element.
  // Input:  none
  // Output: T
  removeLast(): T {
    if (!this.tail) throw new RangeError("List is empty");
    return this.unlink(this.tail);
  }

  // Removes and returns the element at the specified index, traversing from the nearer end.
  // Input:  index (number)
  // Output: T
  removeAt(index: number): T {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
    let curr: DLLNode<T>;
    if (index < this._length / 2) {
      curr = this.head!;
      for (let i = 0; i < index; i++) curr = curr.next!;
    } else {
      curr = this.tail!;
      for (let i = this._length - 1; i > index; i--) curr = curr.prev!;
    }
    return this.unlink(curr);
  }

  // Returns the element at the given index, traversing from the nearer end.
  // Input:  index (number)
  // Output: T
  get(index: number): T {
    if (index < 0 || index >= this._length) throw new RangeError("Index out of bounds");
    let curr: DLLNode<T>;
    if (index < this._length / 2) {
      curr = this.head!;
      for (let i = 0; i < index; i++) curr = curr.next!;
    } else {
      curr = this.tail!;
      for (let i = this._length - 1; i > index; i--) curr = curr.prev!;
    }
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
    while (curr) { if (curr.data === value) return true; curr = curr.next; }
    return false;
  }

  // Reverses the list in place by swapping each node's prev and next references.
  // Input:  none
  // Output: void
  reverse(): void {
    let curr: DLLNode<T> | null = this.head;
    [this.head, this.tail] = [this.tail, this.head];
    while (curr) {
      [curr.prev, curr.next] = [curr.next, curr.prev];
      curr = curr.prev;
    }
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

  // Returns a bidirectional arrow-notation string of all elements.
  // Input:  none
  // Output: string
  toString(): string {
    const parts: string[] = [];
    let curr = this.head;
    while (curr) { parts.push(String(curr.data)); curr = curr.next; }
    return "null <-> " + parts.join(" <-> ") + " <-> null";
  }
}
