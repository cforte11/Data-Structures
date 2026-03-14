/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Queue
 * Description: Personal take on a FIFO (first-in, first-out) linear data structure backed by a singly linked list.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class Queue {
private:
    struct Node {
        T data;
        Node* next;
        Node(const T& val) : data(val), next(nullptr) {}
    };

    Node* frontNode;
    Node* backNode;
    int length;

public:
    // Initializes an empty queue.
    // Input:  None
    // Output: None
    Queue() : frontNode(nullptr), backNode(nullptr), length(0) {}

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~Queue() { clear(); }

    // Adds a new value to the back of the queue.
    // Input:  const T& value
    // Output: None
    void enqueue(const T& value) {
        Node* node = new Node(value);
        if (backNode) backNode->next = node;
        backNode = node;
        if (!frontNode) frontNode = backNode;
        length++;
    }

    // Removes and returns the front element.
    // Input:  None
    // Output: T
    T dequeue() {
        if (!frontNode) throw std::underflow_error("Queue is empty");
        Node* node = frontNode;
        T val = node->data;
        frontNode = frontNode->next;
        if (!frontNode) backNode = nullptr;
        delete node;
        length--;
        return val;
    }

    // Returns a const reference to the front element without removing it.
    // Input:  None
    // Output: const T&
    const T& front() const {
        if (!frontNode) throw std::underflow_error("Queue is empty");
        return frontNode->data;
    }

    // Returns a const reference to the back element without removing it.
    // Input:  None
    // Output: const T&
    const T& back() const {
        if (!backNode) throw std::underflow_error("Queue is empty");
        return backNode->data;
    }

    // Removes all elements and resets the queue to empty.
    // Input:  None
    // Output: None
    void clear() {
        while (frontNode) {
            Node* temp = frontNode;
            frontNode = frontNode->next;
            delete temp;
        }
        backNode = nullptr;
        length = 0;
    }

    // Returns the number of elements in the queue.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if the queue holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Prints all elements from front to back.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = frontNode;
        std::cout << "front [ ";
        while (curr) {
            std::cout << curr->data;
            if (curr->next) std::cout << ", ";
            curr = curr->next;
        }
        std::cout << " ] back\n";
    }
};
