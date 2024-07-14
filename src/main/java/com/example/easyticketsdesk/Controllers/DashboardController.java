package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.EventComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.util.List;

public class DashboardController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private HBox cardsContainer;
    @FXML
    private Label welcome_label;
    @FXML
    private ImageView imageView;


    public void setMainScreenController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void set_upcoming_events(){
        List<Event> events = RequestsUtility.getUpcomingEvents(mainWindowController.getJwt());

        EventComponent newComponent = null;
        for (Event event : events) {
            newComponent = new EventComponent(mainWindowController, event);
            cardsContainer.getChildren().add(newComponent);
        }

        this.welcome_label.setText(mainWindowController.getWelcomeText());
    }
}
