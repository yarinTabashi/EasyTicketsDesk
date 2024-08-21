package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.EventComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.RequestsUtilty.MainRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Random;

public class DashboardController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private GridPane gridpane;
    @FXML
    private Label welcome_label;
    @FXML
    private ImageView imageView;
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    public void setMainScreenController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void set_upcoming_events(){
        List<Event> events = MainRequests.getUpcomingEvents(mainWindowController.getJwt());

        initializeGridPane();
        EventComponent newComponent = null;
        int row = 0, col = 0;
        for (Event event : events) {
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


    private void setRandomBackgroundColor(EventComponent component) {
        Random random = new Random();
        String randomColor = colors[random.nextInt(colors.length)];
        component.setStyle("-fx-background-color: " + randomColor + ";");
    }

    public void initializeGridPane(){
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
}
