/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Stack
 * Description: Personal take on a LIFO (last-in, first-out) linear data structure backed by a dynamic array.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class Stack {
private:
    T* data;
    int capacity;
    int topIndex;

    // Doubles the internal array capacity when the stack is full.
    // Input:  None
    // Output: None
    void resize() {
        int newCapacity = capacity * 2;
        T* newData = new T[newCapacity];
        for (int i = 0; i <= topIndex; i++) newData[i] = data[i];
        delete[] data;
        data = newData;
        capacity = newCapacity;
    }

public:
    // Initializes an empty stack with a given starting capacity.
    // Input:  int initialCapacity (default 8)
    // Output: None
    explicit Stack(int initialCapacity = 8)
        : capacity(initialCapacity), topIndex(-1) {
        if (initialCapacity < 1) throw std::invalid_argument("Capacity must be >= 1");
        data = new T[capacity];
    }

    // Releases heap-allocated storage.
    // Input:  None
    // Output: None
    ~Stack() { delete[] data; }

    // Pushes a new value onto the top of the stack.
    // Input:  const T& value
    // Output: None
    void push(const T& value) {
        if (topIndex + 1 == capacity) resize();
        data[++topIndex] = value;
    }

    // Removes and returns the top element.
    // Input:  None
    // Output: T
    T pop() {
        if (isEmpty()) throw std::underflow_error("Stack is empty");
        return data[topIndex--];
    }

    // Returns a const reference to the top element without removing it.
    // Input:  None
    // Output: const T&
    const T& peek() const {
        if (isEmpty()) throw std::underflow_error("Stack is empty");
        return data[topIndex];
    }

    // Removes all elements by resetting the top index.
    // Input:  None
    // Output: None
    void clear() { topIndex = -1; }

    // Returns the number of elements currently on the stack.
    // Input:  None
    // Output: int
    int size() const { return topIndex + 1; }

    // Returns true if the stack holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return topIndex == -1; }

    // Prints all elements from bottom to top.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        std::cout << "bottom [ ";
        for (int i = 0; i <= topIndex; i++) {
            std::cout << data[i];
            if (i < topIndex) std::cout << ", ";
        }
        std::cout << " ] top\n";
    }
};
