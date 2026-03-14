# Author: Christopher Forte
# Date: March 14, 2026
# Title: Circular Queue
# Description: Personal take on a fixed-capacity FIFO queue using a circular list with modular index arithmetic.

from typing import TypeVar, Generic

T = TypeVar("T")


class CircularQueue(Generic[T]):

    def __init__(self, capacity: int) -> None:
        # Initializes a circular queue with a fixed capacity.
        # Input:  capacity (int)
        # Output: None
        if capacity < 1:
            raise ValueError("Capacity must be >= 1")
        self._data: list = [None] * capacity
        self._capacity: int = capacity
        self._front: int = 0
        self._length: int = 0

    def enqueue(self, value: T) -> None:
        # Adds a value to the back, wrapping around via modular arithmetic.
        # Input:  value (T)
        # Output: None
        if self.is_full():
            raise OverflowError("Queue is full")
        back = (self._front + self._length) % self._capacity
        self._data[back] = value
        self._length += 1

    def dequeue(self) -> T:
        # Removes and returns the front element, advancing the front index circularly.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        val = self._data[self._front]
        self._data[self._front] = None
        self._front = (self._front + 1) % self._capacity
        self._length -= 1
        return val

    def front(self) -> T:
        # Returns the front element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        return self._data[self._front]

    def back(self) -> T:
        # Returns the back element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Queue is empty")
        return self._data[(self._front + self._length - 1) % self._capacity]

    def clear(self) -> None:
        # Resets all slots, front index, and length.
        # Input:  None
        # Output: None
        self._data = [None] * self._capacity
        self._front = 0
        self._length = 0

    def size(self) -> int:
        # Returns the number of elements currently in the queue.
        # Input:  None
        # Output: int
        return self._length

    def is_empty(self) -> bool:
        # Returns True if no elements are present.
        # Input:  None
        # Output: bool
        return self._length == 0

    def is_full(self) -> bool:
        # Returns True if the queue has reached its fixed capacity.
        # Input:  None
        # Output: bool
        return self._length == self._capacity

    def __len__(self) -> int:
        # Returns the number of elements for use with len().
        # Input:  None
        # Output: int
        return self._length

    def __repr__(self) -> str:
        # Returns a string of elements in FIFO order from front to back.
        # Input:  None
        # Output: str
        parts = [str(self._data[(self._front + i) % self._capacity]) for i in range(self._length)]
        return "front [ " + ", ".join(parts) + " ] back"
