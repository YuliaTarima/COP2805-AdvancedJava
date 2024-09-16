// -- * Store Objects and Arrays in a File *-- //
//    Write a program that stores an array
//    of the five int values 1, 2, 3, 4 and 5,
//    a Date object for the current time,
//    and the double value 5.5
//    into a file named Exercise17_05.dat.
//
//    In the same program,
//    write the code to read and display the data.

package com.assignments.week05;

import java.io.*;
import java.util.Date;

public class YuliaTarima_17_5 {

    public static void main(String[] args) {
        // Define the data
        int[] intArray = {1, 2, 3, 4, 5};
        Date currentDate = new Date();
        double doubleValue = 5.5;

        // File to store the data
        String fileName = "Exercise17_05.dat";

        // Create an instance of FileHandler
        FileHandler fileHandler = new FileHandler(fileName);

        // Write data to the file
        fileHandler.writeData(intArray, currentDate, doubleValue);

        // Read and display data from the file
        Object[] data = fileHandler.readData();
        if (data != null) {
            displayData((int[]) data[0], (Date) data[1], (double) data[2]);
        }
    }

    // Method to display the data
    private static void displayData(int[] intArray, Date currentDate, double doubleValue) {
        // Display the array of integers
        System.out.print("Array of integers: ");
        for (int value : intArray) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Display the Date object
        System.out.println("Date: " + currentDate);

        // Display the double value
        System.out.println("Double value: " + doubleValue);
    }
}

// Class for handling file operations (reading and writing)
class FileHandler {

    private final String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    // Method to write data to the file with internal exception handling
    public void writeData(int[] intArray, Date currentDate, double doubleValue) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(intArray);
            output.writeObject(currentDate);
            output.writeDouble(doubleValue);
            System.out.println("* Data written successfully.\n");
        } catch (IOException e) {
            System.err.println("Error writing data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to read data from the file with internal exception handling
    public Object[] readData() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            int[] intArray = (int[]) input.readObject();
            Date currentDate = (Date) input.readObject();
            double doubleValue = input.readDouble();
            System.out.println("* Data read successfully. \n");
            return new Object[]{intArray, currentDate, doubleValue};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading data: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}