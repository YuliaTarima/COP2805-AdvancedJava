//Write a program that reads words from a text file and displays all the words
// (duplicates allowed) in ascending alphabetical order.
// The words must start with a letter.
//
// The text file is passed as a command line argument.

// java com/assignments/week08/YuliaTarima_20_01 com/assignments/week08/assignment20.txt
package com.assignments.week08;

import java.io.*;
import java.util.*;

public class YuliaTarima_20_01 {

    public static void main(String[] args) {
        // Check if a filename is passed as a command-line argument
        if (args.length != 1) {
            System.out.println("Usage: java YuliaTarima_20_01 assignment20.txt");
            return;
        }

        // File name from command-line argument
        String fileName = args[0];

        // List to store words
        List<String> words = new ArrayList<>();

        // Read words from file and store them in the list
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Split line into words, using non-word characters as delimiters
                String[] splitWords = line.split("\\W+");

                // Add words that start with a letter to the list
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

        // Sort the list of words in ascending alphabetical order
        Collections.sort(words);

        // Display the sorted list
        for (String word : words) {
            System.out.println(word);
        }
    }
}