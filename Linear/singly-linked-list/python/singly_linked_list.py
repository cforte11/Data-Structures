# Author: Christopher Forte
# Date: March 14, 2026
# Title: Singly Linked List
# Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.

from typing import TypeVar, Generic, Optional

T = TypeVar("T")


class SinglyLinkedList(Generic[T]):

    class _Node:
        __slots__ = ("data", "next")

        def __init__(self, data: object) -> None:
            self.data = data
            self.next: Optional["SinglyLinkedList._Node"] = None

    def __init__(self) -> None:
        # Initializes an empty list with no nodes.
        # Input:  None
        # Output: None
        self._head: Optional[SinglyLinkedList._Node] = None
        self._tail: Optional[SinglyLinkedList._Node] = None
        self._length: int = 0

    def add_first(self, value: T) -> None:
        # Prepends a new value before the current head.
        # Input:  value (T)
        # Output: None
        node = self._Node(value)
        node.next = self._head
        self._head = node
        if self._tail is None:
            self._tail = self._head
        self._length += 1

    def add_last(self, value: T) -> None:
        # Appends a new value after the current tail.
        # Input:  value (T)
        # Output: None
        node = self._Node(value)
        if self._tail:
            self._tail.next = node
        self._tail = node
        if self._head is None:
            self._head = self._tail
        self._length += 1

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
        prev = self._head
        for _ in range(index - 1):
            prev = prev.next
        node = self._Node(value)
        node.next = prev.next
        prev.next = node
        self._length += 1

    def remove_first(self) -> T:
        # Removes and returns the first element.
        # Input:  None
        # Output: T
        if not self._head:
            raise IndexError("List is empty")
        val = self._head.data
        self._head = self._head.next
        if self._head is None:
            self._tail = None
        self._length -= 1
        return val

    def remove_last(self) -> T:
        # Traverses to the second-to-last node and removes the tail.
        # Input:  None
        # Output: T
        if not self._head:
            raise IndexError("List is empty")
        if self._head is self._tail:
            return self.remove_first()
        prev = self._head
        while prev.next is not self._tail:
            prev = prev.next
        val = self._tail.data
        self._tail = prev
        self._tail.next = None
        self._length -= 1
        return val

    def remove_at(self, index: int) -> T:
        # Removes and returns the element at the specified index.
        # Input:  index (int)
        # Output: T
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")
        if index == 0:
            return self.remove_first()
        if index == self._length - 1:
            return self.remove_last()
        prev = self._head
        for _ in range(index - 1):
            prev = prev.next
        target = prev.next
        val = target.data
        prev.next = target.next
        self._length -= 1
        return val

    def get(self, index: int) -> T:
        # Returns the element at the specified index by linear traversal.
        # Input:  index (int)
        # Output: T
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")
        curr = self._head
        for _ in range(index):
            curr = curr.next
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
        # Reverses all nodes in place using iterative pointer reassignment.
        # Input:  None
        # Output: None
        prev = None
        curr = self._head
        self._tail = self._head
        while curr:
            nxt = curr.next
            curr.next = prev
            prev = curr
            curr = nxt
        self._head = prev

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
        # Returns a string of elements separated by arrows.
        # Input:  None
        # Output: str
        parts = []
        curr = self._head
        while curr:
            parts.append(str(curr.data))
            curr = curr.next
        return " -> ".join(parts) + " -> null"
