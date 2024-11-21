package com.assignments.week14;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class YuliaTarima_33_01Client extends Application {
  // IO streams
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;

  @Override
  public void start(Stage primaryStage) {
    // Panel for loan parameters
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);

    // Labels and text fields for loan parameters
    Label interestRateLabel = new Label("Annual Interest Rate: ");
    TextField interestRateField = new TextField();
    gridPane.add(interestRateLabel, 0, 0);
    gridPane.add(interestRateField, 1, 0);

    Label yearsLabel = new Label("Number of Years: ");
    TextField yearsField = new TextField();
    gridPane.add(yearsLabel, 0, 1);
    gridPane.add(yearsField, 1, 1);

    Label loanAmountLabel = new Label("Loan Amount: ");
    TextField loanAmountField = new TextField();
    gridPane.add(loanAmountLabel, 0, 2);
    gridPane.add(loanAmountField, 1, 2);

    // Text area to display contents
    TextArea ta = new TextArea();
    ta.setEditable(false);
    ScrollPane scrollPane = new ScrollPane(ta);

    // Main pane
    BorderPane mainPane = new BorderPane();
    mainPane.setTop(gridPane);
    mainPane.setCenter(scrollPane);

    // Create a scene and place it in the stage
    Scene scene = new Scene(mainPane, 450, 350);
    primaryStage.setTitle("Loan Client");
    primaryStage.setScene(scene);
    primaryStage.show();

    // Handle action when Enter is pressed in any text field
    interestRateField.setOnAction(e -> sendLoanParameters(interestRateField, yearsField, loanAmountField, ta));
    yearsField.setOnAction(e -> sendLoanParameters(interestRateField, yearsField, loanAmountField, ta));
    loanAmountField.setOnAction(e -> sendLoanParameters(interestRateField, yearsField, loanAmountField, ta));

    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 8000);

      // Show the connection message on the TextArea once connected
      Platform.runLater(() -> {
        ta.appendText("Connected to the server at " + new Date() + '\n');
      });

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    } catch (IOException ex) {
      ta.appendText(ex.toString() + '\n');
    }
  }

  private void sendLoanParameters(TextField interestRateField, TextField yearsField, TextField loanAmountField, TextArea ta) {
    try {
      // Check if the input fields are not empty
      if (interestRateField.getText().isEmpty() || yearsField.getText().isEmpty() || loanAmountField.getText().isEmpty()) {
        throw new IllegalArgumentException("All fields must be filled in.");
      }
      // Parse the loan parameters from the text fields
      double annualInterestRate = Double.parseDouble(interestRateField.getText().trim());
      int numberOfYears = Integer.parseInt(yearsField.getText().trim());
      double loanAmount = Double.parseDouble(loanAmountField.getText().trim());

      // Validate the loan parameters
      if (annualInterestRate <= 0 || annualInterestRate > 100) {
        Platform.runLater(() -> ta.appendText("Error: Interest rate must be between 0 and 100.\n"));
        return;
      }
      if (numberOfYears <= 0 || numberOfYears > 100) {
        Platform.runLater(() -> ta.appendText("Error: Number of numberOfYears must be a positive value and reasonable (e.g., up to 100).\n"));
        return;
      }
      if (loanAmount <= 0) {
        Platform.runLater(() -> ta.appendText("Error: Loan amount must be greater than 0.\n"));
        return;
      }

      // Send the loan parameters to the server
      toServer.writeDouble(annualInterestRate);
      toServer.writeInt(numberOfYears);
      toServer.writeDouble(loanAmount);
      toServer.flush();

      // Receive the monthly payment and total payment from the server
      double monthlyPayment = fromServer.readDouble();
      double totalPayment = fromServer.readDouble();

      // Format the values to 2 decimal places
      String formattedMonthlyPayment = String.format("%.2f", monthlyPayment);
      String formattedTotalPayment = String.format("%.2f", totalPayment);

      // Display the results to the text area
      Platform.runLater(() -> {
        ta.appendText("Loan Parameters Sent to the Server:\n");
        ta.appendText("Interest Rate: " + annualInterestRate + "\n");
        ta.appendText("Years: " + numberOfYears + "\n");
        ta.appendText("Loan Amount: " + loanAmount + "\n");
        ta.appendText("\nLoan Calculation Results:\n");
        ta.appendText("Monthly Payment: " + formattedMonthlyPayment + "\n");
        ta.appendText("Total Payment: " + formattedTotalPayment + "\n");
      });
    } catch (NumberFormatException ex) {
      Platform.runLater(() -> ta.appendText("Error: Please enter valid numbers for all parameters.\n"));
    } catch (IOException ex) {
      Platform.runLater(() -> ta.appendText("Error: " + ex.getMessage() + "\n"));
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}