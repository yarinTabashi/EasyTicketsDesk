package com.example.easyticketsdesk.CustomComponents;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomCard extends VBox {
    @FXML
    private Pane image_pane;
    @FXML
    private Label event_name_label;

    @FXML
    private Label price_label;

    @FXML
    private Label venue_label;

    @FXML
    private Label date_label;

    public CustomCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/customcard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CustomCard.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("CustomCard clicked!");
                // Add your logic here for handling card click
            }
        });
    }

    public void setEventName(String eventName) {
        event_name_label.setText(eventName);
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
