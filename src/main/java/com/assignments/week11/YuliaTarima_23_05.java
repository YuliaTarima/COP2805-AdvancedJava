package com.assignments.week11;

public class YuliaTarima_23_05 {

    public static void mergeSort(int[] list) {
        // If the array has more than one element, proceed with sorting
        if (list.length > 1) {
            // Split the array into two halves and recursively sort the first half
            int[] LeftHalfOfArray = new int[list.length / 2];
            System.arraycopy(list, 0, LeftHalfOfArray, 0, list.length / 2);
            mergeSort(LeftHalfOfArray);

            // Split the array into two halves and recursively sort the second half
            int RightHalfLength = list.length - list.length / 2;
            int[] RightHalfOfArray = new int[RightHalfLength];
            System.arraycopy(list, list.length / 2, RightHalfOfArray, 0, RightHalfLength);
            mergeSort(RightHalfOfArray);

            // Merge the two halves into the original array
            merge(LeftHalfOfArray, RightHalfOfArray, list);
        }
    }

    /**
     * Merges two sorted arrays (list1 and list2) into the temp array.
     * This function assumes that list1 and list2 are already sorted.
     */
    public static void merge(int[] list1, int[] list2, int[] temp) {
        // Initialize index for the left half of the array
        int CurrentPositionInLeftArray = 0;

        // Initialize index for the right half of the array
        int CurrentPositionInRightArray = 0;

        // Initialize index for the merged array (temp)
        int CurrentPositionInMergedArray = 0;

        // Compare elements from both halves and copy the smaller element to the temp array
        while (CurrentPositionInLeftArray < list1.length && CurrentPositionInRightArray < list2.length) {
            if (list1[CurrentPositionInLeftArray] < list2[CurrentPositionInRightArray])
                temp[CurrentPositionInMergedArray++] = list1[CurrentPositionInLeftArray++];
            else
                temp[CurrentPositionInMergedArray++] = list2[CurrentPositionInRightArray++];
        }

        // Copy any remaining elements from the left half to the temp array
        while (CurrentPositionInLeftArray < list1.length)
            temp[CurrentPositionInMergedArray++] = list1[CurrentPositionInLeftArray++];

        // Copy any remaining elements from the right half to the temp array
        while (CurrentPositionInRightArray < list2.length)
            temp[CurrentPositionInMergedArray++] = list2[CurrentPositionInRightArray++];
    }

    /** A test method to demonstrate the merge sort */
    public static void main(String[] args) {
        // Example array for sorting
        int[] list = {2, 3, 2, 5, 6, 1, 0, -2, 3, 14, 12};

        // Perform merge sort on the array
        mergeSort(list);

        // Print the sorted array
        for (int j : list) System.out.print(j + " ");
    }
}