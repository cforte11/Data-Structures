/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Deque (Double-Ended Queue)
 * Description: Personal take on a linear structure supporting O(1) insertion and removal at both the front and back via a doubly linked list.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class Deque {
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

public:
    // Initializes an empty deque.
    // Input:  None
    // Output: None
    Deque() : head(nullptr), tail(nullptr), length(0) {}

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~Deque() { clear(); }

    // Inserts a new value at the front of the deque.
    // Input:  const T& value
    // Output: None
    void addFirst(const T& value) {
        Node* node = new Node(value);
        node->next = head;
        if (head) head->prev = node;
        head = node;
        if (!tail) tail = head;
        length++;
    }

    // Appends a new value at the back of the deque.
    // Input:  const T& value
    // Output: None
    void addLast(const T& value) {
        Node* node = new Node(value);
        node->prev = tail;
        if (tail) tail->next = node;
        tail = node;
        if (!head) head = tail;
        length++;
    }

    // Removes and returns the front element.
    // Input:  None
    // Output: T
    T removeFirst() {
        if (!head) throw std::underflow_error("Deque is empty");
        Node* node = head;
        T val = node->data;
        head = head->next;
        if (head) head->prev = nullptr;
        else tail = nullptr;
        delete node;
        length--;
        return val;
    }

    // Removes and returns the back element.
    // Input:  None
    // Output: T
    T removeLast() {
        if (!tail) throw std::underflow_error("Deque is empty");
        Node* node = tail;
        T val = node->data;
        tail = tail->prev;
        if (tail) tail->next = nullptr;
        else head = nullptr;
        delete node;
        length--;
        return val;
    }

    // Returns a const reference to the front element without removing it.
    // Input:  None
    // Output: const T&
    const T& first() const {
        if (!head) throw std::underflow_error("Deque is empty");
        return head->data;
    }

    // Returns a const reference to the back element without removing it.
    // Input:  None
    // Output: const T&
    const T& last() const {
        if (!tail) throw std::underflow_error("Deque is empty");
        return tail->data;
    }

    // Removes all nodes and resets the deque to empty.
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

    // Returns the number of elements in the deque.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if the deque holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Prints all elements from front to back.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = head;
        std::cout << "front [ ";
        while (curr) {
            std::cout << curr->data;
            if (curr->next) std::cout << ", ";
            curr = curr->next;
        }
        std::cout << " ] back\n";
    }
};
