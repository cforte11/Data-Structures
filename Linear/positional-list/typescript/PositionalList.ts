/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Positional List
 * Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.
 */

interface PLNode<T> {
  data: T | null;
  prev: PLNode<T> | null;
  next: PLNode<T> | null;
  container: PositionalList<T> | null;
}

export class PositionalList<T> {
  private header: PLNode<T>;
  private trailer: PLNode<T>;
  private _length: number = 0;

  // A lightweight handle wrapping an internal node reference.
  static Position = class<T> {
    readonly _node: PLNode<T>;
    readonly _container: PositionalList<T>;

    // Wraps a node reference with a back-reference to its owning list.
    // Input:  container (PositionalList<T>), node (PLNode<T>)
    // Output: void
    constructor(container: PositionalList<T>, node: PLNode<T>) {
      this._container = container;
      this._node = node;
    }

    // Returns the element stored at this position.
    // Input:  none
    // Output: T
    element(): T { return this._node.data as T; }

    // Returns true if this position refers to the same node as another.
    // Input:  other (Position<T>)
    // Output: boolean
    equals(other: InstanceType<typeof PositionalList.Position>): boolean {
      return this._node === other._node;
    }
  };

  // Constructs the list with sentinel header and trailer nodes.
  // Input:  none
  // Output: void
  constructor() {
    this.header  = { data: null, prev: null, next: null, container: this };
    this.trailer = { data: null, prev: null, next: null, container: this };
    this.header.next  = this.trailer;
    this.trailer.prev = this.header;
  }

  // Validates a position belongs to this list and has not been deleted.
  // Input:  p (Position<T>)
  // Output: PLNode<T>
  private validate(p: InstanceType<typeof PositionalList.Position>): PLNode<T> {
    if (p._container !== this) throw new Error("Position does not belong to this list");
    if (p._node.next === null) throw new Error("Position is no longer valid");
    return p._node as PLNode<T>;
  }

  // Returns a Position for a node, or null if the node is a sentinel.
  // Input:  node (PLNode<T>)
  // Output: Position<T> | null
  private makePosition(node: PLNode<T>): InstanceType<typeof PositionalList.Position> | null {
    if (node === this.header || node === this.trailer) return null;
    return new PositionalList.Position(this, node);
  }

  // Creates a new node between two existing nodes and returns its position.
  // Input:  value (T), before (PLNode<T>), after (PLNode<T>)
  // Output: Position<T>
  private insertBetween(value: T, before: PLNode<T>, after: PLNode<T>): InstanceType<typeof PositionalList.Position> {
    const node: PLNode<T> = { data: value, prev: before, next: after, container: this };
    before.next = node;
    after.prev = node;
    this._length++;
    return new PositionalList.Position(this, node);
  }

  // Returns the position of the first element, or null if empty.
  // Input:  none
  // Output: Position<T> | null
  first(): InstanceType<typeof PositionalList.Position> | null { return this.makePosition(this.header.next!); }

  // Returns the position of the last element, or null if empty.
  // Input:  none
  // Output: Position<T> | null
  last(): InstanceType<typeof PositionalList.Position> | null { return this.makePosition(this.trailer.prev!); }

  // Returns the position immediately before the given position.
  // Input:  p (Position<T>)
  // Output: Position<T> | null
  before(p: InstanceType<typeof PositionalList.Position>): InstanceType<typeof PositionalList.Position> | null {
    return this.makePosition(this.validate(p).prev!);
  }

  // Returns the position immediately after the given position.
  // Input:  p (Position<T>)
  // Output: Position<T> | null
  after(p: InstanceType<typeof PositionalList.Position>): InstanceType<typeof PositionalList.Position> | null {
    return this.makePosition(this.validate(p).next!);
  }

  // Inserts a new element at the front and returns its position.
  // Input:  value (T)
  // Output: Position<T>
  addFirst(value: T): InstanceType<typeof PositionalList.Position> {
    return this.insertBetween(value, this.header, this.header.next!);
  }

  // Inserts a new element at the back and returns its position.
  // Input:  value (T)
  // Output: Position<T>
  addLast(value: T): InstanceType<typeof PositionalList.Position> {
    return this.insertBetween(value, this.trailer.prev!, this.trailer);
  }

  // Inserts a new element immediately before the given position.
  // Input:  p (Position<T>), value (T)
  // Output: Position<T>
  addBefore(p: InstanceType<typeof PositionalList.Position>, value: T): InstanceType<typeof PositionalList.Position> {
    const node = this.validate(p);
    return this.insertBetween(value, node.prev!, node);
  }

  // Inserts a new element immediately after the given position.
  // Input:  p (Position<T>), value (T)
  // Output: Position<T>
  addAfter(p: InstanceType<typeof PositionalList.Position>, value: T): InstanceType<typeof PositionalList.Position> {
    const node = this.validate(p);
    return this.insertBetween(value, node, node.next!);
  }

  // Removes the element at the given position and returns its value.
  // Input:  p (Position<T>)
  // Output: T
  delete(p: InstanceType<typeof PositionalList.Position>): T {
    const node = this.validate(p);
    node.prev!.next = node.next;
    node.next!.prev = node.prev;
    this._length--;
    const val = node.data as T;
    node.prev = node.next = node.container = null;
    node.data = null;
    return val;
  }

  // Replaces the element at the given position and returns the old value.
  // Input:  p (Position<T>), value (T)
  // Output: T
  replace(p: InstanceType<typeof PositionalList.Position>, value: T): T {
    const node = this.validate(p);
    const old = node.data as T;
    node.data = value;
    return old;
  }

  // Removes all non-sentinel nodes and resets length to zero.
  // Input:  none
  // Output: void
  clear(): void {
    this.header.next  = this.trailer;
    this.trailer.prev = this.header;
    this._length = 0;
  }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Yields each element value from first to last.
  // Input:  none
  // Output: IterableIterator<T>
  *[Symbol.iterator](): IterableIterator<T> {
    let cursor = this.first();
    while (cursor !== null) {
      yield cursor.element() as T;
      cursor = this.after(cursor);
    }
  }

  // Returns a string of all element values from first to last.
  // Input:  none
  // Output: string
  toString(): string { return "[ " + [...this].join(", ") + " ]"; }
}
