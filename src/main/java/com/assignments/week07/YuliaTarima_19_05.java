package com.assignments.week07;

import java.util.Scanner;

public class YuliaTarima_19_05 {

    // Generic method to find the maximum element in an array
    public static <E extends Comparable<E>> E max(E[] list) {
        // Start with the first element as the maximum
        E maxElement = list[0];

        // Compare each element to find the maximum
        for (int i = 1; i < list.length; i++) {
            if (list[i].compareTo(maxElement) > 0) {
                maxElement = list[i];
            }
        }
        return maxElement;
    }

    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner input = new Scanner(System.in);

        // Create an array to store 10 integers
        Integer[] intArray = new Integer[10];

        // Prompt user to enter 10 integers
        System.out.println("Enter 10 integers:");
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = input.nextInt();
        }

        // Call the generic max method and display the result
        Integer maxValue = max(intArray);
        System.out.println("The maximum value is: " + maxValue);
    }
}