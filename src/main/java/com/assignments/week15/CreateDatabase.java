package com.assignments.week15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class CreateDatabase {

    // Method to create a new database
    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println("Database creation failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String dbFileName = "src/main/java/com/assignments/week15/test.db";

        // Create a new database
        createNewDatabase(dbFileName);

        // Delete the database file after test
        Path path = Paths.get(dbFileName);
        try {
            Files.delete(path);
            System.out.println("The database file has been deleted.");
        } catch (IOException e) {
            System.out.println("Failed to delete the database file: " + e.getMessage());
        }
    }
}