package com.book_code.chapter14;

// https://liveexample.pearsoncmg.com/html/ShowImage.html

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class ShowImage extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a pane to hold the image views
        Pane pane = new HBox(10);
        pane.setPadding(new Insets(5, 5, 5, 5));

        // Load the image using the class loader
        URL imageUrl = getClass().getResource("/com.book_code.chapter14/image.jpg");
        if (imageUrl == null) {
            System.out.println("Image not found!");
            return; // Exit the method if the image is not found
        } else {
            System.out.println("Image found: " + imageUrl);
        }

        // Create an Image object
        Image image = new Image(imageUrl.toString());
        pane.getChildren().add(new ImageView(image));

        ImageView imageView2 = new ImageView(image);
        imageView2.setFitHeight(100);
        imageView2.setFitWidth(100);
        pane.getChildren().add(imageView2);

        ImageView imageView3 = new ImageView(image);
        imageView3.setRotate(90);
        pane.getChildren().add(imageView3);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShowImage"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}