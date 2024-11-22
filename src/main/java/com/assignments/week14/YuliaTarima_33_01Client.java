package com.assignments.week14;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class YuliaTarima_33_01Client extends Application {
  // Define the output stream for sending data to the server
  DataOutputStream toServer = null;

  // Define the input stream for receiving data from the server
  DataInputStream fromServer = null;

  @Override
  public void start(Stage primaryStage) {
    // Create a GridPane for entering loan parameters
    GridPane gridPane = new GridPane();
    // Set padding and spacing for the GridPane
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);

    // Create a label and text field for the annual interest rate
    Label interestRateLabel = new Label("Annual Interest Rate: ");
    TextField interestRateField = new TextField();
    // Add the label and text field to the GridPane
    gridPane.add(interestRateLabel, 0, 0);
    gridPane.add(interestRateField, 1, 0);

    // Create a label and text field for the number of years
    Label yearsLabel = new Label("Number of Years: ");
    TextField yearsField = new TextField();
    // Add the label and text field to the GridPane
    gridPane.add(yearsLabel, 0, 1);
    gridPane.add(yearsField, 1, 1);

    // Create a label and text field for the loan amount
    Label loanAmountLabel = new Label("Loan Amount: ");
    TextField loanAmountField = new TextField();
    // Add the label and text field to the GridPane
    gridPane.add(loanAmountLabel, 0, 2);
    gridPane.add(loanAmountField, 1, 2);

    // Create a Submit button
    Button submitButton = new Button("Submit");
    // Add the button to the GridPane (row 3, column 1)
    gridPane.add(submitButton, 1, 3);

    // Create a TextArea to display messages and results
    TextArea ta = new TextArea();
    // Make the TextArea non-editable
    ta.setEditable(false);
    // Add the TextArea to a ScrollPane for scrolling capability
    ScrollPane scrollPane = new ScrollPane(ta);

    // Create a BorderPane to organize the layout
    BorderPane mainPane = new BorderPane();
    // Place the GridPane at the top of the BorderPane
    mainPane.setTop(gridPane);
    // Place the ScrollPane (containing the TextArea) at the center of the BorderPane
    mainPane.setCenter(scrollPane);

    // Create a Scene with the BorderPane and set its size
    Scene scene = new Scene(mainPane, 450, 350);
    // Set the title of the stage (client window)
    primaryStage.setTitle("Loan Client");
    // Set the Scene for the primary stage
    primaryStage.setScene(scene);
    // Display the primary stage
    primaryStage.show();

    // Add an action event to the Submit button
    submitButton.setOnAction(e -> sendLoanParameters(interestRateField, yearsField, loanAmountField, ta));

    try {
      // Create a socket to connect to the server at localhost on port 8000
      Socket socket = new Socket("localhost", 8000);

      // Display a message in the TextArea indicating the client is connected to the server
      Platform.runLater(() -> {
        ta.appendText("Connected to the server at " + new Date() + '\n');
      });

      // Initialize the input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Initialize the output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    } catch (IOException ex) {
      // Display an error message in the TextArea if a connection error occurs
      ta.appendText(ex.toString() + '\n');
    }
  }

  // Method to send loan parameters to the server and receive results
  private void sendLoanParameters(TextField interestRateField, TextField yearsField, TextField loanAmountField, TextArea ta) {
    try {
      // Check if all input fields are filled; if not, throw an exception
      if (interestRateField.getText().isEmpty() || yearsField.getText().isEmpty() || loanAmountField.getText().isEmpty()) {
        throw new IllegalArgumentException("All fields must be filled in.");
      }

      // Parse the annual interest rate from the text field
      double annualInterestRate = Double.parseDouble(interestRateField.getText().trim());

      // Parse the number of years from the text field
      int numberOfYears = Integer.parseInt(yearsField.getText().trim());

      // Parse the loan amount from the text field
      double loanAmount = Double.parseDouble(loanAmountField.getText().trim());

      // Validate the annual interest rate; it must be between 0 and 100
      if (annualInterestRate <= 0 || annualInterestRate > 100) {
        Platform.runLater(() -> ta.appendText("Error: Interest rate must be between 0 and 100.\n"));
        return;
      }

      // Validate the number of years; it must be positive and reasonable (e.g., up to 100)
      if (numberOfYears <= 0 || numberOfYears > 100) {
        Platform.runLater(() -> ta.appendText("Error: Number of years must be a positive value and reasonable (e.g., up to 100).\n"));
        return;
      }

      // Validate the loan amount; it must be greater than 0
      if (loanAmount <= 0) {
        Platform.runLater(() -> ta.appendText("Error: Loan amount must be greater than 0.\n"));
        return;
      }

      // Send the annual interest rate to the server
      toServer.writeDouble(annualInterestRate);

      // Send the number of years to the server
      toServer.writeInt(numberOfYears);

      // Send the loan amount to the server
      toServer.writeDouble(loanAmount);

      // Flush the output stream to ensure the data is sent
      toServer.flush();

      // Receive the monthly payment from the server
      double monthlyPayment = fromServer.readDouble();

      // Receive the total payment from the server
      double totalPayment = fromServer.readDouble();

      // Format the monthly and total payments to 2 decimal places
      String formattedMonthlyPayment = String.format("%.2f", monthlyPayment);
      String formattedTotalPayment = String.format("%.2f", totalPayment);

      // Display the loan parameters and results in the TextArea
      Platform.runLater(() -> {
        ta.appendText("\nLoan Parameters Sent to the Server:\n");
        ta.appendText("Interest Rate: " + annualInterestRate + "\n");
        ta.appendText("Years: " + numberOfYears + "\n");
        ta.appendText("Loan Amount: " + loanAmount + "\n");
        ta.appendText("\nLoan Calculation Results:\n");
        ta.appendText("Monthly Payment: " + formattedMonthlyPayment + "\n");
        ta.appendText("Total Payment: " + formattedTotalPayment + "\n");
      });
    } catch (NumberFormatException ex) {
      // Display an error message if the input cannot be parsed as a number
      Platform.runLater(() -> ta.appendText("Error: Please enter valid numbers for all parameters.\n"));
    } catch (IOException ex) {
      // Display an error message if a communication error occurs
      Platform.runLater(() -> ta.appendText("Error: " + ex.getMessage() + "\n"));
    }
  }

  // Main method to launch the JavaFX application
  public static void main(String[] args) {
    launch(args);
  }
}