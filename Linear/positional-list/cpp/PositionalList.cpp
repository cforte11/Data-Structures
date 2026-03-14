/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Positional List
 * Description: Personal take on a doubly linked list where stable Position handles provide O(1) insertion and removal relative to any node.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class PositionalList {
private:
    struct Node {
        T data;
        Node* prev;
        Node* next;
        Node(const T& val) : data(val), prev(nullptr), next(nullptr) {}
    };

    Node* header;
    Node* trailer;
    int length;

    // Validates that a raw node pointer is non-null and not a sentinel.
    // Input:  Node* node
    // Output: None (throws on invalid)
    void validate(Node* node) const {
        if (!node || node == header || node == trailer)
            throw std::invalid_argument("Invalid position");
    }

    // Creates and stitches a new node between two existing nodes, returning its pointer.
    // Input:  Node* before, Node* after, const T& value
    // Output: Node*
    Node* insertBetween(Node* before, Node* after, const T& value) {
        Node* node = new Node(value);
        node->prev = before;
        node->next = after;
        before->next = node;
        after->prev = node;
        length++;
        return node;
    }

public:
    // A lightweight position handle wrapping a node pointer.
    class Position {
        friend class PositionalList<T>;
    private:
        Node* node;
        explicit Position(Node* n) : node(n) {}
    public:
        Position() : node(nullptr) {}

        // Returns a const reference to the element held at this position.
        // Input:  None
        // Output: const T&
        const T& element() const { return node->data; }

        // Returns a mutable reference to the element held at this position.
        // Input:  None
        // Output: T&
        T& element() { return node->data; }

        // Returns true if two positions refer to the same node.
        // Input:  const Position& other
        // Output: bool
        bool operator==(const Position& other) const { return node == other.node; }

        // Returns true if two positions refer to different nodes.
        // Input:  const Position& other
        // Output: bool
        bool operator!=(const Position& other) const { return node != other.node; }
    };

    // Constructs the list with sentinel header and trailer nodes.
    // Input:  None
    // Output: None
    PositionalList() : length(0) {
        header  = new Node(T{}); 
        trailer = new Node(T{});
        header->next  = trailer;
        trailer->prev = header;
    }

    // Releases all nodes including sentinels.
    // Input:  None
    // Output: None
    ~PositionalList() {
        clear();
        delete header;
        delete trailer;
    }

    // Returns the position of the first element, or an invalid position if empty.
    // Input:  None
    // Output: Position
    Position first() const {
        return Position(header->next == trailer ? nullptr : header->next);
    }

    // Returns the position of the last element, or an invalid position if empty.
    // Input:  None
    // Output: Position
    Position last() const {
        return Position(trailer->prev == header ? nullptr : trailer->prev);
    }

    // Returns the position immediately before the given position.
    // Input:  Position p
    // Output: Position
    Position before(Position p) const {
        validate(p.node);
        Node* prev = p.node->prev;
        return Position(prev == header ? nullptr : prev);
    }

    // Returns the position immediately after the given position.
    // Input:  Position p
    // Output: Position
    Position after(Position p) const {
        validate(p.node);
        Node* next = p.node->next;
        return Position(next == trailer ? nullptr : next);
    }

    // Inserts a new element before the front sentinel, returning its position.
    // Input:  const T& value
    // Output: Position
    Position addFirst(const T& value) {
        return Position(insertBetween(header, header->next, value));
    }

    // Inserts a new element before the back sentinel, returning its position.
    // Input:  const T& value
    // Output: Position
    Position addLast(const T& value) {
        return Position(insertBetween(trailer->prev, trailer, value));
    }

    // Inserts a new element immediately before the given position, returning its position.
    // Input:  Position p, const T& value
    // Output: Position
    Position addBefore(Position p, const T& value) {
        validate(p.node);
        return Position(insertBetween(p.node->prev, p.node, value));
    }

    // Inserts a new element immediately after the given position, returning its position.
    // Input:  Position p, const T& value
    // Output: Position
    Position addAfter(Position p, const T& value) {
        validate(p.node);
        return Position(insertBetween(p.node, p.node->next, value));
    }

    // Removes the element at the given position and returns its value.
    // Input:  Position p
    // Output: T
    T remove(Position p) {
        validate(p.node);
        Node* node = p.node;
        T val = node->data;
        node->prev->next = node->next;
        node->next->prev = node->prev;
        delete node;
        length--;
        return val;
    }

    // Replaces the element at the given position and returns the old value.
    // Input:  Position p, const T& value
    // Output: T (old value)
    T set(Position p, const T& value) {
        validate(p.node);
        T old = p.node->data;
        p.node->data = value;
        return old;
    }

    // Removes all non-sentinel nodes and resets length to zero.
    // Input:  None
    // Output: None
    void clear() {
        Node* curr = header->next;
        while (curr != trailer) {
            Node* temp = curr;
            curr = curr->next;
            delete temp;
        }
        header->next  = trailer;
        trailer->prev = header;
        length = 0;
    }

    // Returns the number of elements in the list.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if the list holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Prints all element values from first to last.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = header->next;
        std::cout << "[ ";
        while (curr != trailer) {
            std::cout << curr->data;
            if (curr->next != trailer) std::cout << ", ";
            curr = curr->next;
        }
        std::cout << " ]\n";
    }
};
