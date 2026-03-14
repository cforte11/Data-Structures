# Author: Christopher Forte
# Date: March 14, 2026
# Title: Doubly Linked List
# Description: Personal take on a bidirectionally linked linear structure enabling O(1) insertion and removal at both ends.

from typing import TypeVar, Generic, Optional

T = TypeVar("T")


class DoublyLinkedList(Generic[T]):

    class _Node:
        __slots__ = ("data", "prev", "next")

        def __init__(self, data: object) -> None:
            self.data = data
            self.prev: Optional["DoublyLinkedList._Node"] = None
            self.next: Optional["DoublyLinkedList._Node"] = None

    def __init__(self) -> None:
        # Initializes an empty doubly linked list.
        # Input:  None
        # Output: None
        self._head: Optional[DoublyLinkedList._Node] = None
        self._tail: Optional[DoublyLinkedList._Node] = None
        self._length: int = 0

    def _insert_between(self, before: Optional["DoublyLinkedList._Node"],
                        after: Optional["DoublyLinkedList._Node"], value: T) -> None:
        # Stitches a new node between two existing adjacent nodes.
        # Input:  before (_Node | None), after (_Node | None), value (T)
        # Output: None
        node = self._Node(value)
        node.prev = before
        node.next = after
        if before:
            before.next = node
        else:
            self._head = node
        if after:
            after.prev = node
        else:
            self._tail = node
        self._length += 1

    def _unlink(self, node: "_Node") -> T:
        # Detaches a node from its neighbors and returns its value.
        # Input:  node (_Node)
        # Output: T
        if node.prev:
            node.prev.next = node.next
        else:
            self._head = node.next
        if node.next:
            node.next.prev = node.prev
        else:
            self._tail = node.prev
        self._length -= 1
        return node.data

    def add_first(self, value: T) -> None:
        # Prepends a new value before the current head.
        # Input:  value (T)
        # Output: None
        self._insert_between(None, self._head, value)

    def add_last(self, value: T) -> None:
        # Appends a new value after the current tail.
        # Input:  value (T)
        # Output: None
        self._insert_between(self._tail, None, value)

    def add_at(self, index: int, value: T) -> None:
        # Inserts a new value at the specified zero-based index.
        # Input:  index (int), value (T)
        # Output: None
        if index < 0 or index > self._length:
            raise IndexError("Index out of bounds")
        if index == 0:
            self.add_first(value)
            return
        if index == self._length:
            self.add_last(value)
            return
        curr = self._head
        for _ in range(index):
            curr = curr.next
        self._insert_between(curr.prev, curr, value)

    def remove_first(self) -> T:
        # Removes and returns the first element.
        # Input:  None
        # Output: T
        if not self._head:
            raise IndexError("List is empty")
        return self._unlink(self._head)

    def remove_last(self) -> T:
        # Removes and returns the last element.
        # Input:  None
        # Output: T
        if not self._tail:
            raise IndexError("List is empty")
        return self._unlink(self._tail)

    def remove_at(self, index: int) -> T:
        # Removes and returns the element at the specified index, traversing from the nearer end.
        # Input:  index (int)
        # Output: T
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")
        if index < self._length // 2:
            curr = self._head
            for _ in range(index):
                curr = curr.next
        else:
            curr = self._tail
            for _ in range(self._length - 1, index, -1):
                curr = curr.prev
        return self._unlink(curr)

    def get(self, index: int) -> T:
        # Returns the element at the given index, traversing from the nearer end.
        # Input:  index (int)
        # Output: T
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")
        if index < self._length // 2:
            curr = self._head
            for _ in range(index):
                curr = curr.next
        else:
            curr = self._tail
            for _ in range(self._length - 1, index, -1):
                curr = curr.prev
        return curr.data

    def first(self) -> T:
        # Returns the head element without removing it.
        # Input:  None
        # Output: T
        if not self._head:
            raise IndexError("List is empty")
        return self._head.data

    def last(self) -> T:
        # Returns the tail element without removing it.
        # Input:  None
        # Output: T
        if not self._tail:
            raise IndexError("List is empty")
        return self._tail.data

    def contains(self, value: T) -> bool:
        # Returns True if the list contains the given value.
        # Input:  value (T)
        # Output: bool
        curr = self._head
        while curr:
            if curr.data == value:
                return True
            curr = curr.next
        return False

    def reverse(self) -> None:
        # Reverses the list in place by swapping each node's prev and next references.
        # Input:  None
        # Output: None
        curr = self._head
        self._head, self._tail = self._tail, self._head
        while curr:
            curr.prev, curr.next = curr.next, curr.prev
            curr = curr.prev

    def clear(self) -> None:
        # Resets the list to empty by unlinking all nodes.
        # Input:  None
        # Output: None
        self._head = None
        self._tail = None
        self._length = 0

    def size(self) -> int:
        # Returns the number of elements in the list.
        # Input:  None
        # Output: int
        return self._length

    def is_empty(self) -> bool:
        # Returns True if the list holds no elements.
        # Input:  None
        # Output: bool
        return self._length == 0

    def __len__(self) -> int:
        # Returns the number of elements for use with len().
        # Input:  None
        # Output: int
        return self._length

    def __repr__(self) -> str:
        # Returns a bidirectional arrow-notation string of all elements.
        # Input:  None
        # Output: str
        parts = []
        curr = self._head
        while curr:
            parts.append(str(curr.data))
            curr = curr.next
        return "null <-> " + " <-> ".join(parts) + " <-> null"
