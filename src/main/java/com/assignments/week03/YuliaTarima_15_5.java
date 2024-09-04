package com.assignments.week03;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Date;

public class YuliaTarima_15_5 extends Application {

    // Constants for scene dimensions
    private static final double SCENE_WIDTH = 400;
    private static final double SCENE_HEIGHT = 250;

    // UI components
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfNumberOfYears = new TextField();
    private TextField tfLoanAmount = new TextField();
    private TextField tfMonthlyPayment = new TextField();
    private TextField tfTotalPayment = new TextField();
    private Button btCalculate = new Button("Calculate");

    @Override
    public void start(Stage primaryStage) {
        // Create the grid pane layout
        GridPane gridPane = createGridPane();

        // Set properties for the UI components
        setupUIProperties(gridPane);

        // Setup event handlers for button actions
        setupEventHandlers();

        // Create the scene with the grid pane and set it on the stage
        Scene scene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("Loan Calculator"); // Set the title of the stage
        primaryStage.setScene(scene); // Place the scene on the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * Creates and configures the GridPane layout with UI components.
     * @return Configured GridPane
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5); // Set horizontal gap between columns
        gridPane.setVgap(5); // Set vertical gap between rows

        // Add labels and text fields to the grid pane
        gridPane.add(new Label("Annual Interest Rate:"), 0, 0);
        gridPane.add(tfAnnualInterestRate, 1, 0);
        gridPane.add(new Label("Number of Years:"), 0, 1);
        gridPane.add(tfNumberOfYears, 1, 1);
        gridPane.add(new Label("Loan Amount:"), 0, 2);
        gridPane.add(tfLoanAmount, 1, 2);
        gridPane.add(new Label("Monthly Payment:"), 0, 3);
        gridPane.add(tfMonthlyPayment, 1, 3);
        gridPane.add(new Label("Total Payment:"), 0, 4);
        gridPane.add(tfTotalPayment, 1, 4);
        gridPane.add(btCalculate, 1, 5); // Add calculate button to grid pane

        return gridPane;
    }

    /**
     * Sets properties for UI components, such as alignment and editability.
     * @param gridPane The GridPane that holds the UI components
     */
    private void setupUIProperties(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER); // Center-align the grid pane
        tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT); // Align text to the bottom right
        tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
        tfLoanAmount.setAlignment(Pos.BOTTOM_RIGHT);
        tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
        tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);

        tfMonthlyPayment.setEditable(false); // Make Monthly Payment field read-only
        tfTotalPayment.setEditable(false); // Make Total Payment field read-only

        GridPane.setHalignment(btCalculate, HPos.RIGHT); // Align button to the right
    }

    /**
     * Sets up event handlers for user interactions.
     */
    private void setupEventHandlers() {
        // Handle button click to calculate loan payments
        btCalculate.setOnAction(e -> calculateLoanPayment());
    }

    /**
     * Calculates the loan payments based on user input and updates the text fields.
     */
    private void calculateLoanPayment() {
        try {
            // Retrieve and parse input values
            double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
            int numberOfYears = Integer.parseInt(tfNumberOfYears.getText());
            double loanAmount = Double.parseDouble(tfLoanAmount.getText());

            // Create a loan object with the input values
            Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);

            // Display calculated values in the text fields
            tfMonthlyPayment.setText(String.format("$%.2f", loan.getMonthlyPayment()));
            tfTotalPayment.setText(String.format("$%.2f", loan.getTotalPayment()));
        } catch (NumberFormatException e) {
            // Handle invalid input by displaying an error message
            tfMonthlyPayment.setText("Invalid input");
            tfTotalPayment.setText("Invalid input");
        }
    }

    /**
     * Main method for launching the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    /**
     * Inner class for representing a loan.
     */
    public static class Loan {
        private double annualInterestRate;
        private int numberOfYears;
        private double loanAmount;
        private final Date loanDate;

        /** Default constructor */
        public Loan() {
            this(2.5, 1, 1000);
        }

        /** Construct a loan with specified annual interest rate,
         number of years and loan amount */
        public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
            this.annualInterestRate = annualInterestRate;
            this.numberOfYears = numberOfYears;
            this.loanAmount = loanAmount;
            this.loanDate = new Date();
        }

        /** Return annualInterestRate */
        public double getAnnualInterestRate() {
            return annualInterestRate;
        }

        /** Set a new annualInterestRate */
        public void setAnnualInterestRate(double annualInterestRate) {
            this.annualInterestRate = annualInterestRate;
        }

        /** Return numberOfYears */
        public int getNumberOfYears() {
            return numberOfYears;
        }

        /** Set a new numberOfYears */
        public void setNumberOfYears(int numberOfYears) {
            this.numberOfYears = numberOfYears;
        }

        /** Return loanAmount */
        public double getLoanAmount() {
            return loanAmount;
        }

        /** Set a new loanAmount */
        public void setLoanAmount(double loanAmount) {
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

        /** Return loan date */
        public Date getLoanDate() {
            return loanDate;
        }
    }
}