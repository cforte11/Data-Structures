# Author: Christopher Forte
# Date: March 14, 2026
# Title: Queue
# Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a collections.deque for O(1) operations at both ends.

from typing import TypeVar, Generic
from collections import deque

T = TypeVar("T")


class Queue(Generic[T]):

    def __init__(self) -> None:
        # Initializes an empty queue.
        # Input:  None
        # Output: None
        self._data: deque = deque()

    def enqueue(self, value: T) -> None:
        # Adds a new value to the back of the queue.
        # Input:  value (T)
        # Output: None
        self._data.append(value)

    def dequeue(self) -> T:
        # Removes and returns the front element.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        return self._data.popleft()

    def front(self) -> T:
        # Returns the front element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        return self._data[0]

    def back(self) -> T:
        # Returns the back element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        return self._data[-1]

    def clear(self) -> None:
        # Removes all elements from the queue.
        # Input:  None
        # Output: None
        self._data.clear()

    def size(self) -> int:
        # Returns the number of elements in the queue.
        # Input:  None
        # Output: int
        return len(self._data)

    def is_empty(self) -> bool:
        # Returns True if the queue holds no elements.
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
