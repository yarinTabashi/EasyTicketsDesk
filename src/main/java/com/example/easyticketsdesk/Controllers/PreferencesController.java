package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.CategoryComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PreferencesController {
    @FXML
    private Button save_changes_btn;
    @FXML
    private GridPane categories_container;

    @FXML
    public void initialize() {
        // Add CustomCategory buttons
        CategoryComponent category1 = new CategoryComponent("Rock");
        CategoryComponent category2 = new CategoryComponent("Rock");
        CategoryComponent category3 = new CategoryComponent("Rock");
        CategoryComponent category4 = new CategoryComponent("Rock");
        CategoryComponent category5 = new CategoryComponent("Rock");
        CategoryComponent category6 = new CategoryComponent("Rock");

        // Set positions for the buttons (locate them in 2 lines)
        category1.setLayoutX(50);
        category1.setLayoutY(50);

        category2.setLayoutX(200);
        category2.setLayoutY(50);

        category3.setLayoutX(350);
        category3.setLayoutY(50);

        category4.setLayoutX(50);
        category4.setLayoutY(200);

        category5.setLayoutX(200);
        category5.setLayoutY(200);

        category6.setLayoutX(350);
        category6.setLayoutY(200);

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                categories_container.add(new CategoryComponent("dssd"), col, row);
            }
        }
    }
}
