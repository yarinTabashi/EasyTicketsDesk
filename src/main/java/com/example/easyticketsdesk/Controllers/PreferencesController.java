package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.CategoryComponent;
import com.example.easyticketsdesk.RequestsUtilty.MainRequests;
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


    public void setMainScreenController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void initializeComponents() {
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        Map<String, Boolean> preferencesMap = MainRequests.getUserPreferences(mainWindowController.getJwt());
        createCategoryComponents(preferencesMap);
    }

    /**
     * Creates and positions CategoryComponent instances based on preferencesMap data.
     * Each CategoryComponent is placed dynamically in a grid layout within categories_container.
     * @param preferencesMap A map containing category names as keys and boolean values indicating preferences.
     */
    private void createCategoryComponents(Map<String, Boolean> preferencesMap) {
        int col = 0;
        int row = 0;

        for (Map.Entry<String, Boolean> category : preferencesMap.entrySet()) {
            String categoryName = category.getKey();
            boolean categoryValue = category.getValue();

            CategoryComponent categoryComponent = new CategoryComponent(categoryName, categoryValue);

            // Set positions dynamically based on row and col indices
            categoryComponent.setLayoutX(50 + col * 150);
            categoryComponent.setLayoutY(50 + row * 150);

            // Add the component to the categories container
            categories_container.add(categoryComponent, col, row);

            // Update col and row indices for next position
            col++;
            if (col >= 3) {
                col = 0; // Reset column index
                row++;
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

        // Save in the db
        MainRequests.setUserPreferencesMapping(mainWindowController.getJwt(), preferencesMap);
        System.out.println("Preferences Saved: " + preferencesMap);
    }
}
