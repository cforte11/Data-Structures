# Author: Christopher Forte
# Date: March 14, 2026
# Title: Deque (Double-Ended Queue)
# Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back.

from typing import TypeVar, Generic
from collections import deque

T = TypeVar("T")


class Deque(Generic[T]):

    def __init__(self) -> None:
        # Initializes an empty deque.
        # Input:  None
        # Output: None
        self._data: deque = deque()

    def add_first(self, value: T) -> None:
        # Inserts a new value at the front of the deque.
        # Input:  value (T)
        # Output: None
        self._data.appendleft(value)

    def add_last(self, value: T) -> None:
        # Appends a new value at the back of the deque.
        # Input:  value (T)
        # Output: None
        self._data.append(value)

    def remove_first(self) -> T:
        # Removes and returns the front element.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Deque is empty")
        return self._data.popleft()

    def remove_last(self) -> T:
        # Removes and returns the back element.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Deque is empty")
        return self._data.pop()

    def first(self) -> T:
        # Returns the front element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Deque is empty")
        return self._data[0]

    def last(self) -> T:
        # Returns the back element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Deque is empty")
        return self._data[-1]

    def clear(self) -> None:
        # Removes all elements from the deque.
        # Input:  None
        # Output: None
        self._data.clear()

    def size(self) -> int:
        # Returns the number of elements in the deque.
        # Input:  None
        # Output: int
        return len(self._data)

    def is_empty(self) -> bool:
        # Returns True if the deque holds no elements.
        # Input:  None
        # Output: bool
        return len(self._data) == 0

    def __len__(self) -> int:
        # Returns the number of elements for use with len().
        # Input:  None
        # Output: int
        return len(self._data)

    def __repr__(self) -> str:
        # Returns a string of elements from front to back.
        # Input:  None
        # Output: str
        return "front [ " + ", ".join(str(x) for x in self._data) + " ] back"
