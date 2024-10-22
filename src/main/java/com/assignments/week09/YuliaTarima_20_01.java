//-----------------------------------------------------//
// This program that reads words from a text file------//
// and displays all the words--------------------------//
// in ascending alphabetical order.--------------------//
// Duplicates are allowed.-----------------------------//
// The words must start with a letter.-----------------//
// The text file is passed as a command line argument.--//

// cd into src/main/java from the root folder
// Run the program with the following console command:
// java com/assignments/week09/YuliaTarima_20_01 com/assignments/week09/assignment20.txt
package com.assignments.week09;

import java.io.*;
import java.util.*;

public class YuliaTarima_20_01 {

    public static void main(String[] args) {
        // Variable to hold the filename
        String fileName = null;
        boolean validFile = false; // Flag to check if the file is valid

        // Check if a filename is passed as a command-line argument
        if (args.length == 1) {
            fileName = args[0]; // Use the command-line argument as the filename
            validFile = checkFileExists(fileName); // Check if the file exists
        }

        // If no valid file is provided, prompt the user for input until a valid file is entered
        Scanner scanner = new Scanner(System.in);
        while (!validFile) {
            if (fileName == null) {
                System.out.print("Please provide the file to read from: ");
                fileName = scanner.nextLine(); // Read the filename from the user
            } else {
                // This block should only run if args had no filename
                System.out.println("The file does not exist." +
                        "\nSuggested Usage: src/main/java/com/assignments/week09/assignment20.txt" +
                        "\nPlease try again.");
                fileName = scanner.nextLine(); // Read the filename from the user
            }
            validFile = checkFileExists(fileName); // Check if the file exists
        }

        // List to store words
        List<String> words = new ArrayList<>();

        // Read words from file and store them in the list
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Split line into words, using non-word characters as delimiters
                String[] splitWords = line.split("\\W+");

                // Ensure words start with a letter
                for (String word : splitWords) {
                    if (word.matches("^[a-zA-Z].*")) {
                        words.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Sort words treating uppercase and lowercase equally
        words.sort((word1, word2) -> word1.toLowerCase().compareTo(word2.toLowerCase()));

        // Display the sorted list
        for (String word : words) {
            System.out.println(word);
        }

        scanner.close(); // Close the scanner
    }

    // Method to check if the file exists
    private static boolean checkFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.canRead(); // Check if the file exists and is readable
    }
}