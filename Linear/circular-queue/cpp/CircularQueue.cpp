/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circular Queue
 * Description: Personal take on a fixed-capacity FIFO queue using a circular array with modular index arithmetic.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class CircularQueue {
private:
    T* data;
    int capacity;
    int frontIndex;
    int length;

public:
    // Initializes a circular queue with a fixed capacity.
    // Input:  int capacity
    // Output: None
    explicit CircularQueue(int cap)
        : capacity(cap), frontIndex(0), length(0) {
        if (cap < 1) throw std::invalid_argument("Capacity must be >= 1");
        data = new T[capacity];
    }

    // Releases heap-allocated storage.
    // Input:  None
    // Output: None
    ~CircularQueue() { delete[] data; }

    // Adds a new value to the back, wrapping around via modular arithmetic.
    // Input:  const T& value
    // Output: None
    void enqueue(const T& value) {
        if (isFull()) throw std::overflow_error("Queue is full");
        int back = (frontIndex + length) % capacity;
        data[back] = value;
        length++;
    }

    // Removes and returns the front element, advancing the front index circularly.
    // Input:  None
    // Output: T
    T dequeue() {
        if (isEmpty()) throw std::underflow_error("Queue is empty");
        T val = data[frontIndex];
        frontIndex = (frontIndex + 1) % capacity;
        length--;
        return val;
    }

    // Returns a const reference to the front element without removing it.
    // Input:  None
    // Output: const T&
    const T& front() const {
        if (isEmpty()) throw std::underflow_error("Queue is empty");
        return data[frontIndex];
    }

    // Returns a const reference to the back element without removing it.
    // Input:  None
    // Output: const T&
    const T& back() const {
        if (isEmpty()) throw std::underflow_error("Queue is empty");
        return data[(frontIndex + length - 1) % capacity];
    }

    // Resets front index and length, logically clearing all elements.
    // Input:  None
    // Output: None
    void clear() { frontIndex = 0; length = 0; }

    // Returns the number of elements currently in the queue.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if no elements are present.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Returns true if the queue has reached its fixed capacity.
    // Input:  None
    // Output: bool
    bool isFull() const { return length == capacity; }

    // Prints all elements in FIFO order from front to back.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        std::cout << "front [ ";
        for (int i = 0; i < length; i++) {
            std::cout << data[(frontIndex + i) % capacity];
            if (i < length - 1) std::cout << ", ";
        }
        std::cout << " ] back\n";
    }
};
