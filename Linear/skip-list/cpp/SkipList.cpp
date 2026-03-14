/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Skip List
 * Description: Personal take on a probabilistic, layered linked structure enabling expected O(log n) search, insert, and remove on a sorted sequence.
 */

#pragma once
#include <iostream>
#include <vector>
#include <climits>
#include <cstdlib>
#include <ctime>
#include <stdexcept>

template <typename T>
class SkipList {
private:
    static const int MAX_LEVEL = 16;
    static constexpr double PROB = 0.5;

    struct Node {
        T key;
        int level;
        std::vector<Node*> forward;
        Node(const T& k, int lvl) : key(k), level(lvl), forward(lvl + 1, nullptr) {}
    };

    Node* header;
    int currentLevel;
    int length;

    // Generates a random level for a new node using geometric distribution.
    // Input:  None
    // Output: int (level between 0 and MAX_LEVEL-1)
    int randomLevel() const {
        int lvl = 0;
        while ((double)std::rand() / RAND_MAX < PROB && lvl < MAX_LEVEL - 1) lvl++;
        return lvl;
    }

    // Creates a sentinel header node with the minimum possible key value.
    // Input:  None
    // Output: Node*
    Node* makeHeader() {
        Node* h = new Node(T{}, MAX_LEVEL - 1);
        return h;
    }

public:
    // Initializes the skip list with a sentinel header and seeds the RNG.
    // Input:  None
    // Output: None
    SkipList() : currentLevel(0), length(0) {
        std::srand(static_cast<unsigned>(std::time(nullptr)));
        header = makeHeader();
    }

    // Releases all heap-allocated nodes.
    // Input:  None
    // Output: None
    ~SkipList() { clear(); delete header; }

    // Searches for a key and returns true if it exists in the list.
    // Input:  const T& key
    // Output: bool
    bool contains(const T& key) const {
        Node* curr = header;
        for (int i = currentLevel; i >= 0; i--) {
            while (curr->forward[i] && curr->forward[i]->key < key)
                curr = curr->forward[i];
        }
        curr = curr->forward[0];
        return curr && curr->key == key;
    }

    // Inserts a key into the skip list at the correct sorted position.
    // Input:  const T& key
    // Output: None
    void insert(const T& key) {
        std::vector<Node*> update(MAX_LEVEL, nullptr);
        Node* curr = header;
        for (int i = currentLevel; i >= 0; i--) {
            while (curr->forward[i] && curr->forward[i]->key < key)
                curr = curr->forward[i];
            update[i] = curr;
        }
        curr = curr->forward[0];
        if (curr && curr->key == key) return;
        int newLevel = randomLevel();
        if (newLevel > currentLevel) {
            for (int i = currentLevel + 1; i <= newLevel; i++) update[i] = header;
            currentLevel = newLevel;
        }
        Node* node = new Node(key, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            node->forward[i] = update[i]->forward[i];
            update[i]->forward[i] = node;
        }
        length++;
    }

    // Removes the node with the given key if it exists.
    // Input:  const T& key
    // Output: None
    void remove(const T& key) {
        std::vector<Node*> update(MAX_LEVEL, nullptr);
        Node* curr = header;
        for (int i = currentLevel; i >= 0; i--) {
            while (curr->forward[i] && curr->forward[i]->key < key)
                curr = curr->forward[i];
            update[i] = curr;
        }
        curr = curr->forward[0];
        if (!curr || curr->key != key) return;
        for (int i = 0; i <= currentLevel; i++) {
            if (update[i]->forward[i] != curr) break;
            update[i]->forward[i] = curr->forward[i];
        }
        delete curr;
        while (currentLevel > 0 && !header->forward[currentLevel]) currentLevel--;
        length--;
    }

    // Removes all nodes and resets the list to its initial empty state.
    // Input:  None
    // Output: None
    void clear() {
        Node* curr = header->forward[0];
        while (curr) {
            Node* temp = curr;
            curr = curr->forward[0];
            delete temp;
        }
        for (int i = 0; i < MAX_LEVEL; i++) header->forward[i] = nullptr;
        currentLevel = 0;
        length = 0;
    }

    // Returns the number of elements stored in the skip list.
    // Input:  None
    // Output: int
    int size() const { return length; }

    // Returns true if the skip list holds no elements.
    // Input:  None
    // Output: bool
    bool isEmpty() const { return length == 0; }

    // Prints all keys at level 0 (the base sorted sequence).
    // Input:  None
    // Output: None (stdout)
    void print() const {
        Node* curr = header->forward[0];
        std::cout << "[ ";
        while (curr) {
            std::cout << curr->key;
            if (curr->forward[0]) std::cout << ", ";
            curr = curr->forward[0];
        }
        std::cout << " ]\n";
    }
};
