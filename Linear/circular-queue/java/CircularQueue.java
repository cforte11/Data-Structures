/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Circular Queue
 * Description: Personal take on a fixed-capacity FIFO queue using a circular array with modular index arithmetic.
 */

public class CircularQueue<T> {

    private final Object[] data;
    private final int capacity;
    private int front;
    private int length;

    // Initializes a circular queue with a fixed capacity.
    // Input:  capacity (int)
    // Output: void
    public CircularQueue(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("Capacity must be >= 1");
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.length = 0;
    }

    // Adds a value to the back, wrapping around via modular arithmetic.
    // Input:  value (T)
    // Output: void
    public void enqueue(T value) {
        if (isFull()) throw new IllegalStateException("Queue is full");
        int back = (front + length) % capacity;
        data[back] = value;
        length++;
    }

    // Removes and returns the front element, advancing the front index circularly.
    // Input:  none
    // Output: T
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        T val = (T) data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        length--;
        return val;
    }

    // Returns the front element without removing it.
    // Input:  none
    // Output: T
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        return (T) data[front];
    }

    // Returns the back element without removing it.
    // Input:  none
    // Output: T
    @SuppressWarnings("unchecked")
    public T back() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        return (T) data[(front + length - 1) % capacity];
    }

    // Resets all slots, front index, and length.
    // Input:  none
    // Output: void
    public void clear() {
        java.util.Arrays.fill(data, null);
        front = 0;
        length = 0;
    }

    // Returns the number of elements currently in the queue.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if no elements are present.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns true if the queue has reached its fixed capacity.
    // Input:  none
    // Output: boolean
    public boolean isFull() { return length == capacity; }

    // Returns a string of elements in FIFO order from front to back.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("front [ ");
        for (int i = 0; i < length; i++) {
            sb.append(data[(front + i) % capacity]);
            if (i < length - 1) sb.append(", ");
        }
        return sb.append(" ] back").toString();
    }
}
