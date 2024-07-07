package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.EventComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class DashboardController {
    @FXML
    private HBox cardsContainer;

    @FXML
    public void initialize() {
        set_upcoming_events();
    }

    public void set_upcoming_events(){
        cardsContainer.getChildren().add(new EventComponent());
        cardsContainer.getChildren().add(new EventComponent());
        cardsContainer.getChildren().add(new EventComponent());
        cardsContainer.getChildren().add(new EventComponent());
    }
}
