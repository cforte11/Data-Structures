# Author: Christopher Forte
# Date: March 14, 2026
# Title: Skip List
# Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.

from typing import TypeVar, Generic, Optional, List
import random

T = TypeVar("T")

_MAX_LEVEL = 16
_PROB = 0.5


class SkipList(Generic[T]):

    class _Node:
        __slots__ = ("key", "forward")

        def __init__(self, key: object, level: int) -> None:
            self.key = key
            self.forward: List[Optional["SkipList._Node"]] = [None] * (level + 1)

    def __init__(self) -> None:
        # Initializes the skip list with a sentinel header and level tracking.
        # Input:  None
        # Output: None
        self._header = self._Node(None, _MAX_LEVEL - 1)
        self._current_level: int = 0
        self._length: int = 0

    def _random_level(self) -> int:
        # Generates a random level using geometric distribution.
        # Input:  None
        # Output: int (level between 0 and MAX_LEVEL-1)
        level = 0
        while random.random() < _PROB and level < _MAX_LEVEL - 1:
            level += 1
        return level

    def contains(self, key: T) -> bool:
        # Searches for a key and returns True if it exists in the list.
        # Input:  key (T)
        # Output: bool
        curr = self._header
        for i in range(self._current_level, -1, -1):
            while curr.forward[i] and curr.forward[i].key < key:
                curr = curr.forward[i]
        curr = curr.forward[0]
        return curr is not None and curr.key == key

    def insert(self, key: T) -> None:
        # Inserts a key into the skip list at the correct sorted position.
        # Input:  key (T)
        # Output: None
        update: List[Optional[SkipList._Node]] = [None] * _MAX_LEVEL
        curr = self._header
        for i in range(self._current_level, -1, -1):
            while curr.forward[i] and curr.forward[i].key < key:
                curr = curr.forward[i]
            update[i] = curr
        curr = curr.forward[0]
        if curr and curr.key == key:
            return
        new_level = self._random_level()
        if new_level > self._current_level:
            for i in range(self._current_level + 1, new_level + 1):
                update[i] = self._header
            self._current_level = new_level
        node = self._Node(key, new_level)
        for i in range(new_level + 1):
            node.forward[i] = update[i].forward[i]
            update[i].forward[i] = node
        self._length += 1

    def remove(self, key: T) -> None:
        # Removes the node with the given key if it exists.
        # Input:  key (T)
        # Output: None
        update: List[Optional[SkipList._Node]] = [None] * _MAX_LEVEL
        curr = self._header
        for i in range(self._current_level, -1, -1):
            while curr.forward[i] and curr.forward[i].key < key:
                curr = curr.forward[i]
            update[i] = curr
        curr = curr.forward[0]
        if not curr or curr.key != key:
            return
        for i in range(self._current_level + 1):
            if update[i].forward[i] is not curr:
                break
            update[i].forward[i] = curr.forward[i]
        while self._current_level > 0 and not self._header.forward[self._current_level]:
            self._current_level -= 1
        self._length -= 1

    def clear(self) -> None:
        # Resets all forward pointers on the header and restores initial state.
        # Input:  None
        # Output: None
        self._header = self._Node(None, _MAX_LEVEL - 1)
        self._current_level = 0
        self._length = 0

    def size(self) -> int:
        # Returns the number of elements stored in the skip list.
        # Input:  None
        # Output: int
        return self._length

    def is_empty(self) -> bool:
        # Returns True if the skip list holds no elements.
        # Input:  None
        # Output: bool
        return self._length == 0

    def __len__(self) -> int:
        # Returns the number of elements for use with len().
        # Input:  None
        # Output: int
        return self._length

    def __repr__(self) -> str:
        # Returns a string of all keys at level 0 (the base sorted sequence).
        # Input:  None
        # Output: str
        parts = []
        curr = self._header.forward[0]
        while curr:
            parts.append(str(curr.key))
            curr = curr.forward[0]
        return "[ " + ", ".join(parts) + " ]"
