package com.assignments.week14;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class YuliaTarima_33_01Server extends Application {

  @Override
  public void start(Stage primaryStage) {
    // Create a TextArea for displaying server messages and information
    TextArea ta = new TextArea();

    // Create a Scene containing the TextArea within a ScrollPane
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);

    // Set the title of the primary stage (server window)
    primaryStage.setTitle("Server");

    // Place the scene in the primary stage
    primaryStage.setScene(scene);

    // Display the primary stage
    primaryStage.show();

    // Start a new thread for handling client connections
    new Thread(() -> {
      try {
        // Create a ServerSocket to listen on port 8000
        ServerSocket serverSocket = new ServerSocket(8000);

        // Append a message to the TextArea indicating the server has started
        Platform.runLater(() ->
                ta.appendText("Server started at " + new Date() + '\n'));

        // Wait for a connection request from a client
        Socket socket = serverSocket.accept();

        // Append a message to the TextArea indicating a client has connected
        Platform.runLater(() ->
                ta.appendText("Client connected at " + new Date() + '\n'));

        // Create DataInputStream to receive data from the client
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());

        // Create DataOutputStream to send data back to the client
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

        // Continuously listen for loan parameter input from the client
        while (true) {
          // Read the annual interest rate from the client
          double annualInterestRate = inputFromClient.readDouble();

          // Read the number of years from the client
          int numberOfYears = inputFromClient.readInt();

          // Read the loan amount from the client
          double loanAmount = inputFromClient.readDouble();

          // Create a Loan object with the received parameters
          Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);

          // Calculate the monthly payment using the Loan object
          double monthlyPayment = loan.getMonthlyPayment();

          // Calculate the total payment using the Loan object
          double totalPayment = loan.getTotalPayment();

          // Send the calculated monthly payment to the client
          outputToClient.writeDouble(monthlyPayment);

          // Send the calculated total payment to the client
          outputToClient.writeDouble(totalPayment);

          // Append the received loan parameters and calculated results to the TextArea
          Platform.runLater(() -> {
            ta.appendText("\nLoan parameters received from client:\n");
            ta.appendText("Annual Interest Rate: " + annualInterestRate + "\n");
            ta.appendText("Number of Years: " + numberOfYears + "\n");
            ta.appendText("Loan Amount: " + loanAmount + "\n");
            ta.appendText("\nLoan Calculation Results:\n");
            ta.appendText("Monthly Payment: " + monthlyPayment + "\n");
            ta.appendText("Total Payment: " + totalPayment + "\n");
          });
        }
      } catch (IOException ex) {
        // Print the exception to the console if an error occurs
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method launches the JavaFX application.
   * It is primarily needed for IDEs with limited JavaFX support.
   */
  public static void main(String[] args) {
    launch(args);
  }

  // Loan class for calculating loan payments
  public static class Loan {
    // Annual interest rate for the loan
    private double annualInterestRate;

    // Number of years for the loan
    private int numberOfYears;

    // Loan amount
    private double loanAmount;

    /**
     * Constructor for the Loan class.
     * Initializes the loan with the specified annual interest rate, number of years, and loan amount.
     *
     * @param annualInterestRate Annual interest rate of the loan in percentage
     * @param numberOfYears Number of years for the loan
     * @param loanAmount Principal loan amount
     */
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
      this.annualInterestRate = annualInterestRate;
      this.numberOfYears = numberOfYears;
      this.loanAmount = loanAmount;
    }

    /**
     * Calculates and returns the monthly payment for the loan.
     *
     * @return Monthly payment as a double value
     */
    public double getMonthlyPayment() {
      // Convert annual interest rate to a monthly interest rate
      double monthlyInterestRate = annualInterestRate / 1200;

      // Calculate and return the monthly payment using the loan formula
      return loanAmount * monthlyInterestRate /
              (1 - Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12));
    }

    /**
     * Calculates and returns the total payment for the loan.
     *
     * @return Total payment as a double value
     */
    public double getTotalPayment() {
      // Calculate total payment by multiplying monthly payment by the total number of months
      return getMonthlyPayment() * numberOfYears * 12;
    }
  }
}