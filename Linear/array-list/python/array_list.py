# Author: Christopher Forte
# Date: March 14, 2026
# Title: ArrayList
# Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.

from typing import TypeVar, Generic

T = TypeVar("T")


class ArrayList(Generic[T]):

    def __init__(self, initial_capacity: int = 8) -> None:
        # Initializes the list with an optional starting capacity.
        # Input:  initial_capacity (int, default 8)
        # Output: None
        if initial_capacity < 1:
            raise ValueError("Capacity must be >= 1")
        self._data: list = [None] * initial_capacity
        self._capacity: int = initial_capacity
        self._length: int = 0

    def _resize(self) -> None:
        # Doubles capacity and migrates all elements to a new backing list.
        # Input:  None
        # Output: None
        new_cap = self._capacity * 2
        new_data = [None] * new_cap
        for i in range(self._length):
            new_data[i] = self._data[i]
        self._data = new_data
        self._capacity = new_cap

    def _check_bounds(self, index: int) -> None:
        # Validates that an index falls within the current element range.
        # Input:  index (int)
        # Output: None (raises IndexError on violation)
        if index < 0 or index >= self._length:
            raise IndexError("Index out of bounds")

    def add(self, value: T, index: int = None) -> None:
        # Appends to the end or inserts at the given index, shifting elements right.
        # Input:  value (T), index (int | None)
        # Output: None
        if index is None:
            if self._length == self._capacity:
                self._resize()
            self._data[self._length] = value
            self._length += 1
        else:
            if index < 0 or index > self._length:
                raise IndexError("Index out of bounds")
            if self._length == self._capacity:
                self._resize()
            for i in range(self._length, index, -1):
                self._data[i] = self._data[i - 1]
            self._data[index] = value
            self._length += 1

    def remove(self, index: int) -> T:
        # Removes and returns the element at the given index, shifting elements left.
        # Input:  index (int)
        # Output: T
        self._check_bounds(index)
        val = self._data[index]
        for i in range(index, self._length - 1):
            self._data[i] = self._data[i + 1]
        self._data[self._length - 1] = None
        self._length -= 1
        return val

    def get(self, index: int) -> T:
        # Returns the element at the given index.
        # Input:  index (int)
        # Output: T
        self._check_bounds(index)
        return self._data[index]

    def set(self, index: int, value: T) -> None:
        # Replaces the element at the given index with a new value.
        # Input:  index (int), value (T)
        # Output: None
        self._check_bounds(index)
        self._data[index] = value

    def index_of(self, value: T) -> int:
        # Returns the index of the first matching element, or -1 if absent.
        # Input:  value (T)
        # Output: int
        for i in range(self._length):
            if self._data[i] == value:
                return i
        return -1

    def contains(self, value: T) -> bool:
        # Returns True if the list contains the given value.
        # Input:  value (T)
        # Output: bool
        return self.index_of(value) != -1

    def clear(self) -> None:
        # Resets length to zero, retaining allocated capacity.
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
