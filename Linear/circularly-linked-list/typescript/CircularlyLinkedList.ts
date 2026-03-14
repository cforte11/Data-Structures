/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circularly Linked List
 * Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.
 */

interface CLLNode<T> {
  data: T;
  next: CLLNode<T>;
}

export class CircularlyLinkedList<T> {
  private tail: CLLNode<T> | null = null;
  private _length: number = 0;

  // Inserts a new value immediately after the tail, making it the new head.
  // Input:  value (T)
  // Output: void
  addFirst(value: T): void {
    const node = { data: value, next: null as unknown as CLLNode<T> };
    if (!this.tail) {
      node.next = node as CLLNode<T>;
      this.tail = node as CLLNode<T>;
    } else {
      node.next = this.tail.next;
      this.tail.next = node as CLLNode<T>;
    }
    this._length++;
  }

  // Appends a new value as the new tail of the circular list.
  // Input:  value (T)
  // Output: void
  addLast(value: T): void {
    this.addFirst(value);
    this.tail = this.tail!.next;
  }

  // Removes and returns the front (head) element.
  // Input:  none
  // Output: T
  removeFirst(): T {
    if (!this.tail) throw new RangeError("List is empty");
    const head = this.tail.next;
    const val = head.data;
    if (this.tail === head) this.tail = null;
    else this.tail.next = head.next;
    this._length--;
    return val;
  }

  // Advances the tail pointer one step forward, rotating the front to the back.
  // Input:  none
  // Output: void
  rotate(): void { if (this.tail) this.tail = this.tail.next; }

  // Returns the head element without removing it.
  // Input:  none
  // Output: T
  first(): T {
    if (!this.tail) throw new RangeError("List is empty");
    return this.tail.next.data;
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
    if (!this.tail) return false;
    let curr = this.tail.next;
    do {
      if (curr.data === value) return true;
      curr = curr.next;
    } while (curr !== this.tail.next);
    return false;
  }

  // Resets the list to empty by discarding all node references.
  // Input:  none
  // Output: void
  clear(): void { this.tail = null; this._length = 0; }

  // Returns the number of elements in the list.
  // Input:  none
  // Output: number
  size(): number { return this._length; }

  // Returns true if the list holds no elements.
  // Input:  none
  // Output: boolean
  isEmpty(): boolean { return this._length === 0; }

  // Returns a string of all elements starting from the head, noting the circular wrap.
  // Input:  none
  // Output: string
  toString(): string {
    if (!this.tail) return "(empty)";
    const parts: string[] = [];
    let curr = this.tail.next;
    do { parts.push(String(curr.data)); curr = curr.next; } while (curr !== this.tail.next);
    return parts.join(" -> ") + " -> (head)";
  }
}
