package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.EventComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private TextField search_field;
    @FXML
    private Button search_btn;
    @FXML
    private Label welcome_label;
    private List<Event> eventsList;
    @FXML
    private GridPane gridpane;
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};
    @FXML
    private ChoiceBox<String> categoryChoicebox;
    @FXML
    private Button clearBtn;

    public void setMainWindowController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    public void initializeScreen(){
        this.welcome_label.setText(mainWindowController.getWelcomeText());
        this.eventsList = RequestsUtility.getUpcomingEvents(mainWindowController.getJwt());

        try {
            // Retrieve categories
            Set<String> categories = RequestsUtility.getCategories(mainWindowController.getJwt());

            if (categories != null) {
                // Update the ChoiceBox with the categories
                categoryChoicebox.setItems(FXCollections.observableArrayList(categories));
            } else {
                System.err.println("No categories found or an error occurred.");
            }
        } catch (Exception e) {
            // Handle potential exceptions (e.g., network issues, parsing errors)
            e.printStackTrace();
            System.err.println("Error retrieving categories: " + e.getMessage());
        }
    }

    private void setRandomBackgroundColor(EventComponent component) {
        Random random = new Random();
        String randomColor = colors[random.nextInt(colors.length)];
        component.setStyle("-fx-background-color: " + randomColor + ";");
    }

    public void set_upcoming_events(String keyWord, String category){
        // Filter events based on the keyword and category
        List<Event> filteredEvents = eventsList.stream()
                .filter(event -> event.getEventName().toLowerCase().contains(keyWord.toLowerCase()) &&
                        (category == null || event.getCategory().getCategoryName().equals(category)))
                .collect(Collectors.toList());

        initializeGridPane();
        EventComponent newComponent = null;
        int row = 0, col = 0;
        for (Event event : filteredEvents) {
            newComponent = new EventComponent(mainWindowController, event);

            newComponent.getStyleClass().add("myborder"); // Apply style class to EventComponent
            setRandomBackgroundColor(newComponent); // Set random background color

            gridpane.add(newComponent, col, row, 1, 1);
            col++;

            if (col == 2){
                col = 0;
                row++;
            }
        }

        this.welcome_label.setText(mainWindowController.getWelcomeText());
    }

    public void initializeGridPane(){
        // Clear existing nodes from GridPane
        gridpane.getChildren().clear();

        // Set gap
        gridpane.setHgap(60);
        gridpane.setVgap(60);

        // Set column constraints (50% width for each column)
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridpane.getColumnConstraints().addAll(column1, column2);
    }

    public void searchClicked(MouseEvent mouseEvent) {
        String keyWord = search_field.getText().trim();
        String selectedCategory = categoryChoicebox.getValue();

        // Call set_upcoming_events with the keyword and selected category
        set_upcoming_events(keyWord, selectedCategory);
    }

    public void clear_clicked(MouseEvent mouseEvent) {
        // Clear search field
        search_field.clear();

        // Reset category choice
        categoryChoicebox.setValue(null);

        // Clear all events from the gridpane
        gridpane.getChildren().clear();
    }
}
