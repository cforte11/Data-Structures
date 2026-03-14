/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Positional List
 * Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.
 */

class PositionalList {
  #header;
  #trailer;
  #length;

  // A lightweight handle wrapping an internal node reference.
  static Position = class {
    #node;
    #container;

    // Wraps a node reference with a back-reference to its owning list.
    // Input:  container (PositionalList), node (object)
    // Output: void
    constructor(container, node) {
      this.#container = container;
      this.#node = node;
    }

    // Returns the element stored at this position.
    // Input:  none
    // Output: *
    element() { return this.#node.data; }

    // Returns true if this position refers to the same node as another.
    // Input:  other (Position)
    // Output: boolean
    equals(other) { return this.#node === other._node; }

    // Exposes the internal node for list-internal use only.
    // Input:  none
    // Output: object
    get _node() { return this.#node; }

    // Exposes the container reference for validation.
    // Input:  none
    // Output: PositionalList
    get _container() { return this.#container; }
  };

  // Constructs the list with sentinel header and trailer nodes.
  // Input:  none
  // Output: void
  constructor() {
    this.#header  = { data: null, prev: null, next: null };
    this.#trailer = { data: null, prev: null, next: null };
    this.#header.next  = this.#trailer;
    this.#trailer.prev = this.#header;
    this.#length = 0;
  }

  // Validates a position belongs to this list and has not been deleted.
  // Input:  p (Position)
  // Output: object (internal node)
  #validate(p) {
    if (!(p instanceof PositionalList.Position)) throw new TypeError("Must be a Position");
    if (p._container !== this) throw new Error("Position does not belong to this list");
    if (p._node.next === null) throw new Error("Position is no longer valid");
    return p._node;
  }

  // Returns a Position for a node, or null if the node is a sentinel.
  // Input:  node (object)
  // Output: Position | null
  #makePosition(node) {
    if (node === this.#header || node === this.#trailer) return null;
    return new PositionalList.Position(this, node);
  }

  // Creates a new node between two existing nodes and returns its position.
  // Input:  value (*), before (node), after (node)
  // Output: Position
  #insertBetween(value, before, after) {
    const node = { data: value, prev: before, next: after };
    before.next = node;
    after.prev = node;
    this.#length++;
    return new PositionalList.Position(this, node);
  }

  // Returns the position of the first element, or null if empty.
  // Input:  none
  // Output: Position | null
  first() { return this.#makePosition(this.#header.next); }

  // Returns the position of the last element, or null if empty.
  // Input:  none
  // Output: Position | null
  last() { return this.#makePosition(this.#trailer.prev); }

  // Returns the position immediately before the given position.
  // Input:  p (Position)
  // Output: Position | null
  before(p) { return this.#makePosition(this.#validate(p).prev); }

  // Returns the position immediately after the given position.
  // Input:  p (Position)
  // Output: Position | null
  after(p) { return this.#makePosition(this.#validate(p).next); }

  // Inserts a new element at the front and returns its position.
  // Input:  value (*)
  // Output: Position
  addFirst(value) { return this.#insertBetween(value, this.#header, this.#header.next); }

  // Inserts a new element at the back and returns its position.
  // Input:  value (*)
  // Output: Position
  addLast(value) { return this.#insertBetween(value, this.#trailer.prev, this.#trailer); }

  // Inserts a new element immediately before the given position.
  // Input:  p (Position), value (*)
  // Output: Position
  addBefore(p, value) {
    const node = this.#validate(p);
    return this.#insertBetween(value, node.prev, node);
  }

  // Inserts a new element immediately after the given position.
  // Input:  p (Position), value (*)
  // Output: Position
  addAfter(p, value) {
    const node = this.#validate(p);
    return this.#insertBetween(value, node, node.next);
  }

  // Removes the element at the given position and returns its value.
  // Input:  p (Position)
  // Output: *
  delete(p) {
    const node = this.#validate(p);
    node.prev.next = node.next;
    node.next.prev = node.prev;
    this.#length--;
    const val = node.data;
    node.prev = node.next = node.data = null;
    return val;
  }

  // Replaces the element at the given position and returns the old value.
  // Input:  p (Position), value (*)
  // Output: *
  replace(p, value) {
    const node = this.#validate(p);
    const old = node.data;
    node.data = value;
    return old;
  }

  // Removes all non-sentinel nodes and resets length to zero.
  // Input:  none
  // Output: void
  clear() {
    this.#header.next  = this.#trailer;
    this.#trailer.prev = this.#header;
    this.#length = 0;
  }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size() { return this.#length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty() { return this.#length === 0; }

  // Yields each element value from first to last.
  // Input:  none
  // Output: iterator of *
  *[Symbol.iterator]() {
    let cursor = this.first();
    while (cursor !== null) {
      yield cursor.element();
      cursor = this.after(cursor);
    }
  }

  // Returns a string of all element values from first to last.
  // Input:  none
  // Output: string
  toString() { return "[ " + [...this].join(", ") + " ]"; }
}

module.exports = PositionalList;
