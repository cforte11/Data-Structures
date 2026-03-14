/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circularly Linked List
 * Description: Personal take on a singly-linked circular structure where the tail's next pointer wraps back to the head.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class CircularlyLinkedList {
private:
    struct Node {
        T data;
        Node* next;
        Node(const T& val) : data(val), next(nullptr) {}
    };

    Node* tail;
    int length;

public:
    // Initializes an empty circular list with no nodes.
    // Input:  None
    // Output: None
    CircularlyLinkedList() : tail(nullptr), length(0) {}

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~CircularlyLinkedList() { clear(); }

    // Inserts a new value immediately after the tail (becoming the new head).
    // Input:  const T& value
    // Output: None
    void addFirst(const T& value) {
        Node* node = new Node(value);
        if (!tail) {
            tail = node;
            tail->next = tail;
        } else {
            node->next = tail->next;
            tail->next = node;
        }
        length++;
    }

    // Appends a new value as the new tail of the circular list.
    // Input:  const T& value
    // Output: None
    void addLast(const T& value) {
        addFirst(value);
        tail = tail->next;
    }

    // Removes and returns the front (head) element.
    // Input:  None
    // Output: T
    T removeFirst() {
        if (!tail) throw std::underflow_error("List is empty");
        Node* head = tail->next;
        T val = head->data;
        if (tail == head) {
            tail = nullptr;
        } else {
            tail->next = head->next;
        }
        delete head;
        length--;
        return val;
    }

    // Advances the tail pointer forward one step, rotating the list.
    // Input:  None
    // Output: None
    void rotate() {
        if (tail) tail = tail->next;
    }

    // Returns a const reference to the head (first) element.
    // Input:  None
    // Output: const T&
    const T& first() const {
        if (!tail) throw std::underflow_error("List is empty");
        return tail->next->data;
    }

    // Returns a const reference to the tail (last) element.
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
        if (!tail) return false;
        Node* curr = tail->next;
        do {
            if (curr->data == value) return true;
            curr = curr->next;
        } while (curr != tail->next);
        return false;
    }

    // Deletes all nodes and resets the list to empty.
    // Input:  None
    // Output: None
    void clear() {
        if (!tail) return;
        Node* curr = tail->next;
        while (curr != tail) {
            Node* temp = curr;
            curr = curr->next;
            delete temp;
        }
        delete tail;
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

    // Prints all elements starting from head, showing the circular wrap.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        if (!tail) { std::cout << "(empty)\n"; return; }
        Node* curr = tail->next;
        do {
            std::cout << curr->data;
            curr = curr->next;
            if (curr != tail->next) std::cout << " -> ";
        } while (curr != tail->next);
        std::cout << " -> (head)\n";
    }
};
