/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Array
 * Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class Array {
private:
    T* data;
    int capacity;
    int length;

    // Doubles the internal storage capacity when the array is full.
    // Input:  None
    // Output: None
    void resize() {
        int newCapacity = capacity * 2;
        T* newData = new T[newCapacity];
        for (int i = 0; i < length; i++) newData[i] = data[i];
        delete[] data;
        data = newData;
        capacity = newCapacity;
    }

public:
    // Initializes the array with a given starting capacity.
    // Input:  int initialCapacity (default 8)
    // Output: None
    explicit Array(int initialCapacity = 8)
        : capacity(initialCapacity), length(0) {
        if (initialCapacity < 1) throw std::invalid_argument("Capacity must be >= 1");
        data = new T[capacity];
    }

    // Releases all heap-allocated memory owned by the array.
    // Input:  None
    // Output: None
    ~Array() { delete[] data; }

    // Copy constructor — performs a deep copy of another array.
    // Input:  const Array<T>& other
    // Output: None
    Array(const Array<T>& other) : capacity(other.capacity), length(other.length) {
        data = new T[capacity];
        for (int i = 0; i < length; i++) data[i] = other.data[i];
    }

    // Copy assignment operator — performs a deep copy and returns a reference.
    // Input:  const Array<T>& other
    // Output: Array<T>&
    Array<T>& operator=(const Array<T>& other) {
        if (this == &other) return *this;
        delete[] data;
        capacity = other.capacity;
        length = other.length;
        data = new T[capacity];
        for (int i = 0; i < length; i++) data[i] = other.data[i];
        return *this;
    }

    // Appends a value to the end of the array, resizing if necessary.
    // Input:  const T& value
    // Output: None
    void append(const T& value) {
        if (length == capacity) resize();
        data[length++] = value;
    }

    // Inserts a value at the specified index, shifting subsequent elements right.
    // Input:  int index, const T& value
    // Output: None
    void insert(int index, const T& value) {
        if (index < 0 || index > length) throw std::out_of_range("Index out of bounds");
        if (length == capacity) resize();
        for (int i = length; i > index; i--) data[i] = data[i - 1];
        data[index] = value;
        length++;
    }

    // Removes and returns the element at the specified index, shifting remaining elements left.
    // Input:  int index
    // Output: T (removed element)
    T remove(int index) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        T removed = data[index];
        for (int i = index; i < length - 1; i++) data[i] = data[i + 1];
        length--;
        return removed;
    }

    // Returns a mutable reference to the element at the given index.
    // Input:  int index
    // Output: T& (mutable reference)
    T& get(int index) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        return data[index];
    }

    // Returns a const reference to the element at the given index.
    // Input:  int index
    // Output: const T& (immutable reference)
    const T& get(int index) const {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        return data[index];
    }

    // Replaces the value at a specified index.
    // Input:  int index, const T& value
    // Output: None
    void set(int index, const T& value) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        data[index] = value;
    }

    // Returns the index of the first occurrence of a value, or -1 if not found.
    // Input:  const T& value
    // Output: int (index or -1)
    int indexOf(const T& value) const {
        for (int i = 0; i < length; i++)
            if (data[i] == value) return i;
        return -1;
    }

    // Resets length to zero without freeing or reallocating capacity.
    // Input:  None
    // Output: None
    void clear() { length = 0; }

    // Returns the number of elements currently stored.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if no elements are stored.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Returns the current allocated capacity.
    // Input:  None
    // Output: int
    int getCapacity() const { return capacity; }

    // Mutable subscript operator for index-based access.
    // Input:  int index
    // Output: T&
    T& operator[](int index) { return get(index); }

    // Const subscript operator for index-based read access.
    // Input:  int index
    // Output: const T&
    const T& operator[](int index) const { return get(index); }

    // Prints all elements to standard output.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        std::cout << "[ ";
        for (int i = 0; i < length; i++) {
            std::cout << data[i];
            if (i < length - 1) std::cout << ", ";
        }
        std::cout << " ]\n";
    }
};
