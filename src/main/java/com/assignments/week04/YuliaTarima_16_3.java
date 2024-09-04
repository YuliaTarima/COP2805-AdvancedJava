package com.assignments.week04;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;

/**
 * Main class for the Traffic Light Simulator application.
 */
public class YuliaTarima_16_3 extends Application {
    private static final int WIDTH = 350;
    private static final int HEIGHT = 450;

    @Override
    public void start(Stage primaryStage) {
        TrafficLightBuilder builder = new TrafficLightBuilder();

        // Create the traffic light pane
        VBox circlesContainer = builder.buildCirclesContainer();
        Rectangle borderRectangle = builder.buildBorderRectangle();
        StackPane trafficLightPane = new StackPane(borderRectangle, circlesContainer);

        // Instantiate the TrafficLightController
        TrafficLightController controller = new TrafficLightController(
                (Circle) circlesContainer.getChildren().get(0),
                (Circle) circlesContainer.getChildren().get(1),
                (Circle) circlesContainer.getChildren().get(2)
        );

        // Create radio buttons for controls
        HBox controlsContainer = builder.buildControlsContainer(trafficLightPane, controller);

        // Create and configure the main BorderPane
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(trafficLightPane);
        mainPane.setBottom(controlsContainer);

        // Set up and show the stage
        Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
        primaryStage.setTitle("Traffic Light Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * Controller class for managing the traffic light state and behavior.
 */
class TrafficLightController {
    private final Circle redCircle;
    private final Circle yellowCircle;
    private final Circle greenCircle;

    public TrafficLightController(Circle redCircle, Circle yellowCircle, Circle greenCircle) {
        this.redCircle = redCircle;
        this.yellowCircle = yellowCircle;
        this.greenCircle = greenCircle;
    }

    /**
     * Updates the colors of the traffic light circles.
     * @param redColor Color for the red circle
     * @param yellowColor Color for the yellow circle
     * @param greenColor Color for the green circle
     */
    public void updateLights(Color redColor, Color yellowColor, Color greenColor) {
        redCircle.setFill(redColor);
        yellowCircle.setFill(yellowColor);
        greenCircle.setFill(greenColor);
    }
}

/**
 * Builder class for creating traffic light UI components.
 */
class TrafficLightBuilder {
    private static final double STROKE_SIZE = 2;
    private static final Color STROKE_COLOR = Color.SILVER;
    private static final Color FILL_COLOR = Color.LIGHTGREY;
    private static final int CIRCLE_RADIUS = 40;
    private static final int VBOX_SPACING = 10;
    private static final int HBOX_SPACING = 10;

    /**
     * Builds a VBox containing traffic light circles.
     * @return VBox with circles
     */
    public VBox buildCirclesContainer() {
        VBox circlesContainer = new VBox(VBOX_SPACING);
        circlesContainer.setAlignment(Pos.CENTER);

        Circle redCircle = buildTrafficLightCircle();
        Circle yellowCircle = buildTrafficLightCircle();
        Circle greenCircle = buildTrafficLightCircle();

        circlesContainer.getChildren().addAll(redCircle, yellowCircle, greenCircle);
        return circlesContainer;
    }

    /**
     * Builds the rectangle that forms the border of the traffic light.
     * @return Rectangle with border
     */
    public Rectangle buildBorderRectangle() {
        Rectangle borderRectangle = new Rectangle(100, 300);
        borderRectangle.setFill(Color.WHITE);
        borderRectangle.setStroke(STROKE_COLOR);
        borderRectangle.setStrokeWidth(STROKE_SIZE);

        // Create and set shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setRadius(10);
        borderRectangle.setEffect(shadow);

        return borderRectangle;
    }

    /**
     * Builds an HBox containing radio buttons for controlling the lights.
     * @param trafficLightPane StackPane containing the traffic light elements
     * @param controller TrafficLightController instance
     * @return HBox with radio buttons
     */
    public HBox buildControlsContainer(StackPane trafficLightPane, TrafficLightController controller) {
        RadioButton rbRed = buildRadioButton("Red");
        RadioButton rbYellow = buildRadioButton("Yellow");
        RadioButton rbGreen = buildRadioButton("Green");

        ToggleGroup toggleGroup = new ToggleGroup();
        rbRed.setToggleGroup(toggleGroup);
        rbYellow.setToggleGroup(toggleGroup);
        rbGreen.setToggleGroup(toggleGroup);

        VBox circlesContainer = (VBox) trafficLightPane.getChildren().get(1);
        Circle redCircle = (Circle) circlesContainer.getChildren().get(0);
        Circle yellowCircle = (Circle) circlesContainer.getChildren().get(1);
        Circle greenCircle = (Circle) circlesContainer.getChildren().get(2);

        rbRed.setOnAction(e -> controller.updateLights(Color.RED, FILL_COLOR, FILL_COLOR));
        rbYellow.setOnAction(e -> controller.updateLights(FILL_COLOR, Color.YELLOW, FILL_COLOR));
        rbGreen.setOnAction(e -> controller.updateLights(FILL_COLOR, FILL_COLOR, Color.GREEN));

        HBox controlsContainer = new HBox(HBOX_SPACING);
        controlsContainer.setAlignment(Pos.CENTER);
        controlsContainer.getChildren().addAll(rbRed, rbYellow, rbGreen);

        controlsContainer.setPadding(new Insets(10));

        return controlsContainer;
    }

    /**
     * Builds a radio button with the specified label.
     * @param label Text label for the radio button
     * @return RadioButton with the given label
     */
    private RadioButton buildRadioButton(String label) {
        return new RadioButton(label);
    }

    /**
     * Builds a circle for the traffic light with a default color and border.
     * @return Circle with default settings
     */
    private Circle buildTrafficLightCircle() {
        Circle circle = new Circle(CIRCLE_RADIUS);
        circle.setFill(FILL_COLOR);
        circle.setStroke(STROKE_COLOR);
        circle.setStrokeWidth(STROKE_SIZE);
        return circle;
    }
}