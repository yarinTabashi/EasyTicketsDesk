package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.MainSign;
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
    private Pane image_pane;
    @FXML
    private Label event_name_label;

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

    private void handle_event_card_clicked() {
        try {
            // Load the FXML file for seat selection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/seats.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Seat Selection");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
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
