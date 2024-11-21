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
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    new Thread(() -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
                ta.appendText("Server started at " + new Date() + '\n'));

        // Listen for a connection request
        Socket socket = serverSocket.accept();

        // Show when client connects
        Platform.runLater(() ->
                ta.appendText("Client connected at " + new Date() + '\n'));

        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
                socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
                socket.getOutputStream());

        while (true) {
          // Receive loan parameters from the client
          double annualInterestRate = inputFromClient.readDouble();
          int numberOfYears = inputFromClient.readInt();
          double loanAmount = inputFromClient.readDouble();

          // Calculate loan payments
          Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
          double monthlyPayment = loan.getMonthlyPayment();
          double totalPayment = loan.getTotalPayment();

          // Send results back to the client
          outputToClient.writeDouble(monthlyPayment);
          outputToClient.writeDouble(totalPayment);

          Platform.runLater(() -> {
            ta.appendText("Loan parameters received from client:\n");
            ta.appendText("Annual Interest Rate: " + annualInterestRate + "\n");
            ta.appendText("Number of Years: " + numberOfYears + "\n");
            ta.appendText("Loan Amount: " + loanAmount + "\n");
            ta.appendText("\nLoan Calculation Results:\n");
            ta.appendText("Monthly Payment: " + monthlyPayment + "\n");
            ta.appendText("Total Payment: " + totalPayment + "\n");
          });
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }

  // Loan class for calculating payments
  public static class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;

    /** Construct a loan with specified annual interest rate,
     number of years and loan amount */
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
      this.annualInterestRate = annualInterestRate;
      this.numberOfYears = numberOfYears;
      this.loanAmount = loanAmount;
    }

    /** Find monthly payment */
    public double getMonthlyPayment() {
      double monthlyInterestRate = annualInterestRate / 1200;
      return loanAmount * monthlyInterestRate / (1 -
              (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12)));
    }

    /** Find total payment */
    public double getTotalPayment() {
      return getMonthlyPayment() * numberOfYears * 12;
    }
  }
}