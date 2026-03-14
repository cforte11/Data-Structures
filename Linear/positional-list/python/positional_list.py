# Author: Christopher Forte
# Date: March 14, 2026
# Title: Positional List
# Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.

from typing import TypeVar, Generic, Optional

T = TypeVar("T")


class PositionalList(Generic[T]):

    class _Node:
        __slots__ = ("data", "prev", "next", "container")

        def __init__(self, data: object, prev, next_, container) -> None:
            self.data = data
            self.prev = prev
            self.next = next_
            self.container = container

    class Position:

        def __init__(self, container: "PositionalList", node: "_Node") -> None:
            # Wraps a node reference with a back-reference to its owning list.
            # Input:  container (PositionalList), node (_Node)
            # Output: None
            self._container = container
            self._node = node

        def element(self) -> T:
            # Returns the element stored at this position.
            # Input:  None
            # Output: T
            return self._node.data

        def __eq__(self, other: object) -> bool:
            # Returns True if two positions refer to the same underlying node.
            # Input:  other (object)
            # Output: bool
            return type(other) is type(self) and other._node is self._node

        def __ne__(self, other: object) -> bool:
            # Returns True if two positions refer to different nodes.
            # Input:  other (object)
            # Output: bool
            return not (self == other)

    def _validate(self, p: "Position") -> "_Node":
        # Validates a position belongs to this list and has not been deleted.
        # Input:  p (Position)
        # Output: _Node
        if not isinstance(p, self.Position):
            raise TypeError("p must be a Position instance")
        if p._container is not self:
            raise ValueError("p does not belong to this list")
        if p._node.next is None:
            raise ValueError("p is no longer valid")
        return p._node

    def _make_position(self, node: "_Node") -> Optional["Position"]:
        # Returns a Position for the given node, or None if it is a sentinel.
        # Input:  node (_Node)
        # Output: Position | None
        if node is self._header or node is self._trailer:
            return None
        return self.Position(self, node)

    def __init__(self) -> None:
        # Constructs the list with sentinel header and trailer nodes.
        # Input:  None
        # Output: None
        self._header = self._Node(None, None, None, self)
        self._trailer = self._Node(None, None, None, self)
        self._header.next = self._trailer
        self._trailer.prev = self._header
        self._length: int = 0

    def first(self) -> Optional["Position"]:
        # Returns the position of the first element, or None if empty.
        # Input:  None
        # Output: Position | None
        return self._make_position(self._header.next)

    def last(self) -> Optional["Position"]:
        # Returns the position of the last element, or None if empty.
        # Input:  None
        # Output: Position | None
        return self._make_position(self._trailer.prev)

    def before(self, p: "Position") -> Optional["Position"]:
        # Returns the position immediately before the given position.
        # Input:  p (Position)
        # Output: Position | None
        node = self._validate(p)
        return self._make_position(node.prev)

    def after(self, p: "Position") -> Optional["Position"]:
        # Returns the position immediately after the given position.
        # Input:  p (Position)
        # Output: Position | None
        node = self._validate(p)
        return self._make_position(node.next)

    def _insert_between(self, value: T, before: "_Node", after: "_Node") -> "Position":
        # Creates a new node between two existing nodes and returns its position.
        # Input:  value (T), before (_Node), after (_Node)
        # Output: Position
        node = self._Node(value, before, after, self)
        before.next = node
        after.prev = node
        self._length += 1
        return self.Position(self, node)

    def add_first(self, value: T) -> "Position":
        # Inserts a new element at the front and returns its position.
        # Input:  value (T)
        # Output: Position
        return self._insert_between(value, self._header, self._header.next)

    def add_last(self, value: T) -> "Position":
        # Inserts a new element at the back and returns its position.
        # Input:  value (T)
        # Output: Position
        return self._insert_between(value, self._trailer.prev, self._trailer)

    def add_before(self, p: "Position", value: T) -> "Position":
        # Inserts a new element immediately before the given position.
        # Input:  p (Position), value (T)
        # Output: Position
        node = self._validate(p)
        return self._insert_between(value, node.prev, node)

    def add_after(self, p: "Position", value: T) -> "Position":
        # Inserts a new element immediately after the given position.
        # Input:  p (Position), value (T)
        # Output: Position
        node = self._validate(p)
        return self._insert_between(value, node, node.next)

    def delete(self, p: "Position") -> T:
        # Removes the element at the given position and returns its value.
        # Input:  p (Position)
        # Output: T
        node = self._validate(p)
        node.prev.next = node.next
        node.next.prev = node.prev
        self._length -= 1
        value = node.data
        node.prev = node.next = node.container = node.data = None
        return value

    def replace(self, p: "Position", value: T) -> T:
        # Replaces the element at the given position and returns the old value.
        # Input:  p (Position), value (T)
        # Output: T
        node = self._validate(p)
        old = node.data
        node.data = value
        return old

    def clear(self) -> None:
        # Removes all non-sentinel nodes and resets length to zero.
        # Input:  None
        # Output: None
        self._header.next = self._trailer
        self._trailer.prev = self._header
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

    def __iter__(self):
        # Yields each element value from first to last.
        # Input:  None
        # Output: generator of T
        cursor = self.first()
        while cursor is not None:
            yield cursor.element()
            cursor = self.after(cursor)

    def __repr__(self) -> str:
        # Returns a string of all element values from first to last.
        # Input:  None
        # Output: str
        return "[ " + ", ".join(str(e) for e in self) + " ]"
