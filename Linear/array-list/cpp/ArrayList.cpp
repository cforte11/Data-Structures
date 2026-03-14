/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: ArrayList
 * Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class ArrayList {
private:
    T* data;
    int capacity;
    int length;

    // Doubles capacity and migrates all elements to a new backing array.
    // Input:  None
    // Output: None
    void resize() {
        int newCap = capacity * 2;
        T* newData = new T[newCap];
        for (int i = 0; i < length; i++) newData[i] = data[i];
        delete[] data;
        data = newData;
        capacity = newCap;
    }

    // Validates that an index falls within current list bounds.
    // Input:  int index
    // Output: None (throws on violation)
    void checkBounds(int index) const {
        if (index < 0 || index >= length)
            throw std::out_of_range("Index out of bounds");
    }

public:
    // Initializes the list with an optional starting capacity.
    // Input:  int initialCapacity (default 8)
    // Output: None
    explicit ArrayList(int initialCapacity = 8) : capacity(initialCapacity), length(0) {
        if (initialCapacity < 1) throw std::invalid_argument("Capacity must be >= 1");
        data = new T[capacity];
    }

    // Releases heap-allocated backing array.
    // Input:  None
    // Output: None
    ~ArrayList() { delete[] data; }

    // Appends a value to the end, resizing if necessary.
    // Input:  const T& value
    // Output: None
    void add(const T& value) {
        if (length == capacity) resize();
        data[length++] = value;
    }

    // Inserts a value at the specified index, shifting elements right.
    // Input:  int index, const T& value
    // Output: None
    void add(int index, const T& value) {
        if (index < 0 || index > length) throw std::out_of_range("Index out of bounds");
        if (length == capacity) resize();
        for (int i = length; i > index; i--) data[i] = data[i - 1];
        data[index] = value;
        length++;
    }

    // Removes and returns the element at the given index, shifting elements left.
    // Input:  int index
    // Output: T
    T remove(int index) {
        checkBounds(index);
        T val = data[index];
        for (int i = index; i < length - 1; i++) data[i] = data[i + 1];
        length--;
        return val;
    }

    // Returns a mutable reference to the element at the given index.
    // Input:  int index
    // Output: T&
    T& get(int index) {
        checkBounds(index);
        return data[index];
    }

    // Returns a const reference to the element at the given index.
    // Input:  int index
    // Output: const T&
    const T& get(int index) const {
        checkBounds(index);
        return data[index];
    }

    // Replaces the element at the given index with a new value.
    // Input:  int index, const T& value
    // Output: None
    void set(int index, const T& value) {
        checkBounds(index);
        data[index] = value;
    }

    // Returns the index of the first matching element, or -1 if absent.
    // Input:  const T& value
    // Output: int
    int indexOf(const T& value) const {
        for (int i = 0; i < length; i++)
            if (data[i] == value) return i;
        return -1;
    }

    // Returns true if the list contains the given value.
    // Input:  const T& value
    // Output: bool
    bool contains(const T& value) const { return indexOf(value) != -1; }

    // Resets length to zero, retaining allocated capacity.
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

    // Mutable subscript operator for index-based element access.
    // Input:  int index
    // Output: T&
    T& operator[](int index) { return get(index); }

    // Const subscript operator for read-only index-based access.
    // Input:  int index
    // Output: const T&
    const T& operator[](int index) const { return get(index); }

    // Prints all elements in order.
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
