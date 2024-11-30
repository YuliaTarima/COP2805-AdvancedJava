package com.assignments.week15;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.sql.*;

public class YuliaTarima_34_4 extends Application {
    // TextField for entering SSN
    private final TextField tfSSN = new TextField(); // Only the SSN field is kept
    // Label for displaying the status or query results
    private final Label lblStatus = new Label();
    // Statement for executing queries
    private Statement stmt;

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Initialize database connection and create a Statement object
        initializeDB();

        // Button to trigger the query for grades
        Button btShowGrade = new Button("Show Grade");
        // HBox for arranging the label, text field, and button in a horizontal layout
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(new Label("SSN"), tfSSN, btShowGrade);

        // VBox to stack HBox and the status label vertically
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hBox, lblStatus);

        // Add padding to the VBox (top, right, bottom, left)
        vBox.setPadding(new Insets(10, 10, 10, 20));

        // Set preferred column count for the SSN text field
        tfSSN.setPrefColumnCount(6);
        // Set action to execute the query when the button is clicked
        btShowGrade.setOnAction(e -> showGrade());

        // Create the scene with VBox as the root and set its dimensions
        Scene scene = new Scene(vBox, 420, 250);
        // Set the stage title
        primaryStage.setTitle("FindGrade");
        // Place the scene into the stage
        primaryStage.setScene(scene);
        // Display the stage
        primaryStage.show();
    }

    private void initializeDB() {
        try {
            // Define the database URL
            String urlDB = "jdbc:sqlite:src/main/java/com/assignments/week15/test.db";
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(urlDB);
            System.out.println("Database connected.");
            // Create a Statement object for executing SQL queries
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            // Print any SQL exceptions
            ex.printStackTrace();
        }
    }

    private void showGrade() {
        // Get the SSN entered by the user
        String ssn = tfSSN.getText();

        // Validate numeric input for SSN
        if (!ssn.matches("\\d+")) {
            // SSN is not numeric, show a message to the user
            lblStatus.setText("Error: SSN is invalid. It must be numeric.");
            // Return to prevent further processing
            return;
        }

        try {
            // SQL query to fetch the list of courses and corresponding grades for a specific student
            String queryString =
                    // Select student details from the Student table
                    "SELECT Student.firstName, Student.mi, Student.lastName, " +
                            // Select the course ID from the Enrollment table
                            "Enrollment.courseId, " +
                            // Select the course title from the Course table
                            "Course.title, " +
                            // Select the grade from the Enrollment table
                            "Enrollment.grade " +
                            // Specify the base table (Student table) for the query
                            "FROM Student " +
                            // Join the Enrollment table to match students with their enrolled courses
                            // Match the student's SSN in the Student table with the SSN in the Enrollment table
                            "JOIN Enrollment ON Student.ssn = Enrollment.ssn " +
                            // Join the Course table to match course details to the enrolled courses
                            // Match the course ID in the Enrollment table with the course ID in the Course table
                            "JOIN Course ON Enrollment.courseId = Course.courseId " +
                            // Add a filter to retrieve information only for a specific student
                            // The placeholder '?' will be replaced by the student's SSN at runtime
                            "WHERE Student.ssn = ?";

            // Prepare the SQL statement to prevent SQL injection
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, ssn);

            // Execute the query and retrieve the result set
            ResultSet rset = preparedStatement.executeQuery();

            // Use StringBuilder to build the output string for student's courses and grades
            StringBuilder studentGrades = new StringBuilder();
            // Tracks if common student exists in the database on joined tables
            boolean studentFound = false;
            // Counter for graded courses for the requested student
            int courseCounter = 0;
            // Store the student's full name if found
            String studentFullName = "";

            while (rset.next()) {
                // Get course details and construct the student's full name
                String studentFirstName = rset.getString("firstName");
                String studentMi = rset.getString("mi").trim();
                String studentLastName = rset.getString("lastName");
                studentFullName = (studentFirstName + " " + studentMi + " " + studentLastName).trim();

                // Mark the student as found
                if (studentFullName.isEmpty()) return; else studentFound = true;
                // Get course title and grade
                String title = rset.getString("title");
                String grade = rset.getString("grade");

                // Append details to the output string only if the course exists
                if (title != null && grade != null) {
                    studentGrades.append(studentFullName)
                            .append("'s grade on course ")
                            .append(title).append(" is ")
                            .append(grade).append(".\n");
                    // Increment counter for each graded course
                    courseCounter++;
                }
            }

            // Display the results or indicate no data found
            if (studentGrades.length() > 0) {
                // Student exists and has graded courses
                // Append number of graded courses for the requested student
                if (courseCounter > 0) {
                    studentGrades.append("\n\n")
                            .append(studentFullName)
                            .append(" is enrolled in ")
                            .append(courseCounter)
                            .append(" graded courses.");
                }
                // Display student graded courses summary
                lblStatus.setText(studentGrades.toString());
            } else if (!studentFound) {
                // No student found with the given SSN
                lblStatus.setText("No courses found for student with the following SSN: " + ssn + ".");
            } else {
                // Student exists but has no courses
                lblStatus.setText("No courses found for student " + studentFullName + ".");
            }
        } catch (SQLException ex) {
            // Print any SQL exceptions
            ex.printStackTrace();
        }
    }
}