package com.example.easyticketsdesk.CustomComponents;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * A custom button component representing a category selection.
 * Allows toggling between selected and unselected states visually.
 */
public class CategoryComponent extends Button {
    private String categoryName;
    private Boolean isSelected;

    /**
     * Constructs a CategoryComponent with the specified category name and initial selection state.
     *
     * @param category_name The name of the category
     * @param isSelected    true if the category is initially selected, false otherwise
     */
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

        setOnAction(e -> handleClick()); // Create a click event
    }

    // Sets the component's visual style to indicate it is selected.
    public void setSelected(){
        this.isSelected = true;
        setStyle("-fx-background-color: #544c8c; -fx-background-radius: 50;"); // Selected
    }

    // Sets the component's visual style to indicate it is unselected.
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