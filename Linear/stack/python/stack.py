# Author: Christopher Forte
# Date: March 14, 2026
# Title: Stack
# Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic list.

from typing import TypeVar, Generic

T = TypeVar("T")


class Stack(Generic[T]):

    def __init__(self) -> None:
        # Initializes an empty stack.
        # Input:  None
        # Output: None
        self._data: list = []

    def push(self, value: T) -> None:
        # Pushes a new value onto the top of the stack.
        # Input:  value (T)
        # Output: None
        self._data.append(value)

    def pop(self) -> T:
        # Removes and returns the top element.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Stack is empty")
        return self._data.pop()

    def peek(self) -> T:
        # Returns the top element without removing it.
        # Input:  None
        # Output: T
        if self.is_empty():
            raise IndexError("Stack is empty")
        return self._data[-1]

    def clear(self) -> None:
        # Removes all elements from the stack.
        # Input:  None
        # Output: None
        self._data.clear()

    def size(self) -> int:
        # Returns the number of elements currently on the stack.
        # Input:  None
        # Output: int
        return len(self._data)

    def is_empty(self) -> bool:
        # Returns True if the stack holds no elements.
        # Input:  None
        # Output: bool
        return len(self._data) == 0

    def __len__(self) -> int:
        # Returns the number of elements for use with len().
        # Input:  None
        # Output: int
        return len(self._data)

    def __repr__(self) -> str:
        # Returns a string of elements from bottom to top.
        # Input:  None
        # Output: str
        return "bottom [ " + ", ".join(str(x) for x in self._data) + " ] top"
