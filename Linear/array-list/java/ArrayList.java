/*
 * Author: Christopher Forte
 * Date: March 14, 2026
 * Title: ArrayList
 * Description: Personal take on a dynamic array implementing the List ADT with amortized O(1) append and O(1) indexed access.
 */

public class ArrayList<T> {

    private Object[] data;
    private int capacity;
    private int length;

    // Initializes the list with an optional starting capacity.
    // Input:  initialCapacity (int, default 8)
    // Output: void
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 1) throw new IllegalArgumentException("Capacity must be >= 1");
        this.capacity = initialCapacity;
        this.length = 0;
        this.data = new Object[this.capacity];
    }

    public ArrayList() { this(8); }

    // Doubles the internal array capacity and migrates all elements.
    // Input:  none
    // Output: void
    private void resize() {
        int newCap = capacity * 2;
        Object[] newData = new Object[newCap];
        System.arraycopy(data, 0, newData, 0, length);
        data = newData;
        capacity = newCap;
    }

    // Validates that an index falls within the current element range.
    // Input:  index (int)
    // Output: void (throws IndexOutOfBoundsException on violation)
    private void checkBounds(int index) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Index: " + index);
    }

    // Appends to the end or inserts at the given index, shifting elements right.
    // Input:  value (T), index (int, optional — pass -1 to append)
    // Output: void
    public void add(T value) {
        if (length == capacity) resize();
        data[length++] = value;
    }

    // Inserts a value at the specified index, shifting subsequent elements right.
    // Input:  index (int), value (T)
    // Output: void
    public void add(int index, T value) {
        if (index < 0 || index > length) throw new IndexOutOfBoundsException("Index: " + index);
        if (length == capacity) resize();
        System.arraycopy(data, index, data, index + 1, length - index);
        data[index] = value;
        length++;
    }

    // Removes and returns the element at the given index, shifting elements left.
    // Input:  index (int)
    // Output: T
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkBounds(index);
        T val = (T) data[index];
        System.arraycopy(data, index + 1, data, index, length - index - 1);
        data[--length] = null;
        return val;
    }

    // Returns the element at the given index.
    // Input:  index (int)
    // Output: T
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkBounds(index);
        return (T) data[index];
    }

    // Replaces the element at the given index with a new value.
    // Input:  index (int), value (T)
    // Output: void
    public void set(int index, T value) {
        checkBounds(index);
        data[index] = value;
    }

    // Returns the index of the first matching element, or -1 if absent.
    // Input:  value (T)
    // Output: int
    public int indexOf(T value) {
        for (int i = 0; i < length; i++)
            if (data[i] != null && data[i].equals(value)) return i;
        return -1;
    }

    // Returns true if the list contains the given value.
    // Input:  value (T)
    // Output: boolean
    public boolean contains(T value) { return indexOf(value) != -1; }

    // Resets length to zero, retaining allocated capacity.
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
