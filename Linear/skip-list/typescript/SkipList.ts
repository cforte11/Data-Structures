/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Skip List
 * Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.
 */

const MAX_LEVEL = 16;
const PROB = 0.5;

interface SkipNode<T> {
  key: T | null;
  forward: (SkipNode<T> | null)[];
}

export class SkipList<T> {
  private header: SkipNode<T>;
  private currentLevel: number = 0;
  private _length: number = 0;

  // Initializes the skip list with a sentinel header and level tracking.
  // Input:  none
  // Output: void
  constructor() {
    this.header = { key: null, forward: new Array<SkipNode<T> | null>(MAX_LEVEL).fill(null) };
  }

  // Generates a random level using geometric distribution.
  // Input:  none
  // Output: number (level between 0 and MAX_LEVEL-1)
  private randomLevel(): number {
    let level = 0;
    while (Math.random() < PROB && level < MAX_LEVEL - 1) level++;
    return level;
  }

  // Searches for a key and returns true if it exists in the list.
  // Input:  key (T)
  // Output: boolean
  contains(key: T): boolean {
    let curr: SkipNode<T> = this.header;
    for (let i = this.currentLevel; i >= 0; i--)
      while (curr.forward[i] && (curr.forward[i]!.key as T) < key) curr = curr.forward[i]!;
    curr = curr.forward[0]!;
    return curr !== null && curr.key === key;
  }

  // Inserts a key into the skip list at the correct sorted position.
  // Input:  key (T)
  // Output: void
  insert(key: T): void {
    const update: (SkipNode<T> | null)[] = new Array(MAX_LEVEL).fill(null);
    let curr: SkipNode<T> = this.header;
    for (let i = this.currentLevel; i >= 0; i--) {
      while (curr.forward[i] && (curr.forward[i]!.key as T) < key) curr = curr.forward[i]!;
      update[i] = curr;
    }
    const next = curr.forward[0];
    if (next && next.key === key) return;
    const newLevel = this.randomLevel();
    if (newLevel > this.currentLevel) {
      for (let i = this.currentLevel + 1; i <= newLevel; i++) update[i] = this.header;
      this.currentLevel = newLevel;
    }
    const node: SkipNode<T> = { key, forward: new Array<SkipNode<T> | null>(newLevel + 1).fill(null) };
    for (let i = 0; i <= newLevel; i++) {
      node.forward[i] = update[i]!.forward[i];
      update[i]!.forward[i] = node;
    }
    this._length++;
  }

  // Removes the node with the given key if it exists.
  // Input:  key (T)
  // Output: void
  remove(key: T): void {
    const update: (SkipNode<T> | null)[] = new Array(MAX_LEVEL).fill(null);
    let curr: SkipNode<T> = this.header;
    for (let i = this.currentLevel; i >= 0; i--) {
      while (curr.forward[i] && (curr.forward[i]!.key as T) < key) curr = curr.forward[i]!;
      update[i] = curr;
    }
    const target = curr.forward[0];
    if (!target || target.key !== key) return;
    for (let i = 0; i <= this.currentLevel; i++) {
      if (update[i]!.forward[i] !== target) break;
      update[i]!.forward[i] = target.forward[i];
    }
    while (this.currentLevel > 0 && !this.header.forward[this.currentLevel]) this.currentLevel--;
    this._length--;
  }

  // Resets all header forward pointers and restores initial state.
  // Input:  none
  // Output: void
  clear(): void {
    this.header.forward = new Array<SkipNode<T> | null>(MAX_LEVEL).fill(null);
    this.currentLevel = 0;
    this._length = 0;
  }

  // Returns the number of elements stored in the skip list.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the skip list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns a string of all keys at level 0 in sorted order.
  // Input:  none
  // Output: string
  toString(): string {
    const parts: string[] = [];
    let curr = this.header.forward[0];
    while (curr) { parts.push(String(curr.key)); curr = curr.forward[0]; }
    return "[ " + parts.join(", ") + " ]";
  }
}
