package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.CategoryComponent;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

public class PreferencesController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Button save_changes_btn;
    @FXML
    private GridPane categories_container;
    @FXML
    private Label welcome_label;
    @FXML
    public void initialize(){
    }

    public void setMainScreenController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private void createCategoryComponents(Map<String, Boolean> preferencesMap) {
        int col = 0;
        int row = 0;

        for (Map.Entry<String, Boolean> category : preferencesMap.entrySet()) {
            String categoryName = category.getKey();
            boolean categoryValue = category.getValue();

            CategoryComponent categoryComponent = new CategoryComponent(categoryName, categoryValue);

            // Set positions dynamically based on row and col indices
            categoryComponent.setLayoutX(50 + col * 150); // Adjust X position as needed
            categoryComponent.setLayoutY(50 + row * 150); // Adjust Y position as needed

            // Add categoryComponent to categories_container
            categories_container.add(categoryComponent, col, row);

            // Update col and row indices for next position
            col++;
            if (col >= 3) { // Assuming 3 columns per row
                col = 0; // Reset column index
                row++; // Move to the next row
            }
        }
    }

    @FXML
    private void save_btn_clicked(MouseEvent event) {
        Map<String, Boolean> preferencesMap = new HashMap<>();

        for (int i = 0; i < categories_container.getChildren().size(); i++) {
            CategoryComponent categoryComponent = (CategoryComponent) categories_container.getChildren().get(i);

            boolean selected = categoryComponent.isSelected();
            String categoryName = categoryComponent.getCategoryName();

            preferencesMap.put(categoryName, selected);
        }

        // TODO: Save this preferencesMap to the db by UPDATE request
        RequestsUtility.setUserPreferencesMapping(mainWindowController.getJwt(), preferencesMap);
        System.out.println("Preferences Saved: " + preferencesMap);
    }

    public void initializeComponents() {
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        Map<String, Boolean> preferencesMap = RequestsUtility.getUserPreferences(mainWindowController.getJwt());
        createCategoryComponents(preferencesMap);
    }
}
