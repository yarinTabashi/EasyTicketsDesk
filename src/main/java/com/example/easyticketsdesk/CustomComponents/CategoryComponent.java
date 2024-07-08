package com.example.easyticketsdesk.CustomComponents;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import java.util.Map;

public class CategoryComponent extends Button {
    private String categoryName;
    private Boolean isSelected;

    public CategoryComponent(String category_name, Boolean isSelected) {
        super();
        this.categoryName = category_name;
        setText(category_name);
        setFont(Font.font("System Bold", 25.0));
        setPrefWidth(129.0);
        setPrefHeight(131.0);

        if (isSelected){
            setSelected();
        }
        else {
            setUnselected();
        }

        // Handle click event
        setOnAction(e -> handleClick());
    }

    public void setSelected(){
        this.isSelected = true;
        setStyle("-fx-background-color: #544c8c; -fx-background-radius: 50;"); // Selected
    }

    public void setUnselected(){
        this.isSelected = false;
        setStyle("-fx-background-color: #9a93c9; -fx-background-radius: 50;"); // Unselected
    }

    private void handleClick() {
        if (this.isSelected){
            setUnselected();
        }
        else{
            setSelected();
        }
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
}