package com.assignments.week06;
import java.util.Scanner;

public class YuliaTarima_18_09 {

        // Recursive method to reverse and display a string
        public static void reverseDisplay(String value) {
            if (value.isEmpty()) {
                return;
            }
            // Display the last character
            System.out.print(value.charAt(value.length() - 1));
            // Recursively call the function with the rest of the string
            reverseDisplay(value.substring(0, value.length() - 1));
        }

        public static void main(String[] args) {
            // Create a Scanner object for user input
            Scanner input = new Scanner(System.in);

            // Prompt user to enter a string
            System.out.print("Enter a string: ");
            String value = input.nextLine();

            // Call reverseDisplay to display the string in reverse
            System.out.print("Reversed string: ");
            reverseDisplay(value);
            System.out.println();
        }
    }