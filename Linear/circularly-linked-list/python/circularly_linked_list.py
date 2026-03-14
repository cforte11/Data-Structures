# Author: Christopher Forte
# Date: March 14, 2026
# Title: Circularly Linked List
# Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.

from typing import TypeVar, Generic, Optional

T = TypeVar("T")


class CircularlyLinkedList(Generic[T]):

    class _Node:
        __slots__ = ("data", "next")

        def __init__(self, data: object) -> None:
            self.data = data
            self.next: Optional["CircularlyLinkedList._Node"] = None

    def __init__(self) -> None:
        # Initializes an empty circular list with no nodes.
        # Input:  None
        # Output: None
        self._tail: Optional[CircularlyLinkedList._Node] = None
        self._length: int = 0

    def add_first(self, value: T) -> None:
        # Inserts a new value immediately after the tail, making it the new head.
        # Input:  value (T)
        # Output: None
        node = self._Node(value)
        if self._tail is None:
            self._tail = node
            self._tail.next = self._tail
        else:
            node.next = self._tail.next
            self._tail.next = node
        self._length += 1

    def add_last(self, value: T) -> None:
        # Appends a new value as the new tail of the circular list.
        # Input:  value (T)
        # Output: None
        self.add_first(value)
        self._tail = self._tail.next

    def remove_first(self) -> T:
        # Removes and returns the front (head) element.
        # Input:  None
        # Output: T
        if not self._tail:
            raise IndexError("List is empty")
        head = self._tail.next
        val = head.data
        if self._tail is head:
            self._tail = None
        else:
            self._tail.next = head.next
        self._length -= 1
        return val

    def rotate(self) -> None:
        # Advances the tail pointer forward one step, rotating the front to the back.
        # Input:  None
        # Output: None
        if self._tail:
            self._tail = self._tail.next

    def first(self) -> T:
        # Returns the head element without removing it.
        # Input:  None
        # Output: T
        if not self._tail:
            raise IndexError("List is empty")
        return self._tail.next.data

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
        if not self._tail:
            return False
        curr = self._tail.next
        while True:
            if curr.data == value:
                return True
            curr = curr.next
            if curr is self._tail.next:
                break
        return False

    def clear(self) -> None:
        # Resets the list to empty by discarding all node references.
        # Input:  None
        # Output: None
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
        # Returns a string of all elements starting from the head, noting the circular wrap.
        # Input:  None
        # Output: str
        if not self._tail:
            return "(empty)"
        parts = []
        curr = self._tail.next
        while True:
            parts.append(str(curr.data))
            curr = curr.next
            if curr is self._tail.next:
                break
        return " -> ".join(parts) + " -> (head)"
