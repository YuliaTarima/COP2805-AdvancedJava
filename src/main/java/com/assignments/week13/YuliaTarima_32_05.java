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
import javafx.scene.effect.DropShadow;

import static javafx.application.Application.launch;

public class YuliaTarima_32_05 extends Application {
    // Thread to control the fan animation
    private Thread fanThread;
    // Used to control the thread state
    private boolean running = true;

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    // Override the start method in the Application class
    @Override
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
        // Scene size
        Scene scene = new Scene(pane, 400, 450);
        // Stage title
        primaryStage.setTitle("Fan Animation Using Thread");
        // Place the scene in the stage
        primaryStage.setScene(scene);
        // Display the stage
        primaryStage.show();

        // Create and start a thread to handle the animation
        fanThread = new Thread(() -> {
            try {
                while (running) {
                    // Control the fan speed
                    Thread.sleep(80);
                    // Move the fan
                    fan.move();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        // Ensure the thread terminates when the app exits
        fanThread.setDaemon(true);
        // Start the fan thread
        fanThread.start();

        // Button controls
        // Pause the fan
        btPause.setOnAction(e -> running = false);
        // Resume the fan
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
        });
        // Reverse the fan direction
        btReverse.setOnAction(e -> fan.reverse());

        // Adjust fan size when the window is resized
        scene.widthProperty().addListener(e -> fan.setW(fan.getWidth()));
        scene.heightProperty().addListener(e -> fan.setH(fan.getHeight()));
    }
}

class ControlAnimationFanPane extends Pane {
    // Initial width of the pane
    private double w = 400;
    // Initial height of the pane
    private double h = 400;
    // Set fan radius, ensure the fan fits within the pane.
    private double radius = Math.min(w, h) * 0.45;
    private Circle circle = new Circle(w / 2, h / 2, radius);
    // Creates an array that can hold four fan blades
    private Arc arc[] = new Arc[4];
    // Initial angle at which the first fan blade is positioned
    // 360/4 blades = 90, so rest of the blades will be positioned
    // at 120, 210, and 300 degrees for a four-blade fan
    private double startAngle = 30;
    // Fan rotation speed increment
    // how much the startAngle changes (increases by n degrees)
    // with each update of the fan's position
    private double increment = 3;

    public ControlAnimationFanPane() {
        circle.setStroke(Color.LIGHTGRAY);
        circle.setFill(Color.WHITE);
        getChildren().add(circle);

        // Create a shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGREY);
        shadow.setRadius(5);
        // Set horizontal offset
        shadow.setOffsetX(3);
        // Set vertical offset
        shadow.setOffsetY(3);

        for (int i = 0; i < 4; i++) {
            // reduce fan radius (radius * n) to create the fan blades
            // arc starting angle (i * 90) degrees spreads blades evenly in four directions
            // each arc spans 35 degrees.
            arc[i] = new Arc(w / 2, h / 2, radius * 0.9, radius * 0.9, startAngle + i * 90, 35);
            // Set fill color
            arc[i].setFill(Color.TURQUOISE);
            arc[i].setType(ArcType.ROUND);
            // Apply the shadow effect
            arc[i].setEffect(shadow);
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
            // Set radius of the Fan Blades
            arc[i].setRadiusX(radius * 0.95);
            arc[i].setRadiusY(radius * 0.95);
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