// -----------------------------------------//
// Implement methods addAll, removeAll, ----//
// retainAll, toArray(), and toArray(T[]) --//
// required for the MyList class -----------//
// -----------------------------------------//

package com.assignments.week12;

import java.util.*;

// Interface defining the structure of MyList
interface MyList<E> extends java.util.Collection<E> {
    /**
     * Add a new element at the specified index in this list
     */
    public void add(int index, E e);

    /**
     * Return the element from this list at the specified index
     */
    public E get(int index);

    /**
     * Return the index of the first matching element in this list.
     * Return -1 if no match.
     */
    public int indexOf(Object e);

    /**
     * Return the index of the last matching element in this list
     * Return -1 if no match.
     */
    public int lastIndexOf(E e);

    /**
     * Remove the element at the specified position in this list
     * Shift any subsequent elements to the left.
     * Return the element that was removed from the list.
     */
    public E remove(int index);

    /**
     * Replace the element at the specified position in this list
     * with the specified element and returns the new set.
     */
    public E set(int index, E e);

    @Override
    /** Add a new element at the end of this list */
    public default boolean add(E e) {
        add(size(), e); // Call the add method at the end
        return true; // Indicate successful addition
    }

    @Override
    /** Return true if this list contains no elements */
    public default boolean isEmpty() {
        return size() == 0; // Check if size is zero
    }

    @Override
    /** Remove the first occurrence of the element e
     * from this list. Shift any subsequent elements to the left.
     * Return true if the element is removed. */
    public default boolean remove(Object e) {
        // If the element is found, remove it and return true
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        } else
            return false; // Element not found
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        // Check if all elements in collection c are contained in this list
        for (Object e : c)
            if (!this.contains(e))
                return false; // If any element is missing, return false
        return true; // All elements found
    }

    /**
     * Adds the elements in otherList to this list.
     * Returns true if this list changed as a result of the call
     */
    @Override
    public default boolean addAll(Collection<? extends E> c) {
        boolean isModified = false; // Track if the list is modified
        for (E e : c) { // Iterate over each element in the collection
            if (!contains(e)) { // Check if the element is not already present
                add(e); // Add the element
                isModified = true; // Mark that the list has been changed
            }
        }
        return isModified; // Return true if list changed
    }

    /**
     * Removes all the elements in otherList from this list
     * Returns true if this list changed as a result of the call
     */
    @Override
    public default boolean removeAll(Collection<?> c) {
        boolean isModified = false; // Track if the list is modified
        for (Object element : c) {
            if (remove(element)) { // Use the remove method
                isModified = true; // Mark the list as modified
            }
        }
        return isModified; // Return true if list changed
    }

    /**
     * Retains the elements in this list that are also in otherList
     * Returns true if this list changed as a result of the call
     */
    @Override
    public default boolean retainAll(Collection<?> c) {
        boolean isModified = false; // Track if the list is modified
        for (int i = size() - 1; i >= 0; i--) { // Iterate backwards
            if (!c.contains(get(i))) { // Check if the element is not in the collection
                remove(i); // Remove the element
                isModified = true; // Mark the list as modified
            }
        }
        return isModified; // Return true if list changed
    }

    // convert the elements of a collection (like a list) into an array of Object.
    @Override
    public default Object[] toArray() {
        Object[] result = new Object[size()]; // Create an Object array
        for (int i = 0; i < size(); i++) {
            result[i] = get(i); // Assuming get(i) retrieves the element at index i
        }
        return result; // Return the populated array
    }

    // Converts elements of a collection into an array of a specified type T.
    // It takes an array of type T as parameter and populates it with elements of the collection.
    @Override
    public default <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            // If the provided array is too small, create a new one
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
        }
        for (int i = 0; i < size(); i++) {
            a[i] = (T) get(i); // Populate the array with elements
        }
        return a; // Return the populated array
    }
}

// Main class for the assignment
public class YuliaTarima_24_01 {

    // Constructor for the class YuliaTarima_24_01
    public YuliaTarima_24_01() {
        // Create a Scanner object to read user input from the console
        Scanner input = new Scanner(System.in);

        // Initialize arrays to hold user input strings
        String[] strArray1 = new String[5];
        String[] strArray2 = new String[5];
        String[] strArray3 = new String[2];

        // Prompt user to enter five strings for the first array
        System.out.print("Enter five strings separated by space for the first array: ");
        // Loop to read five strings into strArray1
        for (int i = 0; i < 5; i++) {
            // Read each string
            strArray1[i] = input.next();
        }

        // Prompt user to enter five strings for the second array
        System.out.print("Enter five strings separated by space for the second array: ");
        // Loop to read five strings into strArray2
        for (int i = 0; i < 5; i++) {
            // Read each string
            strArray2[i] = input.next();
        }

        // Prompt user to enter two strings for the third array
        System.out.print("Enter two strings separated by space for the third array: ");
        // Loop to read two strings into strArray3
        for (int i = 0; i < 2; i++) {
            // Read each string
            strArray3[i] = input.next();
        }

        // Create MyArrayList instance for list1, list2, and list3
        MyList<String> list1 = new MyArrayList<>(strArray1);
        MyList<String> list2 = new MyArrayList<>(strArray2);
        MyList<String> list3 = new MyArrayList<>(strArray3);

        System.out.println("\n---------------*********-----------------");
        System.out.println("Add to list1 only distinct elements from list2");
        // Display list1 and list2
        System.out.println("list1: " + list1);
        System.out.println("list2: " + list2);
        // Add all distinct elements from list2 to list1
        list1.addAll(list2);
        // Display list1 result after addAll
        System.out.println("list1 after addAll: " + list1);

        System.out.println("\n---------------*********-----------------");
        System.out.println("Keep in list1 only distinct elements that are not present in list2");
        // Re-initialize list1 and list2 for the new operation
        list1 = new MyArrayList<>(strArray1);
        list2 = new MyArrayList<>(strArray2);
        // Display list1 and list2
        System.out.println("list1: " + list1);
        System.out.println("list2: " + list2);
        // Remove from list1 all elements that are also present in list2
        list1.removeAll(list2);
        // Display resulting list1 after removeAll
        System.out.println("list1 after removeAll: " + list1);

        System.out.println("\n---------------*********-----------------");
        System.out.println("Retain in list1 only elements that are also present in list2");
        // Re-initialize list1 and list2 for the new operation
        list1 = new MyArrayList<>(strArray1);
        list2 = new MyArrayList<>(strArray2);
        // Display list1 and list2
        System.out.println("list1: " + list1);
        System.out.println("list2: " + list2);
        // Retain in list1 only the elements that are also in list2
        list1.retainAll(list2);
        // Display resulting list1 after retainAll
        System.out.println("list1 after retainAll: " + list1);


        System.out.println("\n---------------*********-----------------");
        System.out.println("Display true if list1 contains all elements from list2");
        // Re-initialize list1 and list2
        list1 = new MyArrayList<>(strArray1);
        list2 = new MyArrayList<>(strArray2);
        // Display the contents of list1 and list2
        System.out.println("list1: " + list1);
        System.out.println("list2: " + list2);
        // Check if list1 contains all elements from list2
        System.out.println("list1 containsAll from list2: " + list1.containsAll(list2));

        System.out.println("\n---------------*********-----------------");
        System.out.println("Check if list1 contains all elements from list3");
        // Re-initialize list1 for a new operation
        list1 = new MyArrayList<>(strArray1);
        // Display the contents of list1 and list3
        System.out.println("list1: " + list1);
        System.out.println("list3: " + list3);
        // Check if list1 contains all elements from list3
        System.out.println("list1 containsAll from list3: " + list1.containsAll(list3));

        System.out.println("\n---------------*********-----------------");
        System.out.println("Convert list1 to an Object array");
        Object[] objArr = list1.toArray();
        // Display list1 and objArr
        System.out.println("list1: " + list1);
        System.out.print("objArr: ");
        for (Object e : objArr) {
            // Print each element of objArr
            System.out.print(e + " ");
        }

        System.out.println("\n\n---------------*********-----------------");
        System.out.println("Convert list1 to a String array");
        // Create an empty String array for strArrForList1
        String[] strArrForList1 = new String[list1.size()];
        // Convert list1 to a String array (strArr)
        String[] strArr = list1.toArray(strArrForList1);
        // Display list1 and strArr
        System.out.println("list1: " + list1);
        System.out.print("strArr: ");
        for (Object e : strArr) {
            // Print each element of strArr
            System.out.print(e + " ");
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        // Create an instance of YuliaTarima_24_01 to execute the constructor
        new YuliaTarima_24_01();
    }
}

// Class implementing a custom dynamic array list
class MyArrayList<E> implements MyList<E> {
    // Initial capacity for the array
    public static final int INITIAL_CAPACITY = 16;

    // Array to store the elements of the list
    private E[] data = (E[]) new Object[INITIAL_CAPACITY];
    // Variable to track the number of elements in the list
    private int size = 0;

    /**
     * Create an empty list
     */
    public MyArrayList() {
    }

    /**
     * Create a list from an array of objects
     */
    public MyArrayList(E[] objects) {
        // Loop through the input array and add each element to the list
        for (int i = 0; i < objects.length; i++)
            add(objects[i]); // Warning: don't use super(objects)!
    }

    @Override
    /** Add a new element at the specified index */
    public void add(int index, E e) {
        // Ensure there is enough capacity to add a new element
        ensureCapacity();

        // Move the elements to the right after the specified index
        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];

        // Insert new element at the specified index
        data[index] = e;
        // Increase size by 1
        size++;
    }

    /**
     * Create a new larger array, doubling the current size + 1
     */
    private void ensureCapacity() {
        // Check if the current size exceeds the capacity
        if (size >= data.length) {
            // Create a new array with double the current size plus one
            E[] newData = (E[]) (new Object[size * 2 + 1]);
            // Copy existing data to the new array
            System.arraycopy(data, 0, newData, 0, size);
            // Update the reference to the new array
            data = newData;
        }
    }

    @Override
    /** Clear the list */
    public void clear() {
        // Reset the data array to a new array with initial capacity
        data = (E[]) new Object[INITIAL_CAPACITY];
        // Reset the size to zero
        size = 0;
    }

    @Override
    /** Return true if this list contains the element */
    public boolean contains(Object e) {
        // Loop through the list to check for the existence of the element
        for (int i = 0; i < size; i++)
            if (e.equals(data[i])) return true; // Element found
        return false; // Element not found
    }

    @Override
    /** Return the element at the specified index */
    public E get(int index) {
        // Check if the index is valid
        checkIndex(index);
        // Return the element at the specified index
        return data[index];
    }

    // Method to check if the index is within valid bounds
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
    }

    @Override
    /** Return the index of the first matching element
     * in this list. Return -1 if no match. */
    public int indexOf(Object e) {
        // Loop through the list to find the first occurrence of the element
        for (int i = 0; i < size; i++)
            if (e.equals(data[i])) return i; // Element found
        return -1; // Element not found
    }

    @Override
    /** Return the index of the last matching element
     * in this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
        // Loop backwards to find the last occurrence of the element
        for (int i = size - 1; i >= 0; i--)
            if (e.equals(data[i])) return i; // Element found
        return -1; // Element not found
    }

    @Override
    /** Remove the element at the specified position
     * in this list. Shift any subsequent elements to the left.
     * Return the element that was removed from the list. */
    public E remove(int index) {
        // Check if the index is valid
        checkIndex(index);
        // Store the element to be removed
        E e = data[index];

        // Shift data to the left to fill the gap
        for (int j = index; j < size - 1; j++)
            data[j] = data[j + 1];
        // Set the last element to null as it's been removed
        data[size - 1] = null;
        // Decrease the size of the list
        size--;
        // Return the removed element
        return e;
    }

    @Override
    /** Replace the element at the specified position
     * in this list with the specified element. */
    public E set(int index, E e) {
        // Check if the index is valid
        checkIndex(index);
        // Store the old element
        E old = data[index];
        // Replace with the new element
        data[index] = e;
        // Return the old element
        return old;
    }

    @Override
    public String toString() {
        // Create a StringBuilder to build the string representation
        StringBuilder result = new StringBuilder("[");
        // Loop through the elements to append to the result
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i < size - 1) result.append(", "); // Add comma for separation
        }
        // Return the string representation
        return result.toString() + "]";
    }

    /**
     * Trims the capacity to the current size
     */
    public void trimToSize() {
        // Check if the size is different from the current capacity
        if (size != data.length) {
            // Create a new array with the current size
            E[] newData = (E[]) (new Object[size]);
            // Copy existing data to the new array
            System.arraycopy(data, 0, newData, 0, size);
            // Update the reference to the new array
            data = newData;
        } // If size == capacity, no need to trim
    }

    @Override
    /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        // Return a new ArrayListIterator for this MyArrayList
        return new ArrayListIterator();
    }

    @Override
    /** Return the number of elements in this list */
    public int size() {
        return size; // Return the current size
    }

    // Inner class to implement an iterator for MyArrayList
    private class ArrayListIterator implements java.util.Iterator<E> {
        private int current = 0; // Current index for iteration

        @Override
        public boolean hasNext() {
            // Check if there are more elements to iterate
            return (current < size);
        }

        @Override
        public E next() {
            // Return the current element and move to the next
            return data[current++];
        }

        @Override
        public void remove() {
            // Check if next() has been called
            if (current == 0) // next() has not been called yet
                throw new IllegalStateException();
            // Remove the current element and update the iterator's position
            MyArrayList.this.remove(--current);
        }
    }
}