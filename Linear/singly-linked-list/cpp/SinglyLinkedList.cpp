/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Singly Linked List
 * Description: Personal take on a linear chain of singly-linked nodes supporting O(1) front operations and O(n) indexed traversal.
 */

#pragma once
#include <iostream>
#include <stdexcept>

template <typename T>
class SinglyLinkedList {
private:
    struct Node {
        T data;
        Node* next;
        Node(const T& val) : data(val), next(nullptr) {}
    };

    Node* head;
    Node* tail;
    int length;

public:
    // Initializes an empty list with no nodes.
    // Input:  None
    // Output: None
    SinglyLinkedList() : head(nullptr), tail(nullptr), length(0) {}

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~SinglyLinkedList() { clear(); }

    // Prepends a new value before the current head.
    // Input:  const T& value
    // Output: None
    void addFirst(const T& value) {
        Node* node = new Node(value);
        node->next = head;
        head = node;
        if (!tail) tail = head;
        length++;
    }

    // Appends a new value after the current tail.
    // Input:  const T& value
    // Output: None
    void addLast(const T& value) {
        Node* node = new Node(value);
        if (tail) tail->next = node;
        tail = node;
        if (!head) head = tail;
        length++;
    }

    // Inserts a new value at the specified zero-based index.
    // Input:  int index, const T& value
    // Output: None
    void addAt(int index, const T& value) {
        if (index < 0 || index > length) throw std::out_of_range("Index out of bounds");
        if (index == 0) { addFirst(value); return; }
        if (index == length) { addLast(value); return; }
        Node* prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev->next;
        Node* node = new Node(value);
        node->next = prev->next;
        prev->next = node;
        length++;
    }

    // Removes and returns the first element.
    // Input:  None
    // Output: T
    T removeFirst() {
        if (!head) throw std::underflow_error("List is empty");
        Node* node = head;
        T val = node->data;
        head = head->next;
        if (!head) tail = nullptr;
        delete node;
        length--;
        return val;
    }

    // Traverses to the second-to-last node and removes the tail.
    // Input:  None
    // Output: T
    T removeLast() {
        if (!head) throw std::underflow_error("List is empty");
        if (head == tail) return removeFirst();
        Node* prev = head;
        while (prev->next != tail) prev = prev->next;
        T val = tail->data;
        delete tail;
        tail = prev;
        tail->next = nullptr;
        length--;
        return val;
    }

    // Removes and returns the element at the specified index.
    // Input:  int index
    // Output: T
    T removeAt(int index) {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();
        Node* prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev->next;
        Node* target = prev->next;
        T val = target->data;
        prev->next = target->next;
        delete target;
        length--;
        return val;
    }

    // Returns a const reference to the element at the specified index.
    // Input:  int index
    // Output: const T&
    const T& get(int index) const {
        if (index < 0 || index >= length) throw std::out_of_range("Index out of bounds");
        Node* curr = head;
        for (int i = 0; i < index; i++) curr = curr->next;
        return curr->data;
    }

    // Returns a const reference to the head element without removal.
    // Input:  None
    // Output: const T&
    const T& first() const {
        if (!head) throw std::underflow_error("List is empty");
        return head->data;
    }

    // Returns a const reference to the tail element without removal.
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

    // Reverses the order of all nodes in place using iterative pointer reassignment.
    // Input:  None
    // Output: None
    void reverse() {
        Node* prev = nullptr;
        Node* curr = head;
        tail = head;
        while (curr) {
            Node* next = curr->next;
            curr->next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
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

    // Returns the number of elements currently in the list.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if the list holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Prints elements from head to tail separated by arrows.
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = head;
        while (curr) {
            std::cout << curr->data;
            if (curr->next) std::cout << " -> ";
            curr = curr->next;
        }
        std::cout << " -> null\n";
    }
};
