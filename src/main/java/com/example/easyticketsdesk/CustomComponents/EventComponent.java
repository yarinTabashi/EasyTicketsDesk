package com.example.easyticketsdesk.CustomComponents;
import com.example.easyticketsdesk.Controllers.MainWindowController;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.ImageLoadingService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView ;

/**
 * Custom component representing an event card with details and an image.
 * Clicking on the card loads the seats screen related to the event.
 */
public class EventComponent extends VBox {
    @FXML
    private MainWindowController mainWindowController;
    private Event event;
    @FXML
    private Label name_label, price_label, venue_label, date_label; // Labels for event details
    @FXML
    private ImageView imageView;

    public EventComponent(MainWindowController mainWindowController, Event event) {
        // Initialize the properties
        this.mainWindowController = mainWindowController;
        this.event = event;

        // Load the fxml
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

        // Initialize the event details
        this.setEventDetails();
    }

    // Sets the event details and also load the event image.
    public void setEventDetails(){
        this.name_label.setText(this.event.getEventName());
        this.venue_label.setText(this.event.getVenue());
        this.date_label.setText(this.event.getDateFormat());

        this.setImage("https://media.istockphoto.com/id/1471448614/photo/crowd-of-people-dancing-at-a-music-show-in-barcelona-during-the-summer-of-2022.jpg?s=612x612&w=0&k=20&c=FpGZq6p-1Gqx1JHN-mgapyQhLlvtNGr2M-hxm7mSvt0=");
    }


    // Loads and sets the event image from the provided URL asynchronously.
    public void setImage(String imageUrl) {
        ImageLoadingService service = new ImageLoadingService(imageUrl);

        service.setOnSucceeded(event -> {
            Image loadedImage = service.getValue();
            imageView.setFitWidth(255);
            imageView.setFitHeight(110);
            imageView.setPreserveRatio(false);
            imageView.setImage(loadedImage);
        });

        service.start();
    }

    // Loads the seats screen related to the event.
    private void handle_event_card_clicked() {
        this.mainWindowController.load_seats_screen(event);
    }
}