# Author: Christopher Forte
# Date: March 14, 2026
# Title: Array
# Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.

from typing import TypeVar, Generic, Optional

T = TypeVar("T")


class Array(Generic[T]):

    def __init__(self, initial_capacity: int = 8) -> None:
        # Initializes the array with a given starting capacity.
        # Input:  initial_capacity (int, default 8)
        # Output: None
        if initial_capacity < 1:
            raise ValueError("Capacity must be >= 1")
        self._data: list = [None] * initial_capacity
        self._capacity: int = initial_capacity
        self._length: int = 0

    def _resize(self) -> None:
        # Doubles the internal list capacity when the array is full.
        # Input:  None
        # Output: None
        new_capacity = self._capacity * 2
        new_data = [None] * new_capacity
        for i in range(self._length):
            new_data[i] = self._data[i]
        self._data = new_data
        self._capacity = new_capacity

    def append(self, value: T) -> None:
        # Appends a value to the end of the array, resizing if necessary.
        # Input:  value (T)
        # Output: None
        if self._length == self._capacity:
            self._resize()
        self._data[self._length] = value
        self._length += 1

    def insert(self, index: int, value: T) -> None:
        # Inserts a value at the specified index, shifting subsequent elements right.
        # Input:  index (int), value (T)
        # Output: None
        if index < 0 or index > self._length:
            raise IndexError("Index out of bounds")
        if self._length == self._capacity:
            self._resize()
        for i in range(self._length, index, -1):
            self._data[i] = self._data[i - 1]
        self._data[index] = value
        self._length += 1

    def remove(self, index: int) -> T:
        # Removes and returns the element at the specified index, shifting remaining elements left.
        # Input:  index (int)
        # Output: T (removed element)
        self._check_bounds(index)
        removed = self._data[index]
        for i in range(index, self._length - 1):
            self._data[i] = self._data[i + 1]
        self._data[self._length - 1] = None
        self._length -= 1
        return removed

    def get(self, index: int) -> T:
        # Returns the element at the given index.
        # Input:  index (int)
        # Output: T
        self._check_bounds(index)
        return self._data[index]

    def set(self, index: int, value: T) -> None:
        # Replaces the value at the specified index.
        # Input:  index (int), value (T)
        # Output: None
        self._check_bounds(index)
        self._data[index] = value

    def index_of(self, value: T) -> int:
        # Returns the index of the first occurrence of a value, or -1 if not found.
        # Input:  value (T)
        # Output: int
        for i in range(self._length):
            if self._data[i] == value:
                return i
        return -1

    def clear(self) -> None:
        # Resets the length to zero without freeing capacity.
        # Input:  None
        # Output: None
        self._data = [None] * self._capacity
        self._length = 0

    def size(self) -> int:
        # Returns the number of elements currently stored.
        # Input:  None
        # Output: int
        return self._length

    def is_empty(self) -> bool:
        # Returns True if no elements are stored.
        # Input:  None
        # Output: bool
        return self._length == 0

    def capacity(self) -> int:
        # Returns the current allocated capacity.
        # Input:  None
        # Output: int
        return self._capacity

    def _check_bounds(self, index: int) -> None:
        # Validates that an index falls within the current element range.
        # Input:  index (int)
        # Output: None (raises IndexError on violation)
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")

    def __getitem__(self, index: int) -> T:
        # Supports bracket-based read access.
        # Input:  index (int)
        # Output: T
        return self.get(index)

    def __setitem__(self, index: int, value: T) -> None:
        # Supports bracket-based write access.
        # Input:  index (int), value (T)
        # Output: None
        self.set(index, value)

    def __len__(self) -> int:
        # Returns the number of stored elements for use with len().
        # Input:  None
        # Output: int
        return self._length

    def __repr__(self) -> str:
        # Returns a string representation of all stored elements.
        # Input:  None
        # Output: str
        return "[ " + ", ".join(str(self._data[i]) for i in range(self._length)) + " ]"
