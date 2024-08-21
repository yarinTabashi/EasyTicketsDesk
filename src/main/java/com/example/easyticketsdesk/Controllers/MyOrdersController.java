package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.OrderComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.Reservation;
import com.example.easyticketsdesk.RequestsUtilty.MainRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyOrdersController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private VBox ordersContainer;
    private List<Reservation> reservations;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Label progress_label;

    @FXML
    public void initialize(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        this.reservations = MainRequests.getAllReservations(mainWindowController.getJwt());

        OrderComponent newComponent = null;
        for (Reservation reservation : reservations) {
            newComponent = new OrderComponent();
            newComponent.setMainWindowController(mainWindowController, reservation);
            newComponent.setReservationDetails();
            ordersContainer.getChildren().add(newComponent);
        }

        setCloseEventDetails();
    }

    public void setCloseEventDetails(){
        // Load the next event details
        Event event = MainRequests.getCloseEvent(this.mainWindowController.getJwt());

        if (event != null) {
            // Calculate days remaining to the event
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime eventDateTime = event.getDate(); // Assuming event.getEventDateTime() returns LocalDateTime
            long daysRemaining = Duration.between(now, eventDateTime).toDays();

            // Calculate progress
            //long totalDays = Duration.between(event.order(), event.getEventEndDate()).toDays();
            double progress = 1.0 - (double) daysRemaining / 50;

            // Set the progress bar value
            this.progressbar.setProgress(progress);

            // Set text for progress label
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String eventDateStr = eventDateTime.format(formatter);
            this.progress_label.setText("Just " + daysRemaining + " days to " + event.getEventName() + " on " + eventDateStr);
        } else {
            // Handle case where no event is retrieved
            this.progressbar.setProgress(0.0);
            this.progress_label.setText("No upcoming events."); // Or any appropriate message
        }
    }
}
