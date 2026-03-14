/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: Array
 * Description: Personal take on a fixed-size, index-based linear data structure with dynamic resizing support.
 */

public class DynamicArray<T> {

    private Object[] data;
    private int capacity;
    private int length;

    // Initializes the array with a given starting capacity.
    // Input:  initialCapacity (int, default 8)
    // Output: void
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) throw new IllegalArgumentException("Capacity must be >= 1");
        this.capacity = initialCapacity;
        this.length = 0;
        this.data = new Object[this.capacity];
    }

    public DynamicArray() { this(8); }

    // Doubles the internal storage capacity when the array is full.
    // Input:  none
    // Output: void
    private void resize() {
        int newCapacity = capacity * 2;
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, length);
        data = newData;
        capacity = newCapacity;
    }

    // Validates that an index falls within the current element range.
    // Input:  index (int)
    // Output: void (throws IndexOutOfBoundsException on violation)
    private void checkBounds(int index) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
    }

    // Appends a value to the end of the array, resizing if necessary.
    // Input:  value (T)
    // Output: void
    public void append(T value) {
        if (length == capacity) resize();
        data[length++] = value;
    }

    // Inserts a value at the specified index, shifting subsequent elements right.
    // Input:  index (int), value (T)
    // Output: void
    public void insert(int index, T value) {
        if (index < 0 || index > length)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        if (length == capacity) resize();
        System.arraycopy(data, index, data, index + 1, length - index);
        data[index] = value;
        length++;
    }

    // Removes and returns the element at the specified index, shifting remaining elements left.
    // Input:  index (int)
    // Output: T
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkBounds(index);
        T removed = (T) data[index];
        System.arraycopy(data, index + 1, data, index, length - index - 1);
        data[--length] = null;
        return removed;
    }

    // Returns the element at the given index.
    // Input:  index (int)
    // Output: T
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkBounds(index);
        return (T) data[index];
    }

    // Replaces the value at the specified index.
    // Input:  index (int), value (T)
    // Output: void
    public void set(int index, T value) {
        checkBounds(index);
        data[index] = value;
    }

    // Returns the index of the first occurrence of a value, or -1 if not found.
    // Input:  value (T)
    // Output: int
    public int indexOf(T value) {
        for (int i = 0; i < length; i++)
            if (data[i] != null && data[i].equals(value)) return i;
        return -1;
    }

    // Resets length to zero without freeing capacity.
    // Input:  none
    // Output: void
    public void clear() {
        for (int i = 0; i < length; i++) data[i] = null;
        length = 0;
    }

    // Returns the number of elements currently stored.
    // Input:  none
    // Output: int
    public int size() { return length; }

    // Returns true if no elements are stored.
    // Input:  none
    // Output: boolean
    public boolean isEmpty() { return length == 0; }

    // Returns the current allocated capacity.
    // Input:  none
    // Output: int
    public int capacity() { return capacity; }

    // Returns a string representation of all stored elements.
    // Input:  none
    // Output: String
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < length; i++) {
            sb.append(data[i]);
            if (i < length - 1) sb.append(", ");
        }
        return sb.append(" ]").toString();
    }
}
