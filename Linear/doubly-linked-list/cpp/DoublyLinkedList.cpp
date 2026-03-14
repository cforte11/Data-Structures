/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Doubly Linked List
 * Description: Personal take on a bidirectionally linked linear structure enabling O(1) insertion and removal at both ends.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class DoublyLinkedList {
private:
    struct Node {
        T data;
        Node* prev;
        Node* next;
        Node(const T& val) : data(val), prev(nullptr), next(nullptr) {}
    };

    Node* head;
    Node* tail;
    int length;

    // Stitches a new node between two existing adjacent nodes.
    // Input:  Node* before, Node* after, const T& value
    // Output: None
    void insertBetween(Node* before, Node* after, const T& value) {
        Node* node = new Node(value);
        node->prev = before;
        node->next = after;
        if (before) before->next = node;
        if (after) after->prev = node;
        if (!before) head = node;
        if (!after) tail = node;
        length++;
    }

    // Detaches a node from its neighbors, deletes it, and returns its value.
    // Input:  Node* node
    // Output: T
    T unlink(Node* node) {
        T val = node->data;
        if (node->prev) node->prev->next = node->next;
        if (node->next) node->next->prev = node->prev;
        if (node == head) head = node->next;
        if (node == tail) tail = node->prev;
        delete node;
        length--;
        return val;
    }

public:
    // Initializes an empty doubly linked list.
    // Input:  None
    // Output: None
    DoublyLinkedList() : head(nullptr), tail(nullptr), length(0) {}

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~DoublyLinkedList() { clear(); }

    // Prepends a new value before the current head.
    // Input:  const T& value
    // Output: None
    void addFirst(const T& value) { insertBetween(nullptr, head, value); }

    // Appends a new value after the current tail.
    // Input:  const T& value
    // Output: None
    void addLast(const T& value) { insertBetween(tail, nullptr, value); }

    // Inserts a new value at the specified zero-based index.
    // Input:  int index, const T& value
    // Output: None
    void addAt(int index, const T& value) {
        if (index < 0 || index > length) throw std::out_of_range("Index out of bounds");
        if (index == 0) { addFirst(value); return; }
        if (index == length) { addLast(value); return; }
        Node* curr = head;
        for (int i = 0; i < index; i++) curr = curr->next;
        insertBetween(curr->prev, curr, value);
    }

    // Removes and returns the first element.
    // Input:  None
    // Output: T
    T removeFirst() {
        if (!head) throw std::underflow_error("List is empty");
        return unlink(head);
    }

    // Removes and returns the last element.
    // Input:  None
    // Output: T
    T removeLast() {
        if (!tail) throw std::underflow_error("List is empty");
        return unlink(tail);
    }

    // Removes and returns the element at the specified index.
    // Input:  int index
    // Output: T
    T removeAt(int index) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        Node* curr = (index < length / 2) ? head : tail;
        if (index < length / 2)
            for (int i = 0; i < index; i++) curr = curr->next;
        else
            for (int i = length - 1; i > index; i--) curr = curr->prev;
        return unlink(curr);
    }

    // Returns a reference to the element at the given index, traversing from the nearer end.
    // Input:  int index
    // Output: T&
    T& get(int index) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        Node* curr = (index < length / 2) ? head : tail;
        if (index < length / 2)
            for (int i = 0; i < index; i++) curr = curr->next;
        else
            for (int i = length - 1; i > index; i--) curr = curr->prev;
        return curr->data;
    }

    // Returns a const reference to the first element.
    // Input:  None
    // Output: const T&
    const T& first() const {
        if (!head) throw std::underflow_error("List is empty");
        return head->data;
    }

    // Returns a const reference to the last element.
    // Input:  None
    // Output: const T&
    const T& last() const {
        if (!tail) throw std::underflow_error("List is empty");
        return tail->data;
    }

    // Returns true if the list contains the given value.
    // Input:  const T& value
    // Output: bool
    bool contains(const T& value) const {
        Node* curr = head;
        while (curr) {
            if (curr->data == value) return true;
            curr = curr->next;
        }
        return false;
    }

    // Reverses the list in place by swapping each node's prev and next pointers.
    // Input:  None
    // Output: None
    void reverse() {
        Node* curr = head;
        std::swap(head, tail);
        while (curr) {
            std::swap(curr->prev, curr->next);
            curr = curr->prev;
        }
    }

    // Deletes all nodes and resets list state to empty.
    // Input:  None
    // Output: None
    void clear() {
        while (head) {
            Node* temp = head;
            head = head->next;
            delete temp;
        }
        tail = nullptr;
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

    // Prints all elements with bidirectional arrow notation.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = head;
        std::cout << "null <-> ";
        while (curr) {
            std::cout << curr->data;
            if (curr->next) std::cout << " <-> ";
            curr = curr->next;
        }
        std::cout << " <-> null\n";
    }
};
