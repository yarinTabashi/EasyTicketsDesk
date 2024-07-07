package com.example.easyticketsdesk.CustomComponents;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CustomCategory extends Button {
    public CustomCategory(String category_name) {
        super();
        setText(category_name);
        setFont(Font.font("System Bold", 25.0));
        setPrefWidth(129.0);
        setPrefHeight(131.0);
        setStyle("-fx-background-color: #a4bd91; -fx-background-radius: 50;");

        // Apply hover effect using CSS pseudoclasses
        setOnMouseEntered(e -> setStyle("-fx-background-color: #85a96e; -fx-background-radius: 50;"));
        setOnMouseExited(e -> setStyle("-fx-background-color: #a4bd91; -fx-background-radius: 50;"));
        // Handle click event
        setOnAction(e -> handleClick());
    }

    private void handleClick() {
        System.out.println("Button clicked: " + getText());
        // Add your custom logic here for handling the click event
    }
}