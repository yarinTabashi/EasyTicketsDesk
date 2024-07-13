package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.MainSign;
import com.example.easyticketsdesk.Controllers.MainWindowController;
import com.example.easyticketsdesk.Entities.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EventComponent extends VBox {
    @FXML
    private MainWindowController mainWindowController;
    private Event event;
    @FXML
    private Pane image_pane;
    @FXML
    private Label name_label;

    @FXML
    private Label price_label;

    @FXML
    private Label venue_label;

    @FXML
    private Label date_label;

    public EventComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/event_component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(EventComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handle_event_card_clicked();
            }
        });
    }

    public void setMainWindowController(MainWindowController mainWindowController, Event event) {
        this.mainWindowController = mainWindowController;
        this.event = event;
    }

    public void setEventDetails(String eventName, String venue, String eventDate){
        this.name_label.setText(eventName);
        this.venue_label.setText(venue);
        this.date_label.setText(eventDate);
    }

    private void handle_event_card_clicked() {
        this.mainWindowController.load_seats_screen(event);
    }


    public void setEventName(String eventName) {
        name_label.setText(eventName);
    }

    public void setPrice(String price) {
        price_label.setText(price);
    }

    public void setVenue(String venue) {
        venue_label.setText(venue);
    }

    public void setDate(String date) {
        date_label.setText(date);
    }
}
