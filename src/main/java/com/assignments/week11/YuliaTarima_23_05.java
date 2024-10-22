// -----------------------------------------------//
// MergeSort method that recursively sorts -------//
// the first and the second half of the array ----//
// without creating new temporary arrays. --------//
// Then it merges the two into a temporary array -//
// and copies its contents to the original array -//
// -----------------------------------------------//

package com.assignments.week11;

public class YuliaTarima_23_05 {

    public static void inPlaceMergeSort(int[] list) {
        // Pass the full array to the recursive function
        inPlaceMergeSort(list, 0, list.length - 1);
    }

    /**
     * Recursive inPlaceMergeSort that operates within a range [leftBoundary, rightBoundary] in the original array
     * leftBoundary: the index of the first element in the current portion of the array.
     * rightBoundary: the index of the last element in the current portion of the array.
     */
    private static void inPlaceMergeSort(int[] list, int leftBoundary, int rightBoundary) {
        // ensure array portion being processed contains > 1 element
        if (leftBoundary < rightBoundary) {
            // center index of the current portion of the array
            int mid = (leftBoundary + rightBoundary) / 2;

            // Recursively sort the first half of the array
            inPlaceMergeSort(list, leftBoundary, mid);

            // Recursively sort the second half of the array
            inPlaceMergeSort(list, mid + 1, rightBoundary);

            // Merge the two halves
            merge(list, leftBoundary, mid, rightBoundary);
        }
    }

    // Merge two sorted halves defined by indices [left, mid] and [mid+1, right]
    private static void merge(int[] list, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];  // Temporary array to store the merged result
        int currentLeft = left;      // Pointer for the left half
        int currentRight = mid + 1;   // Pointer for the right half
        int currentTemp = 0;      // Pointer for the temp array

        // Merge the two halves into the temporary array
        while (currentLeft <= mid && currentRight <= right) {
            if (list[currentLeft] <= list[currentRight]) {
                temp[currentTemp++] = list[currentLeft++];
            } else {
                temp[currentTemp++] = list[currentRight++];
            }
        }

        // Copy any remaining elements from the left half
        while (currentLeft <= mid) {
            temp[currentTemp++] = list[currentLeft++];
        }

        // Copy any remaining elements from the right half
        while (currentRight <= right) {
            temp[currentTemp++] = list[currentRight++];
        }

        // Copy the merged result back into the original array
        System.arraycopy(temp, 0, list, left, temp.length);
    }

    public static void main(String[] args) {
        int[] list = {2, 3, 2, 5, 6, 1, 0, -2, 3, 14, 12};

        // Perform the in-place merge sort on the array
        inPlaceMergeSort(list);

        // Print the sorted array
        for (int j : list) {
            System.out.print(j + " ");
        }
    }
}