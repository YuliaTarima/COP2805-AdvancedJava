package com.assignments.week02;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class YuliaTarima_14_05 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a pane to hold the circle and text
        Pane pane = new Pane();

        // Create a circle with center at (150, 150) and radius 100
        Circle circle = new Circle(150, 150, 100);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);

        // Add the circle to the pane
        pane.getChildren().add(circle);

        // String to display around the circle
        String message = "Welcome to Java ";
        double angleStep = 360.0 / message.length();
        double radius = 100;

        // Adjust radius so the text is placed outside the circle
        double textRadius = radius + 15;

        // Display each character at the correct position with the correct rotation
        for (int i = 0; i < message.length(); i++) {
            // Start angle adjusted to position text from top
            double angle = i * angleStep - 155;

            // Calculate position of each character
            double x = 150 + textRadius * Math.cos(Math.toRadians(angle));
            double y = 150 + textRadius * Math.sin(Math.toRadians(angle));

            // Create the text character
            Text text = new Text(x, y, String.valueOf(message.charAt(i)));
            text.setFont(Font.font(20));

            // Rotate each character around its position
            // Adjust rotation so text is upright
            text.setRotate(angle + 90);

            // Adjust position to center the character
            text.setX(text.getX() - text.getLayoutBounds().getWidth() / 2);
            text.setY(text.getY() + text.getLayoutBounds().getHeight() / 4);

            pane.getChildren().add(text);
        }

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 300, 300);
        // Set the stage title
        primaryStage.setTitle("Welcome to Java");
        // Place the scene in the stage
        primaryStage.setScene(scene);
        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}