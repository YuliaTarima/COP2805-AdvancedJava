// -----------------------------------------------//
// Rewrite Programming Exercise 15.28 ------------//
// using a thread instead of Timeline ------------//
// to control the fan animation. -----------------//
// -----------------------------------------------//

package com.assignments.week13;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class YuliaTarima_32_05 extends Application {
    private Thread fanThread; // Thread to control the fan animation
    private boolean running = true; // Used to control the thread state

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a FanPane object
        ControlAnimationFanPane fan = new ControlAnimationFanPane();

        // Create buttons to control the fan
        HBox hBox = new HBox(5);
        Button btPause = new Button("Pause");
        Button btResume = new Button("Resume");
        Button btReverse = new Button("Reverse");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btPause, btResume, btReverse);

        // Set up the layout
        BorderPane pane = new BorderPane();
        pane.setCenter(fan);
        pane.setBottom(hBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 400, 450); // Doubling the scene size
        primaryStage.setTitle("Fan Animation Using Thread"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Create and start a thread to handle the animation
        fanThread = new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(100); // Control the fan speed
                    fan.move(); // Move the fan
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        fanThread.setDaemon(true); // Ensure the thread terminates when the app exits
        fanThread.start(); // Start the fan thread

        // Button controls
        btPause.setOnAction(e -> running = false); // Pause the fan
        btResume.setOnAction(e -> {
            if (!running) {
                running = true;
                fanThread = new Thread(() -> {
                    try {
                        while (running) {
                            Thread.sleep(100);
                            fan.move();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                fanThread.setDaemon(true);
                fanThread.start();
            }
        }); // Resume the fan

        btReverse.setOnAction(e -> fan.reverse()); // Reverse the fan direction

        // Adjust fan size when the window is resized
        scene.widthProperty().addListener(e -> fan.setW(fan.getWidth()));
        scene.heightProperty().addListener(e -> fan.setH(fan.getHeight()));
    }
}

class ControlAnimationFanPane extends Pane {
    private double w = 400; // Double the initial width
    private double h = 400; // Double the initial height
    private double radius = Math.min(w, h) * 0.45;
    private Circle circle = new Circle(w / 2, h / 2, radius);
    private Arc arc[] = new Arc[4];
    private double startAngle = 30;
    private double increment = 5; // Fan rotation increment

    public ControlAnimationFanPane() {
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        getChildren().add(circle);

        for (int i = 0; i < 4; i++) {
            arc[i] = new Arc(w / 2, h / 2, radius * 0.9, radius * 0.9, startAngle + i * 90, 35);
            arc[i].setFill(Color.RED); // Set fill color
            arc[i].setType(ArcType.ROUND);
            getChildren().addAll(arc[i]);
        }
    }

    // Reverse the fan rotation direction
    public void reverse() {
        increment = -increment;
    }

    // Move the fan by updating the start angle
    public void move() {
        setStartAngle(startAngle + increment);
    }

    // Set the start angle and update the values
    public void setStartAngle(double angle) {
        startAngle = angle;
        setValues();
    }

    // Update the size and position of the fan blades
    public void setValues() {
        radius = Math.min(w, h) * 0.45;
        circle.setRadius(radius);
        circle.setCenterX(w / 2);
        circle.setCenterY(h / 2);

        for (int i = 0; i < 4; i++) {
            arc[i].setRadiusX(radius * 0.9);
            arc[i].setRadiusY(radius * 0.9);
            arc[i].setCenterX(w / 2);
            arc[i].setCenterY(h / 2);
            arc[i].setStartAngle(startAngle + i * 90);
        }
    }

    // Adjust the width of the fan pane
    public void setW(double w) {
        this.w = w;
        setValues();
    }

    // Adjust the height of the fan pane
    public void setH(double h) {
        this.h = h;
        setValues();
    }
}